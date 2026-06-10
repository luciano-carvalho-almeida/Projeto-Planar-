package com.unifan.planar.repository;

import com.unifan.planar.entities.ServicoManutencao;
import com.unifan.planar.entities.Veiculo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ServicoManutencaoRepositoryTest {

    @Autowired
    private ServicoManutencaoRepository servicoManutencaoRepository;

    @Autowired
    private VeiculoRepository veiculoRepository;

    private Veiculo veiculo;

    @BeforeEach
    void setUp() {
        veiculo = new Veiculo();
        veiculo.setPlaca("XYZ-9876");
        veiculo.setModelo("Corolla");
        veiculo.setAno(2021);
        veiculo.setQuilometragemAtual(80000.0);
        veiculo = veiculoRepository.save(veiculo);

        ServicoManutencao s1 = new ServicoManutencao();
        s1.setDescricao("Troca de óleo");
        s1.setCusto(250.0);
        s1.setDataRealizacao(LocalDate.of(2024, 6, 5));
        s1.setQuilometragemRealizacao(80000.0);
        s1.setVeiculo(veiculo);

        ServicoManutencao s2 = new ServicoManutencao();
        s2.setDescricao("Alinhamento");
        s2.setCusto(150.0);
        s2.setDataRealizacao(LocalDate.of(2024, 6, 15));
        s2.setQuilometragemRealizacao(80200.0);
        s2.setVeiculo(veiculo);

        servicoManutencaoRepository.save(s1);
        servicoManutencaoRepository.save(s2);
    }

    @Test
    void deveSomarCustoDeManutencaoNoPeriodo() {
        Double resultado = servicoManutencaoRepository.somarCustoPorPeriodo(
                veiculo,
                LocalDate.of(2024, 6, 1),
                LocalDate.of(2024, 6, 30)
        );
        assertThat(resultado).isEqualTo(400.0);
    }

    @Test
    void deveRetornarNullQuandoNaoHaServicosNoPeriodo() {
        Double resultado = servicoManutencaoRepository.somarCustoPorPeriodo(
                veiculo,
                LocalDate.of(2023, 1, 1),
                LocalDate.of(2023, 1, 31)
        );
        assertThat(resultado).isNull();
    }
}