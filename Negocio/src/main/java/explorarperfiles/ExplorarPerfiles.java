/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package explorarperfiles;

import dto.UsuarioDTO;
import dto.LikeDTO;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Roger Jr
 */
public class ExplorarPerfiles implements IExplorarPerfiles {
    @Override
    public List<UsuarioDTO> obtenerCandidatos(Long usuarioId) {
        List<UsuarioDTO> lista = new ArrayList<>();
        UsuarioDTO v = new UsuarioDTO();
        v.setNombre("Ruben");
        v.setEdad(21);
        v.setDescripcionPersonal("Fan de la programacion");
        lista.add(v);
        return lista;
    }
    
    @Override
    public List<UsuarioDTO> obtenerCandidatosFiltrados(Long usuarioId) {
        List<UsuarioDTO> lista = new ArrayList<>();
        UsuarioDTO c = new UsuarioDTO();
        
        c.setNombre("Ana (Pareja Ideal)");
        c.setEdad(20);
        c.setDescripcionPersonal("Me encantan los videojuegos y estudiar Ingeniería de Software.");
        lista.add(c);
        
        return lista;
    }

    @Override
    public void registrarLike(LikeDTO like) {
        System.out.println("Like guardado para " + like.getUsuarioDestinoId());
    }
}
