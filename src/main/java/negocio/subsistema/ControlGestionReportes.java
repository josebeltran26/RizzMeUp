/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio.subsistema;

import infraestructura.InterfazCorreos;
import jakarta.mail.MessagingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import negocio.bo.IReporteBO;
import negocio.bo.ISancionBO;
import negocio.bo.IUsuarioBO;
import negocio.bo.ReporteBO;
import negocio.bo.SancionBO;
import negocio.bo.UsuarioBO;
import negocio.dto.ReporteDTO;
import negocio.dto.ReporteDetalleDTO;
import negocio.dto.SancionDTO;
import negocio.dto.UsuarioDTO;

/**
 *
 * @author Roger Jr
 */
public class ControlGestionReportes implements IGestionReportes
{
    private static ControlGestionReportes instancia;
    private final IReporteBO reporteBO;
    private final ISancionBO sancionBO;
    private final IUsuarioBO usuarioBO;
    private final InterfazCorreos correoInterfaz;

    private ControlGestionReportes()
    {
        reporteBO = ReporteBO.getInstancia();
        sancionBO = SancionBO.getInstancia();
        usuarioBO = UsuarioBO.getInstancia();
        correoInterfaz = InterfazCorreos.getInstancia();
    }

    public static ControlGestionReportes getInstancia()
    {
        if (instancia == null)
        {
            instancia = new ControlGestionReportes();
        }
        return instancia;
    }

    @Override
    public boolean crearReporte(ReporteDTO dto)
    {
        try
        {
            // verifica que no exista ya un reporte pendiente entre los mismos usuarios
            boolean duplicado = reporteBO.validarDuplicado(
                    dto.getIdUsuarioReportante(),
                    dto.getIdUsuarioReportado());

            if (duplicado)
            {
                System.err.println("ya existe un reporte pendiente para este usuario");
                return false;
            }

            boolean creado = reporteBO.crearReporte(dto);

            if (creado)
            {
                // notifica al usuario reportado por correo
                UsuarioDTO reportado = usuarioBO.obtenerDatos(
                        dto.getIdUsuarioReportado());

                if (reportado != null && reportado.getCorreo() != null)
                {
                    try
                    {
                        correoInterfaz.enviarNotificacionReporte(
                                reportado.getCorreo(),
                                "tu perfil ha recibido un reporte por: "
                                + dto.getMotivo()
                                + ". el equipo de rizz me up lo revisara pronto.");
                    }
                    catch (MessagingException e)
                    {
                        // el correo falla pero el reporte ya fue creado
                        System.err.println("error al enviar notificacion de reporte: "
                                + e.getMessage());
                    }
                }
            }

            return creado;
        }
        catch (Exception e)
        {
            System.err.println("error al crear reporte: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean validarDuplicado(String idReportante, String idReportado)
    {
        try
        {
            return reporteBO.validarDuplicado(idReportante, idReportado);
        }
        catch (Exception e)
        {
            System.err.println("error al validar duplicado: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<ReporteDetalleDTO> obtenerPendientes()
    {
        try
        {
            return reporteBO.obtenerPendientes();
        }
        catch (Exception e)
        {
            System.err.println("error al obtener pendientes: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public ReporteDetalleDTO consultarDetalle(String idReporte)
    {
        try
        {
            return reporteBO.consultarDetalle(idReporte);
        }
        catch (Exception e)
        {
            System.err.println("error al consultar detalle: " + e.getMessage());
            return null;
        }
    }

    @Override
    public boolean procesarSancion(String idReporte, String accion)
    {
        try
        {
            // obtiene el detalle del reporte para saber quien es el reportado
            ReporteDetalleDTO detalle = reporteBO.consultarDetalle(idReporte);
            if (detalle == null)
            {
                System.err.println("no se encontro el reporte con id: " + idReporte);
                return false;
            }

            // construye el dto de sancion segun la accion elegida por el admin
            SancionDTO sancionDTO = new SancionDTO();
            sancionDTO.setIdReporte(idReporte);
            sancionDTO.setIdUsuarioSancionado(
                    detalle.getReportado().getId());
            sancionDTO.setTipoSancion(accion);
            sancionDTO.setFechaSancion(new Date());
            sancionDTO.setDescripcion(
                    "sancion aplicada por reporte: " + detalle.getMotivo());

            boolean procesado = sancionBO.procesarSancion(sancionDTO);

            if (procesado)
            {
                // actualiza el estado del reporte a resuelto
                actualizarEstadoReporte(idReporte, "RESUELTO");

                // notifica al usuario sancionado
                UsuarioDTO sancionado = usuarioBO.obtenerDatos(
                        detalle.getReportado().getId());

                if (sancionado != null && sancionado.getCorreo() != null)
                {
                    try
                    {
                        correoInterfaz.enviarNotificacionSancion(
                                sancionado.getCorreo(),
                                "se ha aplicado una sancion a tu cuenta: "
                                + accion
                                + ". motivo: "
                                + detalle.getMotivo());
                    }
                    catch (MessagingException e)
                    {
                        // la sancion ya fue procesada, el correo es secundario
                        System.err.println("error al enviar notificacion de sancion: "
                                + e.getMessage());
                    }
                }
            }

            return procesado;
        }
        catch (Exception e)
        {
            System.err.println("error al procesar sancion: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean actualizarEstadoReporte(String idReporte, String estado)
    {
        try 
        {
            return actualizarEstado(idReporte, estado);
        } 
        catch (Exception e) 
        {
            System.err.println("error al actualizar estado: " + e.getMessage());
            return false;
        }
    }

    // delega la actualizacion de estado directamente al dao via bo
    private boolean actualizarEstado(String idReporte, String estado)
    {
        try
        {
            datos.dao.ReporteDAO.getInstancia().actualizarEstado(idReporte, estado);
            return true;
        }
        catch (Exception e)
        {
            System.err.println("error al actualizar estado del reporte: "
                    + e.getMessage());
            return false;
        }
    }
}
