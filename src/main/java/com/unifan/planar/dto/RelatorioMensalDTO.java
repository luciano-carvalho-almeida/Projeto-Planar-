package com.unifan.planar.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.YearMonth;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RelatorioMensalDTO {
        private YearMonth anoMes;
        private Double totalManutencao;
        private Double totalCombustivel;
        private Double quilometragemPercorrida;

        public Double getGastoTotal(){
            if (totalManutencao == null){
                totalManutencao = 0.0;
            }
            if (totalCombustivel == null){
                totalCombustivel = 0.0;
            }
            return totalManutencao + totalCombustivel;
        }
}


