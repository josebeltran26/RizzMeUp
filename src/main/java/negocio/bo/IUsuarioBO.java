/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package negocio.bo;

import negocio.dto.UsuarioDTO;

/**
 *
 * @author Roger Jr
 */
public interface IUsuarioBO 
{
    UsuarioDTO obtenerDatos(String id);

    boolean validarEstado(String id);

    boolean incrementarAdvertencias(String id);

    UsuarioDTO obtenerPorCorreo(String correo);
}
