//package com.unifan.planar.service;
//
//import com.unifan.planar.dto.ProjecaoGastosDTO;
//import com.unifan.planar.dto.RelatorioMensalDTO;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDate;
//import java.time.YearMonth;
//import java.util.List;
//
//@Service
//public class FinanceiroService {
//
//    @Autowired
//    private ServicoManutencaoRepository manutencaoRepository;
//
//    @Autowired
//    private AbastecimentoRepository abastecimentoRepository;
//
//    public RelatorioMensalDTO gerarRelatorioMensal(Veiculo veiculo, YearMonth mes){
//        LocalDate dataInicio = mes.atDay(1);
//        LocalDate dataFim = mes.atEndOfMonth();
//
//        Double totalManutencaoBruto = manutencaoRepository.somarCustoPorPeriodo(veiculo, dataInicio, dataFim);
//        Double totalCombustivelBruto = abastecimentoRepository.somarCustoCombustivelPorPeriodo(veiculo, dataInicio, dataFim);
//        Double quilometragemBruta = abastecimentoRepository.somarQuilometragemPorPeriodo(veiculo, dataInicio, dataFim);
//
//        Double totalManutencao = totalManutencaoBruto != null ? totalManutencaoBruto : 0.0;
//        Double totalCombustivel = totalCombustivelBruto != null ? totalCombustivelBruto : 0.0;
//        Double quilometragem = quilometragemBruta != null ? quilometragemBruta : 0.0;
//
//        return new RelatorioMensalDTO(mes, totalManutencao, totalCombustivel, quilometragem);
//    }
//
//    public double calcularRegressaoLinear(List<Integer> diasHistorico, List<Double> custosHistorico, Integer diaFuturo) {
//        int n = diasHistorico.size();
//
//        if (n == 0 || n != custosHistorico.size()) {
//            return 0.0;
//        }
//
//        double sumX = 0.0;
//        double sumY = 0.0;
//        double sumXY = 0.0;
//        double sumXX = 0.0;
//
//        for (int i= 0; i < n; i++){
//            sumX += diasHistorico.get(i);
//            sumY += custosHistorico.get(i);
//            sumXY += diasHistorico.get(i) * custosHistorico.get(i);
//            sumXX += diasHistorico.get(i) * diasHistorico.get(i);
//        }
//
//        double m = (n * sumXY - sumX * sumY) / (n * sumXX - sumX * sumX);
//        double b = (sumY - m * sumX) / n;
//
//        return m * diaFuturo + b;
//    }
//
//    public ProjecaoGastosDTO projetarGastos(Veiculo veiculo) {
//        List<Integer> diasHistorico = List.of(1, 2, 3, 4, 5, 6, 7);
//        List<Double> custosHistorico = List.of(12.5, 25.0, 39.2, 51.0, 65.5, 76.0, 89.0);
//
//        Double projecaoMensal = calcularRegressaoLinear(diasHistorico, custosHistorico, 30);
//        Double projecaoAnual = calcularRegressaoLinear(diasHistorico, custosHistorico, 365);
//
//        Double mediaKmDiaria = 38.5;
//        Double custoMedioDiario = custosHistorico.get(custosHistorico.size() - 1) / diasHistorico.size();
//
//        return new ProjecaoGastosDTO(mediaKmDiaria, custoMedioDiario, projecaoMensal, projecaoAnual);
//    }
//
//}
