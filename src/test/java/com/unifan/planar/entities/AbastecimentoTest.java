package com.unifan.planar.entities;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.assertj.core.api.Assertions.assertThat;

class AbastecimentoTest {

    @Test
    void deveCalcularValorTotalCorretamente() {
        Abastecimento a = new Abastecimento();
        a.setLitros(40.0);
        a.setPrecoLitro(5.50);
        assertThat(a.getValorTotal()).isEqualTo(220.0);
    }

    @Test
    void deveCalcularConsumoCorretamente() {
        Abastecimento anterior = new Abastecimento();
        anterior.setQuilometragem(50000.0);

        Abastecimento atual = new Abastecimento();
        atual.setQuilometragem(50400.0);
        atual.setLitros(40.0);

        assertThat(atual.calcularConsumo(anterior)).isEqualTo(10.0);
    }

    @Test
    void deveRetornarZeroQuandoAbastecimentoAnteriorForNulo() {
        Abastecimento atual = new Abastecimento();
        atual.setQuilometragem(50400.0);
        atual.setLitros(40.0);

        assertThat(atual.calcularConsumo(null)).isEqualTo(0.0);
    }
}