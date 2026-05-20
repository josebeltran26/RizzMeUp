/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package explorarperfiles;

import dto.UsuarioDTO;
import dto.LikeDTO;
import java.util.List;
/**
 *
 * @author Roger Jr
 */
public interface IExplorarPerfiles {
    public List<UsuarioDTO> obtenerCandidatos(Long usuarioId);
    public List<UsuarioDTO> obtenerCandidatosFiltrados(Long usuarioId);
    public void registrarLike(LikeDTO like);

    /**
     * Obtiene el perfil completo de un usuario por su ID.
     *
     * @param usuarioId el ID del usuario a buscar
     * @return el UsuarioDTO correspondiente, o null si no existe
     */
    public UsuarioDTO obtenerPerfilPorId(Long usuarioId);
}
