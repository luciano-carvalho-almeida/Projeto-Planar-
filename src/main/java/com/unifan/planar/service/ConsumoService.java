package com.unifan.planar.service;

import com.unifan.planar.entities.TipoCombustivel;
import org.springframework.stereotype.Service;

@Service
public class ConsumoService {

    public Double calcularEficiencia(Double kmPercorridos, Double litrosConsumidos) {

        if (litrosConsumidos == 0) {
            return 0.0;
        }

        return kmPercorridos / litrosConsumidos;
    }

    public TipoCombustivel recomendarCombustivel(
            Double precoEtanol,
            Double precoGasolina) {

        if ((precoEtanol / precoGasolina) <= 0.70) {
            return TipoCombustivel.ETANOL;
        }

        return TipoCombustivel.GASOLINA;
    }
}