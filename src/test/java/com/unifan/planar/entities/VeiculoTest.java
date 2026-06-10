package com.unifan.planar.entities;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

class VeiculoTest {

    @Test
    void deveCalcularMediaKmDiariaCorretamente() {
        Veiculo v = new Veiculo();
        v.setQuilometragemAtual(3000.0);
        v.setDataAquisicao(LocalDate.now().minusDays(100));
        assertThat(v.calcularMediaKmDiaria()).isEqualTo(30.0);
    }

    @Test
    void deveRetornarZeroQuandoDataAquisicaoForNula() {
        Veiculo v = new Veiculo();
        v.setDataAquisicao(null);
        v.setQuilometragemAtual(5000.0);
        assertThat(v.calcularMediaKmDiaria()).isEqualTo(0.0);
    }

    @Test
    void deveRetornarZeroQuandoDataAquisicaoForHoje() {
        Veiculo v = new Veiculo();
        v.setQuilometragemAtual(5000.0);
        v.setDataAquisicao(LocalDate.now());
        assertThat(v.calcularMediaKmDiaria()).isEqualTo(0.0);
    }

    @Test
    void deveGerarAlertaQuandoKmAcimaDeDezmil() {
        Veiculo v = new Veiculo();
        v.setQuilometragemAtual(15000.0);
        List<String> alertas = v.gerarAlertas();
        assertThat(alertas).hasSize(1);
        assertThat(alertas.get(0)).contains("revisão");
    }

    @Test
    void naoDeveGerarAlertaQuandoKmAbaixoDeDezmil() {
        Veiculo v = new Veiculo();
        v.setQuilometragemAtual(8000.0);
        assertThat(v.gerarAlertas()).isEmpty();
    }
}