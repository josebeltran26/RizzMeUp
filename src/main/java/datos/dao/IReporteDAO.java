/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package datos.dao;

import java.util.List;
import org.bson.Document;

/**
 *
 * @author Roger Jr
 */
public interface IReporteDAO 
{
    boolean insertarReporte(Document doc);

    boolean existeReporte(String idReportante, String idReportado);

    List<Document> obtenerPendientes();

    Document obtenerPorId(String id);

    boolean actualizarEstado(String id, String estado);
}
