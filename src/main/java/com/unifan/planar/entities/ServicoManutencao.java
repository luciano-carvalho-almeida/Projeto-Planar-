package com.unifan.planar.entities;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "servicos_manutencao")
public class ServicoManutencao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dataRealizacao;
    private Double quilometragemRealizacao;
    private String descricao;
    private Double custo;

    @Column(columnDefinition = "TEXT")
    private String observacoes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plano_revisao_id")
    private PlanoRevisao planoRevisao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "veiculo_id")
    private Veiculo veiculo;


    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public void associarPlano(PlanoRevisao plano) {
        this.planoRevisao = plano;
        if (plano != null) {
            plano.setUltimaRealizacaoKm(this.quilometragemRealizacao);
            plano.setUltimaRealizacaoData(this.dataRealizacao);
        }
    }


    public ServicoManutencao() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
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
    public PlanoRevisao getPlanoRevisao() { return planoRevisao; }
    public void setPlanoRevisao(PlanoRevisao planoRevisao) { this.planoRevisao = planoRevisao; }
}
