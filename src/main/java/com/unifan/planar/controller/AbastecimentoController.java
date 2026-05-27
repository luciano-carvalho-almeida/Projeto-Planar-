package com.unifan.planar.controller;

import com.unifan.planar.entities.Abastecimento;
import com.unifan.planar.entities.TipoCombustivel;
import com.unifan.planar.repository.AbastecimentoRepository;
import com.unifan.planar.service.ConsumoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/abastecimentos")
public class AbastecimentoController {

    private final AbastecimentoRepository repository;
    private final ConsumoService consumoService;

    public AbastecimentoController(AbastecimentoRepository repository, ConsumoService consumoService) {
        this.repository = repository;
        this.consumoService = consumoService;
    }

    @PostMapping
    public Abastecimento salvar(@RequestBody Abastecimento abastecimento) {
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
