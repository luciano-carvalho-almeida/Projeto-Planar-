package com.unifan.planar.dto;

import com.unifan.planar.entities.TipoCombustivel;
import java.time.LocalDate;

public class AbastecimentoDTO {
    private LocalDate data;
    private Double quilometragem;
    private Double litros;
    private TipoCombustivel tipoCombustivel;
    private Double precoLitro;
    private Long veiculoId;

    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }
    public Double getQuilometragem() { return quilometragem; }
    public void setQuilometragem(Double quilometragem) { this.quilometragem = quilometragem; }
    public Double getLitros() { return litros; }
    public void setLitros(Double litros) { this.litros = litros; }
    public TipoCombustivel getTipoCombustivel() { return tipoCombustivel; }
    public void setTipoCombustivel(TipoCombustivel tipoCombustivel) { this.tipoCombustivel = tipoCombustivel; }
    public Double getPrecoLitro() { return precoLitro; }
    public void setPrecoLitro(Double precoLitro) { this.precoLitro = precoLitro; }
    public Long getVeiculoId() { return veiculoId; }
    public void setVeiculoId(Long veiculoId) { this.veiculoId = veiculoId; }
}
