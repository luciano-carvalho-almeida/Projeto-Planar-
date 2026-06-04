package com.unifan.planar.controller;

import com.unifan.planar.dto.ServicoManutencaoDTO;
import com.unifan.planar.entities.Alerta;
import com.unifan.planar.entities.PlanoRevisao;
import com.unifan.planar.entities.ServicoManutencao;
import com.unifan.planar.entities.Veiculo;
import com.unifan.planar.repository.PlanoRevisaoRepository;
import com.unifan.planar.repository.ServicoManutencaoRepository;
import com.unifan.planar.repository.VeiculoRepository;
import com.unifan.planar.service.AlertaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/manutencao")
public class ManutencaoController {

    private final PlanoRevisaoRepository planoRevisaoRepository;
    private final ServicoManutencaoRepository servicoManutencaoRepository;
    private final VeiculoRepository veiculoRepository;
    private final AlertaService alertaService;

    public ManutencaoController(PlanoRevisaoRepository planoRevisaoRepository,
                                ServicoManutencaoRepository servicoManutencaoRepository,
                                VeiculoRepository veiculoRepository,
                                AlertaService alertaService) {
        this.planoRevisaoRepository = planoRevisaoRepository;
        this.servicoManutencaoRepository = servicoManutencaoRepository;
        this.veiculoRepository = veiculoRepository;
        this.alertaService = alertaService;
    }

    @PostMapping("/planos")
    public ResponseEntity<PlanoRevisao> criarPlano(@RequestBody PlanoRevisao plano) {
        PlanoRevisao novoPlano = planoRevisaoRepository.save(plano);
        return new ResponseEntity<>(novoPlano, HttpStatus.CREATED);
    }

    @GetMapping("/planos")
    public ResponseEntity<List<PlanoRevisao>> listarPlanos() {
        return ResponseEntity.ok(planoRevisaoRepository.findAll());
    }

    @PostMapping("/servicos")
    public ResponseEntity<ServicoManutencao> registrarServico(@RequestBody ServicoManutencaoDTO requestDTO) {
        Veiculo veiculo = veiculoRepository.findById(requestDTO.getVeiculoId())
                .orElseThrow(() -> new RuntimeException("Veículo não encontrado com ID: " + requestDTO.getVeiculoId()));

        ServicoManutencao servico = new ServicoManutencao();
        servico.setDataRealizacao(requestDTO.getDataRealizacao());
        servico.setQuilometragemRealizacao(requestDTO.getQuilometragemRealizacao());
        servico.setDescricao(requestDTO.getDescricao());
        servico.setCusto(requestDTO.getCusto());
        servico.setObservacoes(requestDTO.getObservacoes());
        servico.setVeiculo(veiculo);

        if (requestDTO.getPlanoId() != null) {
            planoRevisaoRepository.findById(requestDTO.getPlanoId())
                    .ifPresent(servico::associarPlano);
        }

        ServicoManutencao salvo = servicoManutencaoRepository.save(servico);
        return new ResponseEntity<>(salvo, HttpStatus.CREATED);
    }

    @GetMapping("/servicos")
    public ResponseEntity<List<ServicoManutencao>> listarServicos() {
        return ResponseEntity.ok(servicoManutencaoRepository.findAll());
    }

    @PostMapping("/verificar-alertas")
    public ResponseEntity<List<Alerta>> verificarAlertas(@RequestParam Double kmAtual,
                                                          @RequestParam(required = false) String data) {
        LocalDate dataAtual = (data != null) ? LocalDate.parse(data) : LocalDate.now();
        List<Alerta> alertasGerados = alertaService.gerarAlertasManutencao(kmAtual, dataAtual);
        return ResponseEntity.ok(alertasGerados);
    }

    @GetMapping("/planos-revisao")
    public ResponseEntity<List<PlanoRevisao>> listarPlanosAlternativo() {
        return ResponseEntity.ok(planoRevisaoRepository.findAll());
    }
}
