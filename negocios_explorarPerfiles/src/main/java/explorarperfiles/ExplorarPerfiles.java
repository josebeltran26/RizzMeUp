/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package explorarperfiles;

import dto.UsuarioDTO;
import dto.LikeDTO;
import dto.Hobbie;
import dto.GeneroMusical;
import dto.MateriaEscolar;
import dto.ImagenUtil;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalDateTime;
/**
 *
 * @author Roger Jr
 */
public class ExplorarPerfiles implements IExplorarPerfiles {
    @Override
    public List<UsuarioDTO> obtenerCandidatos(Long usuarioId) {
        List<UsuarioDTO> lista = new ArrayList<>();
        
        // Candidata mujer con foto
        UsuarioDTO mujer = new UsuarioDTO();
        mujer.setId(2L);
        mujer.setCorreo("sofia@itson.edu.mx");
        mujer.setNombre("Sofia");
        mujer.setEdad(21);
        mujer.setGenero("Femenino");
        mujer.setProfesion("Estudiante de Diseño Grafico");
        mujer.setCiudad("Ciudad Obregon");
        mujer.setDescripcionPersonal("Amante del arte, el cafe y los atardeceres 🌅");
        mujer.setFechaNacimiento(LocalDate.of(2004, 11, 22));
        mujer.setFechaRegistro(LocalDateTime.now());
        mujer.setActivo(true);
        
        // Cargar foto de perfil
        String fotoMujer = ImagenUtil.cargarImagenBase64("/mujer1.jpg");
        mujer.setFotoPerfilBase64(fotoMujer);
        
        // Hobbies
        ArrayList<Hobbie> hobbiesMujer = new ArrayList<>();
        hobbiesMujer.add(Hobbie.FOTOGRAFIA);
        hobbiesMujer.add(Hobbie.VIAJAR);
        hobbiesMujer.add(Hobbie.CINE);
        hobbiesMujer.add(Hobbie.DIBUJO);
        hobbiesMujer.add(Hobbie.MUSICA);
        mujer.setHobbies(hobbiesMujer);
        
        // Generos musicales
        ArrayList<GeneroMusical> generosMujer = new ArrayList<>();
        generosMujer.add(GeneroMusical.INDIE);
        generosMujer.add(GeneroMusical.POP);
        generosMujer.add(GeneroMusical.JAZZ);
        mujer.setGenerosMusicalFavoritos(generosMujer);
        
        // Materias
        ArrayList<MateriaEscolar> materiasMujer = new ArrayList<>();
        materiasMujer.add(MateriaEscolar.ARTE);
        materiasMujer.add(MateriaEscolar.DISEÑO);
        materiasMujer.add(MateriaEscolar.COMUNICACION);
        mujer.setMateriasFavoritas(materiasMujer);
        
        // Info adicional
        mujer.setEscuela("ITSON");
        mujer.setNivelEstudios("Universidad");
        mujer.setSignoZodiacal("Escorpio");
        mujer.setRedSocialInstagram("@sofi.art");
        mujer.setRedSocialTiktok("@sofi.creates");
        mujer.setEstacionFavorita("Otoño");
        mujer.setVideojuegoFavorito("Animal Crossing");
        mujer.setPeliculaFavorita("Amelie");
        mujer.setSerieFavorita("Stranger Things");
        mujer.setCancionFavorita("Electric Feel");
        mujer.setArtistaFavorito("MGMT");
        mujer.setComidaFavorita("Sushi");
        mujer.setBebidaFavorita("Matcha Latte");
        mujer.setAnimalFavorito("Gato");
        mujer.setColorFavorito("Morado");
        mujer.setEmojiFavorito("✨");
        mujer.setFrasePersonal("La vida es un lienzo en blanco");
        mujer.setPlanIdealCita("Visitar una galeria de arte y cenar en un cafe acogedor");
        mujer.setTipoMascota("Gato");
        mujer.setSuperpoder("Volar");
        mujer.setDeporteFavorito("Yoga");
        mujer.setLugarSonado("Paris");
        
        lista.add(mujer);
        
        // Candidato hombre con foto
        UsuarioDTO hombre = new UsuarioDTO();
        hombre.setId(3L);
        hombre.setCorreo("ruben@itson.edu.mx");
        hombre.setNombre("Ruben");
        hombre.setEdad(21);
        hombre.setGenero("Masculino");
        hombre.setProfesion("Estudiante de Ingenieria en Software");
        hombre.setCiudad("Ciudad Obregon");
        hombre.setDescripcionPersonal("Fan de la programacion y los videojuegos 🎮");
        hombre.setFechaNacimiento(LocalDate.of(2004, 3, 10));
        hombre.setFechaRegistro(LocalDateTime.now());
        hombre.setActivo(true);
        
        // Cargar foto de perfil
        String fotoHombre = ImagenUtil.cargarImagenBase64("/hombre1.jpg");
        hombre.setFotoPerfilBase64(fotoHombre);
        
        // Hobbies
        ArrayList<Hobbie> hobbiesHombre = new ArrayList<>();
        hobbiesHombre.add(Hobbie.PROGRAMACION);
        hobbiesHombre.add(Hobbie.VIDEOJUEGOS);
        hobbiesHombre.add(Hobbie.DEPORTES);
        hobbiesHombre.add(Hobbie.SERIES);
        hombre.setHobbies(hobbiesHombre);
        
        // Generos musicales
        ArrayList<GeneroMusical> generosHombre = new ArrayList<>();
        generosHombre.add(GeneroMusical.RAP);
        generosHombre.add(GeneroMusical.HIP_HOP);
        generosHombre.add(GeneroMusical.TRAP);
        hombre.setGenerosMusicalFavoritos(generosHombre);
        
        // Info adicional
        hombre.setEscuela("ITSON");
        hombre.setNivelEstudios("Universidad");
        hombre.setSignoZodiacal("Piscis");
        hombre.setRedSocialInstagram("@ruben_gamer");
        hombre.setEstacionFavorita("Verano");
        hombre.setVideojuegoFavorito("Valorant");
        hombre.setPeliculaFavorita("The Matrix");
        hombre.setSerieFavorita("Mr. Robot");
        hombre.setComidaFavorita("Tacos");
        hombre.setBebidaFavorita("Monster");
        hombre.setAnimalFavorito("Lobo");
        hombre.setColorFavorito("Negro");
        hombre.setEmojiFavorito("🔥");
        hombre.setFrasePersonal("GG EZ");
        hombre.setPlanIdealCita("Maraton de peliculas y pizza");
        hombre.setTipoMascota("Perro");
        hombre.setSuperpoder("Super velocidad");
        hombre.setDeporteFavorito("Basketball");
        hombre.setLugarSonado("Los Angeles");
        
        lista.add(hombre);
        
        return lista;
    }
    
