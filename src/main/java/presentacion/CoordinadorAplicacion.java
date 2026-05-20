/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentacion;

import javax.swing.JFrame;

/**
 *
 * @author Roger Jr
 */
public class CoordinadorAplicacion 
{
    private static CoordinadorAplicacion instancia;

    // referencia al contenedor principal del equipo
    // se inyecta desde afuera cuando se integra al proyecto del equipo
    private IContenedorPrincipal contenedorPrincipal;

    private CoordinadorAplicacion()
    {
    }

    public static CoordinadorAplicacion getInstancia()
    {
        if (instancia == null)
        {
            instancia = new CoordinadorAplicacion();
        }
        return instancia;
    }

    // se llama desde el proyecto del equipo para registrar el contenedor
    public void setContenedorPrincipal(IContenedorPrincipal contenedor)
    {
        this.contenedorPrincipal = contenedor;
    }

    // ── dialogos modales (se abren encima de lo que haya) ──

    public void mostrarReportarUsuario(
            JFrame padre,
            String idUsuarioReportante,
            String idUsuarioReportado)
    {
        FrmReportarUsuario dialogo = new FrmReportarUsuario(
                padre,
                idUsuarioReportante,
                idUsuarioReportado);
        dialogo.setLocationRelativeTo(padre);
        dialogo.setVisible(true);
    }

    public void mostrarConfirmacion(
            JFrame padre,
            boolean exitoso,
            String mensaje)
    {
        FrmConfirmacionReporte dialogo = new FrmConfirmacionReporte(
                padre,
                exitoso,
                mensaje);
        dialogo.setLocationRelativeTo(padre);
        dialogo.setVisible(true);
    }

    // ── paneles intercambiables (requieren contenedor) ──

    public void mostrarBandejaReportes()
    {
        if (contenedorPrincipal != null)
        {
            contenedorPrincipal.mostrarPanel(new FrmBandejaReportes());
        }
    }

    public void mostrarDetalleReporte(String idReporte)
    {
        if (contenedorPrincipal != null)
        {
            contenedorPrincipal.mostrarPanel(
                    new FrmDetalleReporte(idReporte));
        }
    }

    public void mostrarExitoEvaluacion()
    {
        if (contenedorPrincipal != null)
        {
            contenedorPrincipal.mostrarPanel(
                    new FrmExitoEvaluacion(contenedorPrincipal));
        }
    }

    public void reset()
    {
        instancia = null;
        contenedorPrincipal = null;
    }
}
