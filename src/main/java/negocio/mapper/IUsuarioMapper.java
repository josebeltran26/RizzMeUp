/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package negocio.mapper;

import datos.entidades.Usuario;
import java.util.List;
import org.bson.Document;

/**
 *
 * @author Roger Jr
 */
public interface IUsuarioMapper 
{
    Document toDocument(Usuario u);

    Usuario toEntity(Document d);

    List<Usuario> toListEntity(List<Document> lista);
}
