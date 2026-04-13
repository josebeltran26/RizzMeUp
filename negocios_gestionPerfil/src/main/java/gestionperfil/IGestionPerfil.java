/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package gestionperfil;

import dto.UsuarioDTO;
/**
 *
 * @author Roger Jr
 */
public interface IGestionPerfil {
    public UsuarioDTO obtenerPerfil(Long usuarioId);
    public void actualizarPerfil(UsuarioDTO perfilActualizado);
}

