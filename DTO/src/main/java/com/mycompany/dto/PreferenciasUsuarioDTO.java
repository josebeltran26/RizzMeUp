package com.mycompany.dto;

import java.util.List;

/**
 * Aqui se guardan las preferencias del usuario
 *
 * @author USUARIO
 */
public class PreferenciasUsuarioDTO {

    private Long id;
    private Long usuarioId;
    private String generoBuscado;
    private int edadMinima;
    private int edadMaxima;
    private String ciudadPreferida;

    private boolean interesEstudios;
    private List<GeneroMusical> generosMusicalPreferidos;
    private List<Hobbie> hobbiesPreferidos;
    private List<MateriaEscolar> materiasPreferidas;
    private List<String> signosPreferidos;

    public PreferenciasUsuarioDTO() {
    }

    public PreferenciasUsuarioDTO(Long id, Long usuarioId, String generoBuscado,
            int edadMinima, int edadMaxima, String ciudadPreferida,
            boolean interesEstudios, List<GeneroMusical> generosMusicalPreferidos,
            List<Hobbie> hobbiesPreferidos, List<MateriaEscolar> materiasPreferidas,
            List<String> signosPreferidos) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.generoBuscado = generoBuscado;
        this.edadMinima = edadMinima;
        this.edadMaxima = edadMaxima;
        this.ciudadPreferida = ciudadPreferida;
        this.interesEstudios = interesEstudios;
        this.generosMusicalPreferidos = generosMusicalPreferidos;
        this.hobbiesPreferidos = hobbiesPreferidos;
        this.materiasPreferidas = materiasPreferidas;
        this.signosPreferidos = signosPreferidos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getGeneroBuscado() {
        return generoBuscado;
    }

    public void setGeneroBuscado(String generoBuscado) {
        this.generoBuscado = generoBuscado;
    }

    public int getEdadMinima() {
        return edadMinima;
    }

    public void setEdadMinima(int edadMinima) {
        this.edadMinima = edadMinima;
    }

    public int getEdadMaxima() {
        return edadMaxima;
    }

    public void setEdadMaxima(int edadMaxima) {
        this.edadMaxima = edadMaxima;
    }

    public String getCiudadPreferida() {
        return ciudadPreferida;
    }

    public void setCiudadPreferida(String ciudadPreferida) {
        this.ciudadPreferida = ciudadPreferida;
    }

    public boolean isInteresEstudios() {
        return interesEstudios;
    }

    public void setInteresEstudios(boolean interesEstudios) {
        this.interesEstudios = interesEstudios;
    }

    public List<GeneroMusical> getGenerosMusicalPreferidos() {
        return generosMusicalPreferidos;
    }

    public void setGenerosMusicalPreferidos(List<GeneroMusical> generosMusicalPreferidos) {
        this.generosMusicalPreferidos = generosMusicalPreferidos;
    }

    public List<Hobbie> getHobbiesPreferidos() {
        return hobbiesPreferidos;
    }

    public void setHobbiesPreferidos(List<Hobbie> hobbiesPreferidos) {
        this.hobbiesPreferidos = hobbiesPreferidos;
    }

    public List<MateriaEscolar> getMateriasPreferidas() {
        return materiasPreferidas;
    }

    public void setMateriasPreferidas(List<MateriaEscolar> materiasPreferidas) {
        this.materiasPreferidas = materiasPreferidas;
    }

    public List<String> getSignosPreferidos() {
        return signosPreferidos;
    }

    public void setSignosPreferidos(List<String> signosPreferidos) {
        this.signosPreferidos = signosPreferidos;
    }

    @Override
    public String toString() {
        return "PreferenciasUsuarioDTO{"
                + "id=" + id
                + ", usuarioId=" + usuarioId
                + ", generoBuscado='" + generoBuscado + '\''
                + ", edadMinima=" + edadMinima
                + ", edadMaxima=" + edadMaxima
                + ", ciudadPreferida='" + ciudadPreferida + '\''
                + ", interesEstudios=" + interesEstudios
                + ", generosMusicalPreferidos=" + generosMusicalPreferidos
                + ", hobbiesPreferidos=" + hobbiesPreferidos
                + ", materiasPreferidas=" + materiasPreferidas
                + ", signosPreferidos=" + signosPreferidos
                + '}';
    }
}
