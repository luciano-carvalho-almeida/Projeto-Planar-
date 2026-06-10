package com.unifan.planar.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "alertas")
public class Alerta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TipoAlerta tipoAlerta;

    private String mensaje;
    private LocalDateTime dataGeracao;
    private Boolean lida;

    public Alerta() {
        this.dataGeracao = LocalDateTime.now();
        this.lida = false;
    }

    public Alerta(TipoAlerta tipoAlerta, String mensagem) {
        this();
        this.tipoAlerta = tipoAlerta;
        this.mensaje = mensagem;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public TipoAlerta getTipoAlerta() { return tipoAlerta; }
    public void setTipoAlerta(TipoAlerta tipoAlerta) { this.tipoAlerta = tipoAlerta; }
    public String getMensagem() { return mensaje; }
    public void setMensagem(String mensagem) { this.mensaje = mensagem; }
    public LocalDateTime getDataGeracao() { return dataGeracao; }
    public void setDataGeracao(LocalDateTime dataGeracao) { this.dataGeracao = dataGeracao; }
    public Boolean getLida() { return lida; }
    public void setLida(Boolean lida) { this.lida = lida; }
}
