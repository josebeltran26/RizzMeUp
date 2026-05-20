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
public interface ISancionDAO 
{
    boolean insertarSancion(Document doc);

    List<Document> obtenerPorUsuario(String idUsuario);

    boolean actualizarEstado(String id, String estado);
}
