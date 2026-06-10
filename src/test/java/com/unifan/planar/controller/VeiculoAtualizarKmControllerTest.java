package com.unifan.planar.controller;

import com.unifan.planar.entities.Veiculo;
import com.unifan.planar.service.VeiculoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VeiculoController.class)
class VeiculoAtualizarKmControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VeiculoService veiculoService;

    @Test
    void deveAtualizarQuilometragemComSucesso() throws Exception {
        Veiculo atualizado = new Veiculo(1L, "ABC-1234", "Civic", 2020, 75000.0);
        when(veiculoService.atualizarQuilometragem(1L, 75000.0)).thenReturn(atualizado);

        mockMvc.perform(put("/veiculos/1/quilometragem")
                        .param("novaKm", "75000.0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quilometragemAtual").value(75000.0));
    }
}