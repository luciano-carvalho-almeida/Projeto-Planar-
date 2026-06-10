package com.unifan.planar.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unifan.planar.entities.Veiculo;
import com.unifan.planar.service.VeiculoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VeiculoController.class)
class VeiculoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VeiculoService veiculoService;

    @Autowired
    private ObjectMapper objectMapper; 

    @Test
    void deveListarTodosOsVeiculosComStatus200() throws Exception {
        Veiculo v1 = new Veiculo(1L, "ABC-1234", "Civic", 2020, 15000.0);
        when(veiculoService.listarTodos()).thenReturn(List.of(v1));

        mockMvc.perform(get("/veiculos")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].placa").value("ABC-1234"))
                .andExpect(jsonPath("$[0].modelo").value("Civic"));
    }

    @Test
    void deveBuscarVeiculoPorIdComStatus200() throws Exception {
        Veiculo v1 = new Veiculo(1L, "XYZ-9876", "Corolla", 2022, 10000.0);
        when(veiculoService.buscarPorId(1L)).thenReturn(Optional.of(v1));

        mockMvc.perform(get("/veiculos/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.placa").value("XYZ-9876"));
    }

    @Test
    void deveSalvarVeiculoComStatus200() throws Exception {
        Veiculo novoVeiculo = new Veiculo(null, "DEF-5678", "Gol", 2018, 50000.0);
        Veiculo veiculoSalvo = new Veiculo(1L, "DEF-5678", "Gol", 2018, 50000.0);

        when(veiculoService.salvar(any(Veiculo.class))).thenReturn(veiculoSalvo);

        mockMvc.perform(post("/veiculos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(novoVeiculo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void deveDeletarVeiculoComStatus200() throws Exception {
        doNothing().when(veiculoService).deletar(1L);

        mockMvc.perform(delete("/veiculos/1"))
                .andExpect(status().isOk());

        verify(veiculoService, times(1)).deletar(1L);
    }
}
