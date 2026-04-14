/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestionperfil;

import dto.UsuarioDTO;
import dto.Hobbie;
import dto.GeneroMusical;
import java.util.ArrayList;
/**
 *
 * @author Roger Jr
 */
public class GestionPerfil implements IGestionPerfil {

    @Override
    public UsuarioDTO obtenerPerfil(Long usuarioId) {
        UsuarioDTO miPerfil = new UsuarioDTO();
        miPerfil.setNombre("Roger");
        miPerfil.setEdad(20);
        miPerfil.setProfesion("Estudiante Ingenieria en Software");
        miPerfil.setCiudad("Ciudad Obregon");
        miPerfil.setDescripcionPersonal("Desarrollando mi primera app");
        
        ArrayList<Hobbie> misHobbies = new ArrayList<>();
        misHobbies.add(Hobbie.PROGRAMACION);
        misHobbies.add(Hobbie.VIDEOJUEGOS);
        miPerfil.setHobbies(misHobbies);
        
        return miPerfil;
    }

    @Override
    public void actualizarPerfil(UsuarioDTO perfilActualizado) {
        System.out.println("Perfil de " + perfilActualizado.getNombre() + " guardado con exito.");
    }
}
