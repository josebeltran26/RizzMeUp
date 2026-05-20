/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package datos.dao;

import org.bson.Document;

/**
 *
 * @author Roger Jr
 */
public interface IUsuarioDAO 
{
    Document obtenerPorId(String id);

    boolean actualizarEstado(String id, String estado);

    boolean incrementarAdvertencias(String id);

    Document obtenerPorCorreo(String correo);
}
