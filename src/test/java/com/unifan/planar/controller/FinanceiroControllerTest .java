package com.unifan.planar.controller;

import com.unifan.planar.dto.ProjecaoGastosDTO;
import com.unifan.planar.dto.RelatorioMensalDTO;
import com.unifan.planar.entities.Veiculo;
import com.unifan.planar.repository.VeiculoRepository;
import com.unifan.planar.service.FinanceiroService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.YearMonth;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FinanceiroController.class)
class FinanceiroControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FinanceiroService financeiroService;

    @MockBean
    private VeiculoRepository veiculoRepository;

    @Test
    void deveRetornarRelatorioMensalComStatus200() throws Exception {
        Veiculo veiculo = new Veiculo(1L, "ABC-1234", "Civic", 2020, 15000.0);
        RelatorioMensalDTO relatorio = new RelatorioMensalDTO(YearMonth.of(2026, 6), 500.0, 300.0, 1000.0);

        when(veiculoRepository.findById(1L)).thenReturn(Optional.of(veiculo));
        when(financeiroService.gerarRelatorioMensal(eq(veiculo), any(YearMonth.class))).thenReturn(relatorio);

        mockMvc.perform(get("/api/financeiro/relatorio/1")
                .param("ano", "2026")
                .param("mes", "6")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalManutencao").value(500.0))
                .andExpect(jsonPath("$.totalCombustivel").value(300.0));
    }

    @Test
    void deveRetornar404QuandoVeiculoNaoExistirNoRelatorio() throws Exception {
        when(veiculoRepository.findById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/financeiro/relatorio/99")
                .param("ano", "2026")
                .param("mes", "6"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveRetornarProjecaoGastosComStatus200() throws Exception {
        Veiculo veiculo = new Veiculo(1L, "ABC-1234", "Civic", 2020, 15000.0);
        ProjecaoGastosDTO projecao = new ProjecaoGastosDTO(38.5, 12.7, 381.0, 4572.0);

        when(veiculoRepository.findById(1L)).thenReturn(Optional.of(veiculo));
        when(financeiroService.projetarGastos(veiculo)).thenReturn(projecao);

        mockMvc.perform(get("/api/financeiro/projecao/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mediaKmDiaria").value(38.5))
                .andExpect(jsonPath("$.projecaoMensal").value(381.0));
    }
}
