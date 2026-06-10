package com.unifan.planar.repository;

import com.unifan.planar.entities.Abastecimento;
import com.unifan.planar.entities.TipoCombustivel;
import com.unifan.planar.entities.Veiculo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class AbastecimentoRepositoryTest {

    @Autowired
    private AbastecimentoRepository abastecimentoRepository;

    @Autowired
    private VeiculoRepository veiculoRepository;

    private Veiculo veiculo;

    @BeforeEach
    void setUp() {
        veiculo = new Veiculo();
        veiculo.setPlaca("ABC-1234");
        veiculo.setModelo("Civic");
        veiculo.setAno(2020);
        veiculo.setQuilometragemAtual(50000.0);
        veiculo = veiculoRepository.save(veiculo);

        Abastecimento a1 = new Abastecimento();
        a1.setData(LocalDate.of(2024, 6, 10));
        a1.setLitros(40.0);
        a1.setPrecoLitro(5.50);
        a1.setQuilometragem(50000.0);
        a1.setTipoCombustivel(TipoCombustivel.GASOLINA);
        a1.setVeiculo(veiculo);

        Abastecimento a2 = new Abastecimento();
        a2.setData(LocalDate.of(2024, 6, 20));
        a2.setLitros(30.0);
        a2.setPrecoLitro(5.00);
        a2.setQuilometragem(50400.0);
        a2.setTipoCombustivel(TipoCombustivel.GASOLINA);
        a2.setVeiculo(veiculo);

        abastecimentoRepository.save(a1);
        abastecimentoRepository.save(a2);
    }

    @Test
    void deveSomarCustoCombustivelNoPeriodo() {
        Double resultado = abastecimentoRepository.somarCustoCombustivelPorPeriodo(
                veiculo,
                LocalDate.of(2024, 6, 1),
                LocalDate.of(2024, 6, 30)
        );
        assertThat(resultado).isEqualTo(370.0);
    }

    @Test
    void deveSomarQuilometragemNoPeriodo() {
        Double resultado = abastecimentoRepository.somarQuilometragemPorPeriodo(
                veiculo,
                LocalDate.of(2024, 6, 1),
                LocalDate.of(2024, 6, 30)
        );
        assertThat(resultado).isEqualTo(400.0);
    }

    @Test
    void deveRetornarNullQuandoNaoHaAbastecimentosNoPeriodo() {
        Double resultado = abastecimentoRepository.somarCustoCombustivelPorPeriodo(
                veiculo,
                LocalDate.of(2023, 1, 1),
                LocalDate.of(2023, 1, 31)
        );
        assertThat(resultado).isNull();
    }
}