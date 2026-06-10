package com.unifan.planar.controller;

import com.unifan.planar.entities.Abastecimento;
import com.unifan.planar.entities.TipoCombustivel;
import com.unifan.planar.repository.AbastecimentoRepository;
import com.unifan.planar.repository.VeiculoRepository;
import com.unifan.planar.service.ConsumoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AbastecimentoController.class)
class AbastecimentoListarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AbastecimentoRepository repository;

    @MockBean
    private VeiculoRepository veiculoRepository;

    @MockBean
    private ConsumoService consumoService;

    @Test
    void deveListarTodosAbastecimentos() throws Exception {
        Abastecimento a = new Abastecimento();
        a.setId(1L);
        a.setLitros(40.0);
        a.setPrecoLitro(5.50);
        a.setTipoCombustivel(TipoCombustivel.GASOLINA);

        when(repository.findAll()).thenReturn(List.of(a));

        mockMvc.perform(get("/abastecimentos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoHaAbastecimentos() throws Exception {
        when(repository.findAll()).thenReturn(List.of());

        mockMvc.perform(get("/abastecimentos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }
}