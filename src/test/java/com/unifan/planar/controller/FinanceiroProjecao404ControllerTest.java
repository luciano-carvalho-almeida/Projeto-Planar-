package com.unifan.planar.controller;

import com.unifan.planar.repository.VeiculoRepository;
import com.unifan.planar.service.FinanceiroService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FinanceiroController.class)
class FinanceiroProjecao404ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FinanceiroService financeiroService;

    @MockBean
    private VeiculoRepository veiculoRepository;

    @Test
    void deveRetornar404QuandoVeiculoNaoExistirNaProjecao() throws Exception {
        when(veiculoRepository.findById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/financeiro/projecao/99"))
                .andExpect(status().isNotFound());
    }
}