/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datos.entidades;

import java.util.Date;
import java.util.List;

/**
 *
 * @author Roger Jr
 */
public class Reporte 
{
    private String id;
    private String idUsuarioReportante;
    private String idUsuarioReportado;
    private String motivo;
    private String estado;
    private Date fecha;
    // lista de evidencias incrustada directamente en el documento mongo
    private List<String> evidencias;
    private Sancion sancion;

    // constructor vacio
    public Reporte() {
    }

    // constructor completo
    public Reporte(
            String id,
            String idUsuarioReportante,
            String idUsuarioReportado,
            String motivo,
            String estado,
            Date fecha,
            List<String> evidencias,
            Sancion sancion) {
        this.id = id;
        this.idUsuarioReportante = idUsuarioReportante;
        this.idUsuarioReportado = idUsuarioReportado;
        this.motivo = motivo;
        this.estado = estado;
        this.fecha = fecha;
        this.evidencias = evidencias;
        this.sancion = sancion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdUsuarioReportante() {
        return idUsuarioReportante;
    }

    public void setIdUsuarioReportante(String idUsuarioReportante) {
        this.idUsuarioReportante = idUsuarioReportante;
    }

    public String getIdUsuarioReportado() {
        return idUsuarioReportado;
    }

    public void setIdUsuarioReportado(String idUsuarioReportado) {
        this.idUsuarioReportado = idUsuarioReportado;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public List<String> getEvidencias() {
        return evidencias;
    }

    public void setEvidencias(List<String> evidencias) {
        this.evidencias = evidencias;
    }

    public Sancion getSancion() {
        return sancion;
    }

    public void setSancion(Sancion sancion) {
        this.sancion = sancion;
    }
}
