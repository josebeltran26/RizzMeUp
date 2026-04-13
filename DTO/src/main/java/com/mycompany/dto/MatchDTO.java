package com.mycompany.dto;

import java.time.LocalDateTime;

/**
 * Aqui se guarda cuando dos personas se dieron like mutuamente,
 * o sea hicieron match. Ahora si pueden hablar.
 *
 * @author USUARIO
 */
public class MatchDTO {

    private Long id;
    private Long usuario1Id;
    private Long usuario2Id;
    private LocalDateTime fechaMatch;
    private boolean activo;

    public MatchDTO() {
    }

    public MatchDTO(Long id, Long usuario1Id, Long usuario2Id,
            LocalDateTime fechaMatch, boolean activo) {
        this.id = id;
        this.usuario1Id = usuario1Id;
        this.usuario2Id = usuario2Id;
        this.fechaMatch = fechaMatch;
        this.activo = activo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUsuario1Id() {
        return usuario1Id;
    }

    public void setUsuario1Id(Long usuario1Id) {
        this.usuario1Id = usuario1Id;
    }

    public Long getUsuario2Id() {
        return usuario2Id;
    }

    public void setUsuario2Id(Long usuario2Id) {
        this.usuario2Id = usuario2Id;
    }

    public LocalDateTime getFechaMatch() {
        return fechaMatch;
    }

    public void setFechaMatch(LocalDateTime fechaMatch) {
        this.fechaMatch = fechaMatch;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    @Override
    public String toString() {
        return "MatchDTO{"
                + "id=" + id
                + ", usuario1Id=" + usuario1Id
                + ", usuario2Id=" + usuario2Id
                + ", fechaMatch=" + fechaMatch
                + ", activo=" + activo
                + '}';
    }
}
