package com.unifan.planar.entities;

import jakarta.persistence.*;
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

    public Veiculo() {
    }
    public Veiculo (long id,String placa ,String modelo ,int ano , double quilometragemataul ) {
        this.id = id;
        this.placa = placa;
        this.modelo= modelo;
        this.ano = ano;
        this.quilometragemAtual = quilometragemataul;

    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public Double getQuilometragemAtual() {
        return quilometragemAtual;
    }

    public void setQuilometragemAtual(Double quilometragemAtual) {
        this.quilometragemAtual = quilometragemAtual;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void atualizarQuilometragem(Double novaKm) {
        this.quilometragemAtual = novaKm;
    }

    public Double calcularMediaKmDiaria() {


        return quilometragemAtual;
    }

    public List<String> gerarAlertas() {

        List<String> alertas = new ArrayList<>();

        if (quilometragemAtual > 10000) {
            alertas.add("Veículo precisa de revisão");
        }

        return alertas;
    }
}