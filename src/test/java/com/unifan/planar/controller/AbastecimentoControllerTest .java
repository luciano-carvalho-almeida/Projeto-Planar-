package com.unifan.planar.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unifan.planar.dto.AbastecimentoDTO;
import com.unifan.planar.entities.Abastecimento;
import com.unifan.planar.entities.TipoCombustivel;
import com.unifan.planar.entities.Veiculo;
import com.unifan.planar.repository.AbastecimentoRepository;
import com.unifan.planar.repository.VeiculoRepository;
import com.unifan.planar.service.ConsumoService;
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

@WebMvcTest(AbastecimentoController.class)
class AbastecimentoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AbastecimentoRepository repository;

    @MockBean
    private VeiculoRepository veiculoRepository;

    @MockBean
    private ConsumoService consumoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deveSalvarAbastecimentoComSucesso() throws Exception {
        AbastecimentoDTO dto = new AbastecimentoDTO();
        dto.setVeiculoId(1L);
        dto.setLitros(40.0);
        dto.setPrecoLitro(5.50);
        dto.setTipoCombustivel(TipoCombustivel.GASOLINA);

        Veiculo veiculo = new Veiculo(1L, "ABC-1234", "Civic", 2020, 15000.0);
        Abastecimento abastecimentoSalvo = new Abastecimento();
        abastecimentoSalvo.setId(1L);
        abastecimentoSalvo.setLitros(40.0);
        abastecimentoSalvo.setPrecoLitro(5.50);

        when(veiculoRepository.findById(1L)).thenReturn(Optional.of(veiculo));
        when(repository.save(any(Abastecimento.class))).thenReturn(abastecimentoSalvo);

        mockMvc.perform(post("/abastecimentos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void deveRetornarEficiencia() throws Exception {
        when(consumoService.calcularEficiencia(100.0, 10.0)).thenReturn(10.0);

        mockMvc.perform(get("/abastecimentos/eficiencia")
                .param("km", "100.0")
                .param("litros", "10.0"))
                .andExpect(status().isOk())
                .andExpect(content().string("10.0"));
    }

    @Test
    void deveRetornarRecomendacaoDeCombustivel() throws Exception {
        when(consumoService.recomendarCombustivel(3.50, 5.00)).thenReturn(TipoCombustivel.ETANOL);

        mockMvc.perform(get("/abastecimentos/recomendacao")
                .param("precoEtanol", "3.50")
                .param("precoGasolina", "5.00"))
                .andExpect(status().isOk())
                .andExpect(content().string("\"ETANOL\"")); // Como é Enum, o JSON retorna como String com aspas
    }
}
