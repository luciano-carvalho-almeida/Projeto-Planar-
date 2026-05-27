//package com.unifan.planar.controller;
//
//import com.unifan.planar.dto.ProjecaoGastosDTO;
//import com.unifan.planar.dto.RelatorioMensalDTO;
//import com.unifan.planar.entities.Veiculo;
//import com.unifan.planar.repository.VeiculoRepository;
//import com.unifan.planar.service.FinanceiroService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.time.YearMonth;
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/api/financeiro")
//public class FinanceiroController {
//
//    @Autowired
//    private FinanceiroService financeiroService;
//
//    @Autowired
//    private VeiculoRepository veiculoRepository;
//
//    @GetMapping("/relatorio/{veiculoId}")
//    public ResponseEntity<RelatorioMensalDTO> obterRelatorioMensal(
//            @PathVariable Long veiculoId,
//            @RequestParam int ano,
//            @RequestParam int mes) {
//
//        Optional<Veiculo> veiculoOpt = veiculoRepository.findById(veiculoId);
//
//        if (veiculoOpt.isEmpty()) {
//            return ResponseEntity.notFound().build();
//        }
//
//        YearMonth anoMes = YearMonth.of(ano, mes);
//        RelatorioMensalDTO relatorio = financeiroService.gerarRelatorioMensal(veiculoOpt.get(), anoMes);
//
//        return ResponseEntity.ok(relatorio);
//    }
//
//    @GetMapping("/projecao/{veiculoId}")
//    public ResponseEntity<ProjecaoGastosDTO> obterProjecaoGastos(@PathVariable Long veiculoId) {
//
//        Optional<Veiculo> veiculoOpt = veiculoRepository.findById(veiculoId);
//
//        if (veiculoOpt.isEmpty()) {
//            return ResponseEntity.notFound().build();
//        }
//
//        ProjecaoGastosDTO projecao = financeiroService.projetarGastos(veiculoOpt.get());
//
//        return ResponseEntity.ok(projecao);
//    }
//}