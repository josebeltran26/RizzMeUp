/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package negocio.subsistema;

import negocio.dto.SancionDTO;
import negocio.dto.UsuarioDTO;

/**
 *
 * @author Roger Jr
 */
public interface IGestionUsuarios 
{
    UsuarioDTO obtenerDatosUsuario(String id);

    boolean validarEstadoCuenta(String id);

    boolean aplicarSancion(SancionDTO dto);

    boolean incrementarAdvertencias(String id);

    UsuarioDTO buscarPorCorreo(String correo);

    boolean actualizarEstadoCuenta(String id, String nuevoEstado);
}
