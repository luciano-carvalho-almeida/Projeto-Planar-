package com.unifan.planar.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unifan.planar.dto.ServicoManutencaoDTO;
import com.unifan.planar.entities.Alerta;
import com.unifan.planar.entities.PlanoRevisao;
import com.unifan.planar.entities.ServicoManutencao;
import com.unifan.planar.entities.TipoAlerta;
import com.unifan.planar.entities.Veiculo;
import com.unifan.planar.repository.PlanoRevisaoRepository;
import com.unifan.planar.repository.ServicoManutencaoRepository;
import com.unifan.planar.repository.VeiculoRepository;
import com.unifan.planar.service.AlertaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ManutencaoController.class)
class ManutencaoControllerTest {

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

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deveCriarPlanoDeRevisao() throws Exception {
        PlanoRevisao plano = new PlanoRevisao();
        plano.setDescricao("Troca de Óleo");
        plano.setIntervaloKm(10000.0);

        PlanoRevisao planoSalvo = new PlanoRevisao();
        planoSalvo.setId(1L);
        planoSalvo.setDescricao("Troca de Óleo");

        when(planoRevisaoRepository.save(any(PlanoRevisao.class))).thenReturn(planoSalvo);

        mockMvc.perform(post("/api/manutencao/planos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(plano)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.descricao").value("Troca de Óleo"));
    }

    @Test
    void deveRegistrarServicoDeManutencao() throws Exception {
        ServicoManutencaoDTO dto = new ServicoManutencaoDTO();
        dto.setVeiculoId(1L);
        dto.setDescricao("Alinhamento");
        dto.setCusto(150.0);

        Veiculo veiculo = new Veiculo(1L, "ABC-1234", "Civic", 2020, 15000.0);
        ServicoManutencao servicoSalvo = new ServicoManutencao();
        servicoSalvo.setId(1L);
        servicoSalvo.setDescricao("Alinhamento");

        when(veiculoRepository.findById(1L)).thenReturn(Optional.of(veiculo));
        when(servicoManutencaoRepository.save(any(ServicoManutencao.class))).thenReturn(servicoSalvo);

        mockMvc.perform(post("/api/manutencao/servicos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void deveVerificarAlertas() throws Exception {
        Alerta alerta = new Alerta(TipoAlerta.MANUTENCAO_PLANO, "Revisão vencida");
        
        when(alertaService.gerarAlertasManutencao(any(Double.class), any(LocalDate.class)))
                .thenReturn(List.of(alerta));

        mockMvc.perform(post("/api/manutencao/verificar-alertas")
                .param("kmAtual", "60000.0")
                .param("data", "2026-06-15"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].tipoAlerta").value("MANUTENCAO_PLANO"))
                .andExpect(jsonPath("$[0].mensagem").value("Revisão vencida"));
    }
}
