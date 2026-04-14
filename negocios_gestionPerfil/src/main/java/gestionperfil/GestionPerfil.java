/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestionperfil;

import dto.UsuarioDTO;
import dto.Hobbie;
import dto.GeneroMusical;
import dto.ImagenUtil;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.LocalDateTime;
/**
 *
 * @author Roger Jr
 */
public class GestionPerfil implements IGestionPerfil {

    @Override
    public UsuarioDTO obtenerPerfil(Long usuarioId) {
        UsuarioDTO miPerfil = new UsuarioDTO();
        miPerfil.setId(1L);
        miPerfil.setCorreo("roger@itson.edu.mx");
        miPerfil.setNombre("Roger");
        miPerfil.setEdad(20);
        miPerfil.setGenero("Masculino");
        miPerfil.setProfesion("Estudiante Ingenieria en Software");
        miPerfil.setCiudad("Ciudad Obregon");
        miPerfil.setDescripcionPersonal("Desarrollando mi primera app");
        miPerfil.setFechaNacimiento(LocalDate.of(2005, 8, 15));
        miPerfil.setFechaRegistro(LocalDateTime.now());
        miPerfil.setActivo(true);
        
        // Cargar foto de perfil
        String fotoBase64 = ImagenUtil.cargarImagenBase64("/hombre1.jpg");
        miPerfil.setFotoPerfilBase64(fotoBase64);
        
        // Hobbies
        ArrayList<Hobbie> misHobbies = new ArrayList<>();
        misHobbies.add(Hobbie.PROGRAMACION);
        misHobbies.add(Hobbie.VIDEOJUEGOS);
        misHobbies.add(Hobbie.ANIME);
        miPerfil.setHobbies(misHobbies);
        
        // Generos musicales
        ArrayList<GeneroMusical> misGeneros = new ArrayList<>();
        misGeneros.add(GeneroMusical.ROCK);
        misGeneros.add(GeneroMusical.ELECTRONICA);
        miPerfil.setGenerosMusicalFavoritos(misGeneros);
        
        // Info adicional
        miPerfil.setEscuela("ITSON");
        miPerfil.setNivelEstudios("Universidad");
        miPerfil.setSignoZodiacal("Leo");
        miPerfil.setRedSocialInstagram("@roger_dev");
        miPerfil.setEstacionFavorita("Invierno");
        miPerfil.setVideojuegoFavorito("Minecraft");
        miPerfil.setPeliculaFavorita("Interstellar");
        miPerfil.setSerieFavorita("Breaking Bad");
        miPerfil.setCancionFavorita("Bohemian Rhapsody");
        miPerfil.setArtistaFavorito("Queen");
        miPerfil.setComidaFavorita("Pizza");
        miPerfil.setBebidaFavorita("Cafe");
        miPerfil.setAnimalFavorito("Perro");
        miPerfil.setColorFavorito("Azul");
        miPerfil.setEmojiFavorito("😎");
        miPerfil.setFrasePersonal("El codigo es poesia");
        miPerfil.setPlanIdealCita("Ir a un arcade retro");
        miPerfil.setTipoMascota("Perro");
        miPerfil.setSuperpoder("Teletransportacion");
        miPerfil.setDeporteFavorito("Futbol");
        miPerfil.setLugarSonado("Japon");
        
        return miPerfil;
    }

    @Override
    public void actualizarPerfil(UsuarioDTO perfilActualizado) {
        System.out.println("Perfil de " + perfilActualizado.getNombre() + " guardado con exito.");
    }
}
