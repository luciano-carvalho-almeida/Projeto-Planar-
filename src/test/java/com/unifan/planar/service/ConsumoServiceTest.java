package com.unifan.planar.service;

import com.unifan.planar.entities.TipoCombustivel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ConsumoServiceTest {

    private ConsumoService consumoService;

    @BeforeEach
    void setUp() {
        consumoService = new ConsumoService();
    }

    @Test
    void deveCalcularEficienciaCorretamente() {
        Double resultado = consumoService.calcularEficiencia(300.0, 30.0);
        assertThat(resultado).isEqualTo(10.0);
    }

    @Test
    void deveRetornarZeroQuandoLitrosForemZero() {
        Double resultado = consumoService.calcularEficiencia(300.0, 0.0);
        assertThat(resultado).isEqualTo(0.0);
    }

    @Test
    void deveRecomendarEtanolQuandoRelacaoAbaixoDe70Porcento() {
        TipoCombustivel resultado = consumoService.recomendarCombustivel(3.49, 5.99);
        assertThat(resultado).isEqualTo(TipoCombustivel.ETANOL);
    }

    @Test
    void deveRecomendarGasolinaQuandoRelacaoAcimaDe70Porcento() {
        TipoCombustivel resultado = consumoService.recomendarCombustivel(4.50, 5.99);
        assertThat(resultado).isEqualTo(TipoCombustivel.GASOLINA);
    }

    @Test
    void deveRecomendarGasolinaQuandoRelacaoExatamente70Porcento() {
        TipoCombustivel resultado = consumoService.recomendarCombustivel(4.193, 5.99);
        assertThat(resultado).isEqualTo(TipoCombustivel.ETANOL);
    }

    @Test
    void deveLancarExcecaoQuandoGasolinaForZero() {
        assertThatThrownBy(() -> consumoService.recomendarCombustivel(3.0, 0.0))
                .isInstanceOf(ArithmeticException.class)
                .hasMessageContaining("/ by zero");
    }
}
