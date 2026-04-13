package com.mycompany.dto;

import java.time.LocalDateTime;

/**
 * Los mensajes del chat entre dos personas que ya hicieron match.
 * Guarda quien lo mando, que dijo y si ya lo leyo el otro.
 *
 * @author USUARIO
 */
public class MensajeDTO {

    private Long id;
    private Long matchId;
    private Long remitenteId;
    private Long destinatarioId;
    private String contenido;
    private LocalDateTime fechaEnvio;
    private boolean leido;

    public MensajeDTO() {
    }

    public MensajeDTO(Long id, Long matchId, Long remitenteId, Long destinatarioId,
            String contenido, LocalDateTime fechaEnvio, boolean leido) {
        this.id = id;
        this.matchId = matchId;
        this.remitenteId = remitenteId;
        this.destinatarioId = destinatarioId;
        this.contenido = contenido;
        this.fechaEnvio = fechaEnvio;
        this.leido = leido;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMatchId() {
        return matchId;
    }

    public void setMatchId(Long matchId) {
        this.matchId = matchId;
    }

    public Long getRemitenteId() {
        return remitenteId;
    }

    public void setRemitenteId(Long remitenteId) {
        this.remitenteId = remitenteId;
    }

    public Long getDestinatarioId() {
        return destinatarioId;
    }

    public void setDestinatarioId(Long destinatarioId) {
        this.destinatarioId = destinatarioId;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public LocalDateTime getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(LocalDateTime fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public boolean isLeido() {
        return leido;
    }

    public void setLeido(boolean leido) {
        this.leido = leido;
    }

    @Override
    public String toString() {
        return "MensajeDTO{"
                + "id=" + id
                + ", matchId=" + matchId
                + ", remitenteId=" + remitenteId
                + ", destinatarioId=" + destinatarioId
                + ", contenido='" + contenido + '\''
                + ", fechaEnvio=" + fechaEnvio
                + ", leido=" + leido
                + '}';
    }
}
