package com.unifan.planar.service;

import com.unifan.planar.dto.ProjecaoGastosDTO;
import com.unifan.planar.dto.RelatorioMensalDTO;
import com.unifan.planar.entities.Veiculo;
import com.unifan.planar.repository.AbastecimentoRepository;
import com.unifan.planar.repository.ServicoManutencaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FinanceiroServiceTest {

    @Mock
    private ServicoManutencaoRepository manutencaoRepository;

    @Mock
    private AbastecimentoRepository abastecimentoRepository;

    @InjectMocks
    private FinanceiroService financeiroService;

    private Veiculo veiculo;
    private YearMonth mes;

    @BeforeEach
    void setUp() {
        veiculo = new Veiculo();
        mes = YearMonth.of(2024, 6);
    }

    // --- gerarRelatorioMensal ---

    @Test
    void deveGerarRelatorioMensalComValoresCorretos() {
        when(manutencaoRepository.somarCustoPorPeriodo(eq(veiculo), any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(350.0);
        when(abastecimentoRepository.somarCustoCombustivelPorPeriodo(eq(veiculo), any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(200.0);
        when(abastecimentoRepository.somarQuilometragemPorPeriodo(eq(veiculo), any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(1500.0);

        RelatorioMensalDTO resultado = financeiroService.gerarRelatorioMensal(veiculo, mes);

        assertThat(resultado.getAnoMes()).isEqualTo(mes);
        assertThat(resultado.getTotalManutencao()).isEqualTo(350.0);
        assertThat(resultado.getTotalCombustivel()).isEqualTo(200.0);
        assertThat(resultado.getQuilometragemPercorrida()).isEqualTo(1500.0);
        assertThat(resultado.getGastoTotal()).isEqualTo(550.0);
    }

    @Test
    void deveRetornarZerosQuandoRepositoriesRetornamNull() {
        when(manutencaoRepository.somarCustoPorPeriodo(eq(veiculo), any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(null);
        when(abastecimentoRepository.somarCustoCombustivelPorPeriodo(eq(veiculo), any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(null);
        when(abastecimentoRepository.somarQuilometragemPorPeriodo(eq(veiculo), any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(null);

        RelatorioMensalDTO resultado = financeiroService.gerarRelatorioMensal(veiculo, mes);

        assertThat(resultado.getTotalManutencao()).isEqualTo(0.0);
        assertThat(resultado.getTotalCombustivel()).isEqualTo(0.0);
        assertThat(resultado.getQuilometragemPercorrida()).isEqualTo(0.0);
        assertThat(resultado.getGastoTotal()).isEqualTo(0.0);
    }

    // --- calcularRegressaoLinear ---

    @Test
    void deveCalcularRegressaoLinearCorretamente() {
        List<Integer> dias = List.of(1, 2, 3, 4, 5);
        List<Double> custos = List.of(10.0, 20.0, 30.0, 40.0, 50.0);

        double resultado = financeiroService.calcularRegressaoLinear(dias, custos, 10);

        assertThat(resultado).isCloseTo(100.0, within(0.01));
    }

    @Test
    void deveRetornarZeroQuandoListasVazias() {
        double resultado = financeiroService.calcularRegressaoLinear(List.of(), List.of(), 30);
        assertThat(resultado).isEqualTo(0.0);
    }

    @Test
    void deveRetornarZeroQuandoTamanhosDasListasDiferem() {
        double resultado = financeiroService.calcularRegressaoLinear(List.of(1, 2, 3), List.of(10.0, 20.0), 30);
        assertThat(resultado).isEqualTo(0.0);
    }

    // --- projetarGastos ---

    @Test
    void deveProjetarGastosComCamposPreenchidos() {
        ProjecaoGastosDTO resultado = financeiroService.projetarGastos(veiculo);

        assertThat(resultado.getMediaKmDiaria()).isEqualTo(38.5);
        assertThat(resultado.getCustoMedioDiario()).isGreaterThan(0.0);
        assertThat(resultado.getProjecaoMensal()).isGreaterThan(0.0);
        assertThat(resultado.getProjecaoAnual()).isGreaterThan(resultado.getProjecaoMensal());
    }
}
