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
public class ReporteDetalleDTO 
{
    private String id;
    private UsuarioDTO reportante;
    private UsuarioDTO reportado;
    private String motivo;
    private String estado;
    private Date fecha;
    private SancionDTO sancion;
    private List<String> evidencias;

    public ReporteDetalleDTO()
    {
    }

    public ReporteDetalleDTO(
            String id,
            UsuarioDTO reportante,
            UsuarioDTO reportado,
            String motivo,
            String estado,
            Date fecha,
            SancionDTO sancion,
            List<String> evidencias)
    {
        this.id = id;
        this.reportante = reportante;
        this.reportado = reportado;
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

    public UsuarioDTO getReportante()
    {
        return reportante;
    }

    public void setReportante(UsuarioDTO reportante)
    {
        this.reportante = reportante;
    }

    public UsuarioDTO getReportado()
    {
        return reportado;
    }

    public void setReportado(UsuarioDTO reportado)
    {
        this.reportado = reportado;
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
