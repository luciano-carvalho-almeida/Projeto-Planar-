package com.unifan.planar;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unifan.planar.dto.AbastecimentoDTO;
import com.unifan.planar.dto.RelatorioMensalDTO;
import com.unifan.planar.entities.PlanoRevisao;
import com.unifan.planar.entities.TipoCombustivel;
import com.unifan.planar.entities.Veiculo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class PlanarIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void fluxoVeiculo_criarBuscarEAtualizarQuilometragem() throws Exception {
        Veiculo novoVeiculo = new Veiculo();
        novoVeiculo.setPlaca("INT-0001");
        novoVeiculo.setModelo("Civic");
        novoVeiculo.setAno(2020);
        novoVeiculo.setQuilometragemAtual(50000.0);

        MvcResult criarResult = mockMvc.perform(post("/veiculos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(novoVeiculo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.placa").value("INT-0001"))
                .andReturn();

        Veiculo criado = objectMapper.readValue(criarResult.getResponse().getContentAsString(), Veiculo.class);
        Long id = criado.getId();

        mockMvc.perform(get("/veiculos/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.modelo").value("Civic"));

        mockMvc.perform(put("/veiculos/" + id + "/quilometragem")
                        .param("novaKm", "75000.0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quilometragemAtual").value(75000.0));
    }

    @Test
    void fluxoAbastecimento_registrarEConsultarEficiencia() throws Exception {
        Veiculo veiculo = new Veiculo();
        veiculo.setPlaca("INT-0002");
        veiculo.setModelo("Gol");
        veiculo.setAno(2019);
        veiculo.setQuilometragemAtual(30000.0);

        MvcResult veiculoResult = mockMvc.perform(post("/veiculos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(veiculo)))
                .andExpect(status().isOk())
                .andReturn();

        Veiculo criado = objectMapper.readValue(veiculoResult.getResponse().getContentAsString(), Veiculo.class);

        AbastecimentoDTO dto = new AbastecimentoDTO();
        dto.setVeiculoId(criado.getId());
        dto.setData(LocalDate.of(2024, 6, 10));
        dto.setQuilometragem(30000.0);
        dto.setLitros(40.0);
        dto.setPrecoLitro(5.50);
        dto.setTipoCombustivel(TipoCombustivel.GASOLINA);

        mockMvc.perform(post("/abastecimentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/abastecimentos/eficiencia")
                        .param("km", "400.0")
                        .param("litros", "40.0"))
                .andExpect(status().isOk())
                .andExpect(content().string("10.0"));
    }

    @Test
    void fluxoManutencao_criarPlanoEVerificarAlerta() throws Exception {
        PlanoRevisao plano = new PlanoRevisao();
        plano.setDescricao("Troca de óleo");
        plano.setIntervaloKm(10000.0);
        plano.setIntervaloMeses(6);
        plano.setUltimaRealizacaoKm(90000.0);
        plano.setUltimaRealizacaoData(LocalDate.of(2024, 1, 1));

        mockMvc.perform(post("/api/manutencao/planos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(plano)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.descricao").value("Troca de óleo"));

        mockMvc.perform(post("/api/manutencao/verificar-alertas")
                        .param("kmAtual", "101000.0")
                        .param("data", "2024-08-01"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].tipoAlerta").value("MANUTENCAO_PLANO"));
    }

    @Test
    void fluxoFinanceiro_registrarDadosEGerarRelatorio() throws Exception {
        Veiculo veiculo = new Veiculo();
        veiculo.setPlaca("INT-0003");
        veiculo.setModelo("Corolla");
        veiculo.setAno(2021);
        veiculo.setQuilometragemAtual(60000.0);

        MvcResult veiculoResult = mockMvc.perform(post("/veiculos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(veiculo)))
                .andExpect(status().isOk())
                .andReturn();

        Veiculo criado = objectMapper.readValue(veiculoResult.getResponse().getContentAsString(), Veiculo.class);

        AbastecimentoDTO dto = new AbastecimentoDTO();
        dto.setVeiculoId(criado.getId());
        dto.setData(LocalDate.of(2024, 6, 15));
        dto.setQuilometragem(60000.0);
        dto.setLitros(50.0);
        dto.setPrecoLitro(5.00);
        dto.setTipoCombustivel(TipoCombustivel.GASOLINA);

        mockMvc.perform(post("/abastecimentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());

        MvcResult relatorioResult = mockMvc.perform(get("/api/financeiro/relatorio/" + criado.getId())
                        .param("ano", "2024")
                        .param("mes", "6"))
                .andExpect(status().isOk())
                .andReturn();

        RelatorioMensalDTO relatorio = objectMapper.readValue(
                relatorioResult.getResponse().getContentAsString(), RelatorioMensalDTO.class);

        assertThat(relatorio.getTotalCombustivel()).isEqualTo(250.0);
        assertThat(relatorio.getTotalManutencao()).isEqualTo(0.0);
    }

    @Test
    void deveRetornar404AoBuscarVeiculoInexistente() throws Exception {
        mockMvc.perform(get("/veiculos/9999"))
                .andExpect(status().isNotFound());
    }
}