package com.unifan.planar.entities;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.assertj.core.api.Assertions.assertThat;

class ServicoManutencaoTest {

    @Test
    void deveAssociarPlanoEAtualizarDados() {
        PlanoRevisao plano = new PlanoRevisao();
        plano.setIntervaloKm(10000.0);
        plano.setIntervaloMeses(6);

        ServicoManutencao servico = new ServicoManutencao();
        servico.setQuilometragemRealizacao(95000.0);
        servico.setDataRealizacao(LocalDate.of(2024, 6, 1));

        servico.associarPlano(plano);

        assertThat(servico.getPlanoRevisao()).isEqualTo(plano);
        assertThat(plano.getUltimaRealizacaoKm()).isEqualTo(95000.0);
        assertThat(plano.getUltimaRealizacaoData()).isEqualTo(LocalDate.of(2024, 6, 1));
    }

    @Test
    void naoDeveAtualizarPlanoQuandoForNulo() {
        ServicoManutencao servico = new ServicoManutencao();
        servico.setQuilometragemRealizacao(95000.0);
        servico.associarPlano(null);
        assertThat(servico.getPlanoRevisao()).isNull();
    }
}