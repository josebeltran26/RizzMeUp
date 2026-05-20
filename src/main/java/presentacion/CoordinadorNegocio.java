/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentacion;

import java.util.ArrayList;
import java.util.List;
import negocio.dto.ReporteDTO;
import negocio.dto.ReporteDetalleDTO;
import negocio.dto.UsuarioDTO;
import negocio.subsistema.ControlGestionReportes;
import negocio.subsistema.ControlGestionUsuarios;
import negocio.subsistema.IGestionReportes;
import negocio.subsistema.IGestionUsuarios;

/**
 *
 * @author Roger Jr
 */
public class CoordinadorNegocio 
{
    private static CoordinadorNegocio instancia;
    private final IGestionReportes gestionReportes;
    private final IGestionUsuarios gestionUsuarios;

    private CoordinadorNegocio()
    {
        gestionReportes = ControlGestionReportes.getInstancia();
        gestionUsuarios = ControlGestionUsuarios.getInstancia();
    }

    public static CoordinadorNegocio getInstancia()
    {
        if (instancia == null)
        {
            instancia = new CoordinadorNegocio();
        }
        return instancia;
    }

    // ── metodos de reporte ──────────────────────────────

    public boolean crearReporte(ReporteDTO dto)
    {
        try
        {
            return gestionReportes.crearReporte(dto);
        }
        catch (Exception e)
        {
            System.err.println("error al crear reporte: " + e.getMessage());
            return false;
        }
    }

    public boolean validarReporteExistente(String idReportante, String idReportado)
    {
        try
        {
            return gestionReportes.validarDuplicado(idReportante, idReportado);
        }
        catch (Exception e)
        {
            System.err.println("error al validar reporte existente: "
                    + e.getMessage());
            return false;
        }
    }

    public List<ReporteDetalleDTO> obtenerReportesPendientes()
    {
        try
        {
            return gestionReportes.obtenerPendientes();
        }
        catch (Exception e)
        {
            System.err.println("error al obtener reportes pendientes: "
                    + e.getMessage());
            return new ArrayList<>();
        }
    }

    public ReporteDetalleDTO consultarDetallesReporte(String idReporte)
    {
        try
        {
            return gestionReportes.consultarDetalle(idReporte);
        }
        catch (Exception e)
        {
            System.err.println("error al consultar detalles del reporte: "
                    + e.getMessage());
            return null;
        }
    }

    public boolean procesarSancion(String idReporte, String accion)
    {
        try
        {
            return gestionReportes.procesarSancion(idReporte, accion);
        }
        catch (Exception e)
        {
            System.err.println("error al procesar sancion: " + e.getMessage());
            return false;
        }
    }

    public boolean actualizarEstadoReporte(String idReporte, String estado)
    {
        try
        {
            return gestionReportes.actualizarEstadoReporte(idReporte, estado);
        }
        catch (Exception e)
        {
            System.err.println("error al actualizar estado del reporte: "
                    + e.getMessage());
            return false;
        }
    }

    // ── metodos de usuario ──────────────────────────────

    public UsuarioDTO obtenerDatosUsuario(String id)
    {
        try
        {
            return gestionUsuarios.obtenerDatosUsuario(id);
        }
        catch (Exception e)
        {
            System.err.println("error al obtener datos de usuario: "
                    + e.getMessage());
            return null;
        }
    }

    public boolean validarEstadoCuenta(String id)
    {
        try
        {
            return gestionUsuarios.validarEstadoCuenta(id);
        }
        catch (Exception e)
        {
            System.err.println("error al validar estado de cuenta: "
                    + e.getMessage());
            return false;
        }
    }

    public void mostrarMensajeExito(String msg)
    {
        javax.swing.JOptionPane.showMessageDialog(
                null, msg, "exito",
                javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }

    public void mostrarMensajeError(String msg)
    {
        javax.swing.JOptionPane.showMessageDialog(
                null, msg, "error",
                javax.swing.JOptionPane.ERROR_MESSAGE);
    }

    public void reset()
    {
        instancia = null;
    }
}
