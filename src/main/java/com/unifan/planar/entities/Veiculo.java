package com.unifan.planar.entities;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String placa;
    private String modelo;
    private Integer ano;
    private Double quilometragemAtual;
    private LocalDate dataAquisicao;

    public Veiculo() {
        this.dataAquisicao = LocalDate.now();
    }

    public Veiculo(Long id, String placa, String modelo, int ano, double quilometragemAtual) {
        this.id = id;
        this.placa = placa;
        this.modelo = modelo;
        this.ano = ano;
        this.quilometragemAtual = quilometragemAtual;
        this.dataAquisicao = LocalDate.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }
    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    public Integer getAno() { return ano; }
    public void setAno(Integer ano) { this.ano = ano; }
    public Double getQuilometragemAtual() { return quilometragemAtual; }
    public void setQuilometragemAtual(Double quilometragemAtual) { this.quilometragemAtual = quilometragemAtual; }
    public LocalDate getDataAquisicao() { return dataAquisicao; }
    public void setDataAquisicao(LocalDate dataAquisicao) { this.dataAquisicao = dataAquisicao; }

    public void atualizarQuilometragem(Double novaKm) {
        this.quilometragemAtual = novaKm;
    }

    public Double calcularMediaKmDiaria() {
        if (dataAquisicao == null) return 0.0;
        long dias = ChronoUnit.DAYS.between(dataAquisicao, LocalDate.now());
        if (dias == 0) return 0.0;
        return quilometragemAtual / dias;
    }

    public List<String> gerarAlertas() {
        List<String> alertas = new ArrayList<>();
        if (quilometragemAtual > 10000) {
            alertas.add("Veículo precisa de revisão");
        }
        return alertas;
    }
}
