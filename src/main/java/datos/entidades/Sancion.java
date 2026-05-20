/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datos.entidades;

import java.util.Date;

/**
 *
 * @author Roger Jr
 */
public class Sancion 
{
    private String id;
    private String idReporte;
    private String idUsuarioSancionado;
    private String tipoSancion;
    private Date fechaSancion;
    private String descripcion;

    // constructor vacio
    public Sancion() {
    }

    // constructor completo
    public Sancion(
            String id,
            String idReporte,
            String idUsuarioSancionado,
            String tipoSancion,
            Date fechaSancion,
            String descripcion) {
        this.id = id;
        this.idReporte = idReporte;
        this.idUsuarioSancionado = idUsuarioSancionado;
        this.tipoSancion = tipoSancion;
        this.fechaSancion = fechaSancion;
        this.descripcion = descripcion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdReporte() {
        return idReporte;
    }

    public void setIdReporte(String idReporte) {
        this.idReporte = idReporte;
    }

    public String getIdUsuarioSancionado() {
        return idUsuarioSancionado;
    }

    public void setIdUsuarioSancionado(String idUsuarioSancionado) {
        this.idUsuarioSancionado = idUsuarioSancionado;
    }

    public String getTipoSancion() {
        return tipoSancion;
    }

    public void setTipoSancion(String tipoSancion) {
        this.tipoSancion = tipoSancion;
    }

    public Date getFechaSancion() {
        return fechaSancion;
    }

    public void setFechaSancion(Date fechaSancion) {
        this.fechaSancion = fechaSancion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