    @Override
    public List<UsuarioDTO> obtenerCandidatosFiltrados(Long usuarioId) {
        List<UsuarioDTO> lista = new ArrayList<>();
        
        // Pareja ideal - Sofia (mujer con foto)
        UsuarioDTO parejaIdeal = new UsuarioDTO();
        parejaIdeal.setId(2L);
        parejaIdeal.setCorreo("sofia@itson.edu.mx");
        parejaIdeal.setNombre("Sofia");
        parejaIdeal.setEdad(21);
        parejaIdeal.setGenero("Femenino");
        parejaIdeal.setProfesion("Estudiante de Diseño Grafico");
        parejaIdeal.setCiudad("Ciudad Obregon");
        parejaIdeal.setDescripcionPersonal("Amante del arte, el cafe y los atardeceres 🌅");
        parejaIdeal.setActivo(true);
        
        String fotoParejaIdeal = ImagenUtil.cargarImagenBase64("/mujer1.jpg");
        parejaIdeal.setFotoPerfilBase64(fotoParejaIdeal);
        
        ArrayList<Hobbie> hobbiesPareja = new ArrayList<>();
        hobbiesPareja.add(Hobbie.FOTOGRAFIA);
        hobbiesPareja.add(Hobbie.VIDEOJUEGOS);
        hobbiesPareja.add(Hobbie.CINE);
        hobbiesPareja.add(Hobbie.PROGRAMACION);
        parejaIdeal.setHobbies(hobbiesPareja);
        
        parejaIdeal.setEscuela("ITSON");
        parejaIdeal.setSignoZodiacal("Escorpio");
        
        lista.add(parejaIdeal);
        
        return lista;
    }

    @Override
    public void registrarLike(LikeDTO like) {
        System.out.println("Like guardado para " + like.getUsuarioDestinoId());
    }
}
