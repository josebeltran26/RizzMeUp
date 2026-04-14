/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package preferencias;

import dto.PreferenciasUsuarioDTO;
/**
 *
 * @author Roger Jr
 */
public interface IPreferencias {
    public PreferenciasUsuarioDTO obtenerPreferencias(Long usuarioId);
    public void guardarPreferencias(PreferenciasUsuarioDTO preferencias);
}
