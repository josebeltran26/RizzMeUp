/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package negocio.mapper;

import datos.entidades.Reporte;
import java.util.List;
import org.bson.Document;

/**
 *
 * @author Roger Jr
 */
public interface IReporteMapper 
{
    Document toDocument(Reporte r);

    Reporte toEntity(Document d);

    List<Reporte> toListEntity(List<Document> lista);
}
