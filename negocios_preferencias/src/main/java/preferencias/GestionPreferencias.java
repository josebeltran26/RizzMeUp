/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package preferencias;

import dto.PreferenciasUsuarioDTO;
import dto.Hobbie;
import dto.MateriaEscolar;
import java.util.ArrayList;
/**
 *
 * @author Roger Jr
 */
public class GestionPreferencias implements IPreferencias {

    @Override
    public PreferenciasUsuarioDTO obtenerPreferencias(Long usuarioId) {
        PreferenciasUsuarioDTO preferenciasMock = new PreferenciasUsuarioDTO();
        preferenciasMock.setUsuarioId(usuarioId);
        preferenciasMock.setEdadMinima(18);
        preferenciasMock.setEdadMaxima(25);
        preferenciasMock.setGeneroBuscado("Femenino");
        preferenciasMock.setCiudadPreferida("Ciudad Obregón");
        preferenciasMock.setInteresEstudios(true);
        
        ArrayList<Hobbie> hobbiesBuscados = new ArrayList<>();
        hobbiesBuscados.add(Hobbie.PROGRAMACION);
        hobbiesBuscados.add(Hobbie.VIDEOJUEGOS);
        preferenciasMock.setHobbiesPreferidos(hobbiesBuscados);
        
        return preferenciasMock;
    }

    @Override
    public void guardarPreferencias(PreferenciasUsuarioDTO preferencias) {
        System.out.println("Preferencias de busqueda guardadas exitosamente");
        System.out.println("Buscando edades entre: " + preferencias.getEdadMinima() + " y " + preferencias.getEdadMaxima());
    }
}
