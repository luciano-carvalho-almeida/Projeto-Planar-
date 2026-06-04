package com.unifan.planar.dto;

import java.time.LocalDate;

public class ServicoManutencaoDTO {
    private LocalDate dataRealizacao;
    private Double quilometragemRealizacao;
    private String descricao;
    private Double custo;
    private String observacoes;
    private Long veiculoId;
    private Long planoId;

    public LocalDate getDataRealizacao() { return dataRealizacao; }
    public void setDataRealizacao(LocalDate dataRealizacao) { this.dataRealizacao = dataRealizacao; }
    public Double getQuilometragemRealizacao() { return quilometragemRealizacao; }
    public void setQuilometragemRealizacao(Double quilometragemRealizacao) { this.quilometragemRealizacao = quilometragemRealizacao; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public Double getCusto() { return custo; }
    public void setCusto(Double custo) { this.custo = custo; }
    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }
    public Long getVeiculoId() { return veiculoId; }
    public void setVeiculoId(Long veiculoId) { this.veiculoId = veiculoId; }
    public Long getPlanoId() { return planoId; }
    public void setPlanoId(Long planoId) { this.planoId = planoId; }
}
