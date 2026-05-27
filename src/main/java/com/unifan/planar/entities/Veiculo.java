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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public double getQuilometragemAtual() {
        return quilometragemAtual;
    }

    public void setQuilometragemAtual(double quilometragemAtual) {
        this.quilometragemAtual = quilometragemAtual;
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