package com.unifan.planar.repository;

import com.unifan.planar.entities.ServicoManutencao;
import com.unifan.planar.entities.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface ServicoManutencaoRepository extends JpaRepository<ServicoManutencao, Long> {
    
    @Query("SELECT SUM(s.custo) FROM ServicoManutencao s " +
           "WHERE s.veiculo = :veiculo " +
           "AND s.dataRealizacao BETWEEN :dataInicio AND :dataFim")
    Double somarCustoPorPeriodo(@Param("veiculo") Veiculo veiculo,
                                @Param("dataInicio") LocalDate dataInicio,
                                @Param("dataFim") LocalDate dataFim);
}
