package com.unifan.planar.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjecaoGastosDTO {
    private Double mediaKmDiaria;
    private Double custoMedioDiario;
    private Double projecaoMensal;
    private Double projecaoAnual;
}
