package com.unifan.planar.service;

import com.unifan.planar.entities.Alerta;
import com.unifan.planar.entities.PlanoRevisao;
import com.unifan.planar.entities.TipoAlerta;
import com.unifan.planar.repository.AlertaRepository;
import com.unifan.planar.repository.PlanoRevisaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AlertaServiceTest {

    @Mock
    private PlanoRevisaoRepository planoRevisaoRepository;

    @Mock
    private AlertaRepository alertaRepository;

    @InjectMocks
    private AlertaService alertaService;

    private PlanoRevisao plano;

    @BeforeEach
    void setUp() {
        plano = new PlanoRevisao();
        plano.setId(1L);
        plano.setDescricao("Troca de óleo");
        plano.setIntervaloKm(10000.0);
        plano.setIntervaloMeses(6);
        plano.setUltimaRealizacaoKm(90000.0);
        plano.setUltimaRealizacaoData(LocalDate.of(2024, 1, 1));
    }

    @Test
    void deveGerarAlertaQuandoKmUltrapassado() {
        when(planoRevisaoRepository.findAll()).thenReturn(List.of(plano));
        when(alertaRepository.saveAll(anyList())).thenAnswer(inv -> inv.getArgument(0));

        List<Alerta> resultado = alertaService.gerarAlertasManutencao(100500.0, LocalDate.of(2024, 2, 1));

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getTipoAlerta()).isEqualTo(TipoAlerta.MANUTENCAO_PLANO);
        assertThat(resultado.get(0).getMensagem()).contains("Troca de óleo");
        verify(alertaRepository, times(1)).saveAll(anyList());
    }

    @Test
    void deveGerarAlertaQuandoDataUltrapassada() {
        when(planoRevisaoRepository.findAll()).thenReturn(List.of(plano));
        when(alertaRepository.saveAll(anyList())).thenAnswer(inv -> inv.getArgument(0));

        List<Alerta> resultado = alertaService.gerarAlertasManutencao(80000.0, LocalDate.of(2024, 8, 1));

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getTipoAlerta()).isEqualTo(TipoAlerta.MANUTENCAO_PLANO);
        verify(alertaRepository, times(1)).saveAll(anyList());
    }

    @Test
    void naoDeveGerarAlertaQuandoDentroDoIntervalo() {
        when(planoRevisaoRepository.findAll()).thenReturn(List.of(plano));

        List<Alerta> resultado = alertaService.gerarAlertasManutencao(95000.0, LocalDate.of(2024, 3, 1));

        assertThat(resultado).isEmpty();
        verify(alertaRepository, never()).saveAll(anyList());
    }

    @Test
    void naoDeveGerarAlertaQuandoNenhumPlanoExiste() {
        when(planoRevisaoRepository.findAll()).thenReturn(List.of());

        List<Alerta> resultado = alertaService.gerarAlertasManutencao(100000.0, LocalDate.now());

        assertThat(resultado).isEmpty();
        verify(alertaRepository, never()).saveAll(anyList());
    }

    @Test
    void naoDeveGerarAlertaQuandoKmOuDataForemNulos() {
        when(planoRevisaoRepository.findAll()).thenReturn(List.of(plano));

        List<Alerta> resultadoKmNulo = alertaService.gerarAlertasManutencao(null, LocalDate.now());
        List<Alerta> resultadoDataNula = alertaService.gerarAlertasManutencao(100000.0, null);

        assertThat(resultadoKmNulo).isEmpty();
        assertThat(resultadoDataNula).isEmpty();
        verify(alertaRepository, never()).saveAll(anyList());
    }

    @Test
    void deveGerarMultiplosAlertasComMultiplosPlanos() {
        PlanoRevisao plano2 = new PlanoRevisao();
        plano2.setId(2L);
        plano2.setDescricao("Alinhamento");
        plano2.setIntervaloKm(5000.0);
        plano2.setIntervaloMeses(3);
        plano2.setUltimaRealizacaoKm(90000.0);
        plano2.setUltimaRealizacaoData(LocalDate.of(2024, 1, 1));

        when(planoRevisaoRepository.findAll()).thenReturn(List.of(plano, plano2));
        when(alertaRepository.saveAll(anyList())).thenAnswer(inv -> inv.getArgument(0));

        List<Alerta> resultado = alertaService.gerarAlertasManutencao(100000.0, LocalDate.of(2024, 8, 1));

        assertThat(resultado).hasSize(2);
        verify(alertaRepository, times(1)).saveAll(anyList());
    }
}
