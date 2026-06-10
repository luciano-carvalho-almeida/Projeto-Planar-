package com.unifan.planar.controller;

import com.unifan.planar.dto.AbastecimentoDTO;
import com.unifan.planar.entities.Abastecimento;
import com.unifan.planar.entities.TipoCombustivel;
import com.unifan.planar.entities.Veiculo;
import com.unifan.planar.repository.AbastecimentoRepository;
import com.unifan.planar.repository.VeiculoRepository;
import com.unifan.planar.service.ConsumoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/abastecimentos")
public class AbastecimentoController {

    private final AbastecimentoRepository repository;
    private final VeiculoRepository veiculoRepository;
    private final ConsumoService consumoService;

    public AbastecimentoController(AbastecimentoRepository repository,
                                   VeiculoRepository veiculoRepository,
                                   ConsumoService consumoService) {
        this.repository = repository;
        this.veiculoRepository = veiculoRepository;
        this.consumoService = consumoService;
    }

    @PostMapping
    public Abastecimento salvar(@RequestBody AbastecimentoDTO requestDTO) {
        Veiculo veiculo = veiculoRepository.findById(requestDTO.getVeiculoId())
                .orElseThrow(() -> new RuntimeException("Veículo não encontrado com ID: " + requestDTO.getVeiculoId()));
        
        Abastecimento abastecimento = new Abastecimento();
        abastecimento.setData(requestDTO.getData());
        abastecimento.setQuilometragem(requestDTO.getQuilometragem());
        abastecimento.setLitros(requestDTO.getLitros());
        abastecimento.setTipoCombustivel(requestDTO.getTipoCombustivel());
        abastecimento.setPrecoLitro(requestDTO.getPrecoLitro());
        abastecimento.setVeiculo(veiculo);
        
        return repository.save(abastecimento);
    }

    @GetMapping
    public List<Abastecimento> listar() {
        return repository.findAll();
    }

    @GetMapping("/eficiencia")
    public Double obterEficiencia(@RequestParam Double km, @RequestParam Double litros) {
        return consumoService.calcularEficiencia(km, litros);
    }

    @GetMapping("/recomendacao")
    public TipoCombustivel obterRecomendacao(@RequestParam Double precoEtanol, @RequestParam Double precoGasolina) {
        return consumoService.recomendarCombustivel(precoEtanol, precoGasolina);
    }
}
