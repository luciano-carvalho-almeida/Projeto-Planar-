package com.unifan.planar.entities;

import java.time.LocalDate;

public class Abastecimento {

    private Long id;
    private LocalDate data;
    private Double quilometragem;
    private Double litros;
    private TipoCombustivel tipoCombustivel;
    private Double precoLitro;

    public Abastecimento() {
    }

    public Double getValorTotal() {
        return litros * precoLitro;
    }

    public Double calcularConsumo(Abastecimento anterior) {

        if (anterior == null) {
            return 0.0;
        }

        double distancia = this.quilometragem - anterior.getQuilometragem();

        return distancia / this.litros;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Double getQuilometragem() {
        return quilometragem;
    }

    public void setQuilometragem(Double quilometragem) {
        this.quilometragem = quilometragem;
    }

    public Double getLitros() {
        return litros;
    }

    public void setLitros(Double litros) {
        this.litros = litros;
    }

    public TipoCombustivel getTipoCombustivel() {
        return tipoCombustivel;
    }

    public void setTipoCombustivel(TipoCombustivel tipoCombustivel) {
        this.tipoCombustivel = tipoCombustivel;
    }

    public Double getPrecoLitro() {
        return precoLitro;
    }

    public void setPrecoLitro(Double precoLitro) {
        this.precoLitro = precoLitro;
    }
}