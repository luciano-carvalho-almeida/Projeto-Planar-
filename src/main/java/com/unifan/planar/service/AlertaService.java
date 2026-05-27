package com.unifan.planar.service;

import com.unifan.planar.entities.Alerta;
import com.unifan.planar.entities.PlanoRevisao;
import com.unifan.planar.entities.TipoAlerta;
import com.unifan.planar.repository.AlertaRepository;
import com.unifan.planar.repository.PlanoRevisaoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class AlertaService {

    private final PlanoRevisaoRepository planoRevisaoRepository;
    private final AlertaRepository alertaRepository;

    public AlertaService(PlanoRevisaoRepository planoRevisaoRepository, AlertaRepository alertaRepository) {
        this.planoRevisaoRepository = planoRevisaoRepository;
        this.alertaRepository = alertaRepository;
    }

    @Transactional
    public List<Alerta> gerarAlertasManutencao(Double kmAtual, LocalDate dataAtual) {
        List<PlanoRevisao> planos = planoRevisaoRepository.findAll();
        List<Alerta> novosAlertas = new ArrayList<>();

        for (PlanoRevisao plano : planos) {

            if (plano.isNecessario(kmAtual, dataAtual)) {
                String msg = String.format("Atenção! A revisão '%s' está vencida ou próxima. Projeção: %.0f Km ou data limite atingida.",
                        plano.getDescricao(), plano.proximaRevisaoKm());

                Alerta alerta = new Alerta(TipoAlerta.MANUTENCAO_PLANO, msg);
                novosAlertas.add(alerta);
            }
        }

        if (!novosAlertas.isEmpty()) {
            return alertaRepository.saveAll(novosAlertas);
        }

        return novosAlertas;
    }
}