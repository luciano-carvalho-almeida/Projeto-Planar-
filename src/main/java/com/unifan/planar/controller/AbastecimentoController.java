package com.unifan.planar.controller;

import com.unifan.planar.entities.Abastecimento;
import com.unifan.planar.repository.AbastecimentoRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/abastecimentos")
public class AbastecimentoController {

    private final AbastecimentoRepository repository;

    public AbastecimentoController(AbastecimentoRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public Abastecimento salvar(@RequestBody Abastecimento abastecimento) {
        return repository.save(abastecimento);
    }

    @GetMapping
    public List<Abastecimento> listar() {
        return repository.findAll();
    }
}