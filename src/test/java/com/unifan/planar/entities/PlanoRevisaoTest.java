package com.unifan.planar.entities;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.assertj.core.api.Assertions.assertThat;

class PlanoRevisaoTest {

    @Test
    void deveCalcularProximaRevisaoKmComUltimaRealizacao() {
        PlanoRevisao p = new PlanoRevisao();
        p.setUltimaRealizacaoKm(90000.0);
        p.setIntervaloKm(10000.0);
        assertThat(p.proximaRevisaoKm()).isEqualTo(100000.0);
    }

    @Test
    void deveRetornarIntervaloKmQuandoUltimaRealizacaoForNula() {
        PlanoRevisao p = new PlanoRevisao();
        p.setIntervaloKm(10000.0);
        assertThat(p.proximaRevisaoKm()).isEqualTo(10000.0);
    }

    @Test
    void deveCalcularProximaRevisaoData() {
        PlanoRevisao p = new PlanoRevisao();
        p.setUltimaRealizacaoData(LocalDate.of(2024, 1, 1));
        p.setIntervaloMeses(6);
        assertThat(p.proximaRevisaoData()).isEqualTo(LocalDate.of(2024, 7, 1));
    }

    @Test
    void deveRetornarNullQuandoUltimaRealizacaoDataForNula() {
        PlanoRevisao p = new PlanoRevisao();
        p.setIntervaloMeses(6);
        assertThat(p.proximaRevisaoData()).isNull();
    }
}