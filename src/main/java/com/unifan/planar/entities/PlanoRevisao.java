package com.unifan.planar.entities;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "planos_revisao")
public class PlanoRevisao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;
    private Double intervaloKm;
    private Integer intervaloMeses;
    private Double ultimaRealizacaoKm;
    private LocalDate ultimaRealizacaoData;

    public Double proximaRevisaoKm() {
        if (ultimaRealizacaoKm == null) return intervaloKm;
        return ultimaRealizacaoKm + intervaloKm;
    }

    public LocalDate proximaRevisaoData() {
        if (ultimaRealizacaoData == null) return null;
        return ultimaRealizacaoData.plusMonths(intervaloMeses);
    }

    public Boolean isNecessario(Double kmAtual, LocalDate dataAtual) {
        if (kmAtual == null || dataAtual == null) {
            return false;
        }

        boolean porKm = kmAtual >= proximaRevisaoKm();
        boolean porData = proximaRevisaoData() != null && !dataAtual.isBefore(proximaRevisaoData());

        return porKm || porData;
    }

    public PlanoRevisao() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public Double getIntervaloKm() { return intervaloKm; }
    public void setIntervaloKm(Double intervaloKm) { this.intervaloKm = intervaloKm; }
    public Integer getIntervaloMeses() { return intervaloMeses; }
    public void setIntervaloMeses(Integer intervaloMeses) { this.intervaloMeses = intervaloMeses; }
    public Double getUltimaRealizacaoKm() { return ultimaRealizacaoKm; }
    public void setUltimaRealizacaoKm(Double ultimaRealizacaoKm) { this.ultimaRealizacaoKm = ultimaRealizacaoKm; }
    public LocalDate getUltimaRealizacaoData() { return ultimaRealizacaoData; }
    public void setUltimaRealizacaoData(LocalDate ultimaRealizacaoData) { this.ultimaRealizacaoData = ultimaRealizacaoData; }
}
