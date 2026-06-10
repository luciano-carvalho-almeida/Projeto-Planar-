package com.unifan.planar.controller;

import com.unifan.planar.entities.PlanoRevisao;
import com.unifan.planar.entities.ServicoManutencao;
import com.unifan.planar.repository.PlanoRevisaoRepository;
import com.unifan.planar.repository.ServicoManutencaoRepository;
import com.unifan.planar.repository.VeiculoRepository;
import com.unifan.planar.service.AlertaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ManutencaoController.class)
class ManutencaoListagensControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlanoRevisaoRepository planoRevisaoRepository;

    @MockBean
    private ServicoManutencaoRepository servicoManutencaoRepository;

    @MockBean
    private VeiculoRepository veiculoRepository;

    @MockBean
    private AlertaService alertaService;

    @Test
    void deveListarPlanos() throws Exception {
        PlanoRevisao plano = new PlanoRevisao();
        plano.setId(1L);
        plano.setDescricao("Troca de óleo");

        when(planoRevisaoRepository.findAll()).thenReturn(List.of(plano));

        mockMvc.perform(get("/api/manutencao/planos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].descricao").value("Troca de óleo"));
    }

    @Test
    void deveListarServicos() throws Exception {
        ServicoManutencao servico = new ServicoManutencao();
        servico.setId(1L);
        servico.setDescricao("Alinhamento");

        when(servicoManutencaoRepository.findAll()).thenReturn(List.of(servico));

        mockMvc.perform(get("/api/manutencao/servicos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].descricao").value("Alinhamento"));
    }

    @Test
    void deveListarPlanosRevisaoEndpointAlternativo() throws Exception {
        PlanoRevisao plano = new PlanoRevisao();
        plano.setId(2L);
        plano.setDescricao("Alinhamento");

        when(planoRevisaoRepository.findAll()).thenReturn(List.of(plano));

        mockMvc.perform(get("/api/manutencao/planos-revisao"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(2));
    }
}