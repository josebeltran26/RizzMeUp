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
}
