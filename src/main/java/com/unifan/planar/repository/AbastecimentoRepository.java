package com.unifan.planar.repository;

import com.unifan.planar.entities.Abastecimento;
import com.unifan.planar.entities.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface AbastecimentoRepository extends JpaRepository<Abastecimento, Long> {

    @Query("SELECT SUM(a.litros * a.precoLitro) FROM Abastecimento a WHERE a.veiculo = :veiculo AND a.data BETWEEN :inicio AND :fim")
    Double somarCustoCombustivelPorPeriodo(Veiculo veiculo, LocalDate inicio, LocalDate fim);

    @Query("SELECT MAX(a.quilometragem) - MIN(a.quilometragem) FROM Abastecimento a WHERE a.veiculo = :veiculo AND a.data BETWEEN :inicio AND :fim")
    Double somarQuilometragemPorPeriodo(Veiculo veiculo, LocalDate inicio, LocalDate fim);
}
