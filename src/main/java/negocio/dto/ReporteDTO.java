/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio.dto;

import java.util.Date;
import java.util.List;

/**
 *
 * @author Roger Jr
 */
public class ReporteDTO 
{
    private String id;
    private String idUsuarioReportante;
    private String idUsuarioReportado;
    private String motivo;
    private String estado;
    private Date fecha;
    private SancionDTO sancion;
    // lista de evidencias incrustada en el documento mongo
    private List<String> evidencias;

    public ReporteDTO()
    {
    }

    public ReporteDTO(
            String id,
            String idUsuarioReportante,
            String idUsuarioReportado,
            String motivo,
            String estado,
            Date fecha,
            SancionDTO sancion,
            List<String> evidencias)
    {
        this.id = id;
        this.idUsuarioReportante = idUsuarioReportante;
        this.idUsuarioReportado = idUsuarioReportado;
        this.motivo = motivo;
        this.estado = estado;
        this.fecha = fecha;
        this.sancion = sancion;
        this.evidencias = evidencias;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getIdUsuarioReportante()
    {
        return idUsuarioReportante;
    }

    public void setIdUsuarioReportante(String idUsuarioReportante)
    {
        this.idUsuarioReportante = idUsuarioReportante;
    }

    public String getIdUsuarioReportado()
    {
        return idUsuarioReportado;
    }

    public void setIdUsuarioReportado(String idUsuarioReportado)
    {
        this.idUsuarioReportado = idUsuarioReportado;
    }

    public String getMotivo()
    {
        return motivo;
    }

    public void setMotivo(String motivo)
    {
        this.motivo = motivo;
    }

    public String getEstado()
    {
        return estado;
    }

    public void setEstado(String estado)
    {
        this.estado = estado;
    }

    public Date getFecha()
    {
        return fecha;
    }

    public void setFecha(Date fecha)
    {
        this.fecha = fecha;
    }

    public SancionDTO getSancion()
    {
        return sancion;
    }

    public void setSancion(SancionDTO sancion)
    {
        this.sancion = sancion;
    }

    public List<String> getEvidencias()
    {
        return evidencias;
    }

    public void setEvidencias(List<String> evidencias)
    {
        this.evidencias = evidencias;
    }
}
