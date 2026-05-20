/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package negocio.mapper;

import datos.entidades.Sancion;
import java.util.List;
import org.bson.Document;

/**
 *
 * @author Roger Jr
 */
public interface ISancionMapper 
{
    Document toDocument(Sancion s);

    Sancion toEntity(Document d);

    List<Sancion> toListEntity(List<Document> lista);
}
