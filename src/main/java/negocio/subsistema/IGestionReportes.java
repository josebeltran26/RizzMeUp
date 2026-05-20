/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package negocio.subsistema;

import java.util.List;
import negocio.dto.ReporteDTO;
import negocio.dto.ReporteDetalleDTO;

/**
 *
 * @author Roger Jr
 */
public interface IGestionReportes 
{
    boolean crearReporte(ReporteDTO dto);

    boolean validarDuplicado(String idReportante, String idReportado);

    List<ReporteDetalleDTO> obtenerPendientes();

    ReporteDetalleDTO consultarDetalle(String idReporte);

    boolean procesarSancion(String idReporte, String accion);

    boolean actualizarEstadoReporte(String idReporte, String estado);
}
