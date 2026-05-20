package persistencia;

import dto.GeneroMusical;
import dto.Hobbie;
import dto.MateriaEscolar;
import dto.UsuarioDTO;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * Programa para insertar 20 usuarios de prueba en MongoDB.
 * Ejecutar: mvn exec:java -pl persistencia -Dexec.mainClass="persistencia.InsertarUsuariosPrueba"
 */
public class InsertarUsuariosPrueba {

    public static void main(String[] args) {
        IAccesoDatos bd = new AccesoDatos();

        List<UsuarioDTO> usuarios = Arrays.asList(
            crear("Sofia Ramirez",    "sofia@mail.com",   "pass123", 20, "Ciudad Obregón", "Femenino",
                  "Estudiante de ISC", "Me encanta el café y el código ☕",
                  "ITSON", "Universitario", "Escorpión", "@sofi.ram", "@sofi_tiktok",
                  "Otoño", "Stardew Valley", "Interstellar", "Stranger Things",
                  "Cruel Summer", "Taylor Swift", "Sushi", "Matcha", "Gato", "Lila",
                  "✨", "El código es poesía.", "Películas en casa con snacks",
                  "Gato", "Volar", "Natación", "Ciudad de México",
                  list(GeneroMusical.POP, GeneroMusical.INDIE),
                  list(Hobbie.PROGRAMACION, Hobbie.LEER, Hobbie.CINE),
                  list(MateriaEscolar.PROGRAMACION, MateriaEscolar.BASE_DE_DATOS)),

            crear("Diego Herrera",    "diego@mail.com",   "pass123", 22, "Ciudad Obregón", "Masculino",
                  "Ing. en Sistemas", "Gamer, amante de la pizza y los memes 🎮",
                  "UNISON", "Universitario", "Tauro", "@diegoh", "@diegoh_tt",
                  "Primavera", "Minecraft", "Avengers: Endgame", "The Boys",
                  "Blinding Lights", "The Weeknd", "Pizza", "Refresco", "Perro", "Azul",
                  "🎮", "Play hard, work harder.", "Lan party toda la noche",
                  "Perro", "Invisibilidad", "Fútbol", "Japón",
                  list(GeneroMusical.POP, GeneroMusical.ELECTRONICA),
                  list(Hobbie.VIDEOJUEGOS, Hobbie.CINE, Hobbie.GYM),
                  list(MateriaEscolar.PROGRAMACION, MateriaEscolar.REDES)),

            crear("Valentina Torres", "vale@mail.com",    "pass123", 19, "Hermosillo",     "Femenino",
                  "Diseño Gráfico", "Amante del arte y la fotografía 📸",
                  "UNISON", "Universitario", "Piscis", "@vale.torres", "@valetorres",
                  "Verano", "The Sims 4", "La La Land", "Emily in Paris",
                  "golden hour", "JVKE", "Pasta", "Agua de jamaica", "Conejo", "Rosa",
                  "🌸", "La vida es demasiado corta para ropa aburrida.", "Galería de arte + cena",
                  "Conejo", "Telepíatía", "Yoga", "París",
                  list(GeneroMusical.POP, GeneroMusical.INDIE, GeneroMusical.KPOP),
                  list(Hobbie.FOTOGRAFIA, Hobbie.PINTURA, Hobbie.DIBUJO),
                  list(MateriaEscolar.ARTE, MateriaEscolar.DISEÑO)),

            crear("Andrés López",     "andres@mail.com",  "pass123", 23, "Ciudad Obregón", "Masculino",
                  "Ingeniero Civil", "Café, música y buenas conversaciones ☕🎸",
                  "ITSON", "Universitario", "Géminis", "@andreslopez", "@andres_tt",
                  "Otoño", "GTA V", "Ford vs Ferrari", "Breaking Bad",
                  "Hotel California", "Eagles", "Tacos", "Café negro", "Perro", "Verde",
                  "🎸", "La música es el lenguaje del alma.", "Concierto + tacos después",
                  "Perro", "Superfuerza", "Guitarra", "Nueva York",
                  list(GeneroMusical.ROCK, GeneroMusical.INDIE),
                  list(Hobbie.MUSICA, Hobbie.DEPORTES, Hobbie.CAMPING),
                  list(MateriaEscolar.FISICA, MateriaEscolar.MATEMATICAS)),

            crear("Isabella Mora",    "isa@mail.com",     "pass123", 21, "Navojoa",        "Femenino",
                  "Psicología", "Escucho más de lo que hablo 🧠",
                  "UNISON", "Universitario", "Cáncer", "@isamoraaa", "@isa_mora",
                  "Primavera", "Animal Crossing", "Inside Out", "Grey's Anatomy",
                  "positions", "Ariana Grande", "Ensalada griega", "Té verde", "Tortuga", "Morado",
                  "🌊", "Las emociones son datos, no debilidades.", "Picnic en el parque",
                  "Tortuga", "Leer mentes", "Meditación", "Grecia",
                  list(GeneroMusical.POP, GeneroMusical.R_AND_B),
                  list(Hobbie.LEER, Hobbie.YOGA, Hobbie.MEDITACION),
                  list(MateriaEscolar.PSICOLOGIA, MateriaEscolar.FILOSOFIA)),

            crear("Rodrigo Sánchez",  "rodri@mail.com",   "pass123", 24, "Ciudad Obregón", "Masculino",
                  "Medicina", "Futuro médico, actual amante del gym 💪",
                  "UNISON", "Universitario", "Capricornio", "@rodrisanchez", "@rodri_san",
                  "Invierno", "FIFA 24", "Rocky", "House MD",
                  "Eye of the Tiger", "Survivor", "Pollo asado", "Agua", "Perro", "Negro",
                  "💪", "El cuerpo logra lo que la mente cree.", "Gym + desayuno juntos",
                  "Perro", "Sanar a cualquier persona", "Fútbol", "España",
                  list(GeneroMusical.LATIN, GeneroMusical.REGGAETON),
                  list(Hobbie.GYM, Hobbie.DEPORTES, Hobbie.CORRER),
                  list(MateriaEscolar.MEDICINA, MateriaEscolar.BIOLOGIA)),

            crear("Camila Jiménez",   "cami@mail.com",    "pass123", 20, "Obregón",        "Femenino",
                  "Comunicación", "Periodista en entrenamiento 📰✨",
                  "ITSON", "Universitario", "Libra", "@cami.jimenez", "@camijimenez",
                  "Primavera", "Life is Strange", "El Gran Gatsby", "Sex Education",
                  "drivers license", "Olivia Rodrigo", "Brunch", "Café con leche", "Periquito", "Amarillo",
                  "☀️", "Contar historias que importen.", "Brunching y charla profunda",
                  "Periquito", "Hablar todos los idiomas", "Escritura", "Londres",
                  list(GeneroMusical.POP, GeneroMusical.INDIE),
                  list(Hobbie.ESCRITURA, Hobbie.LEER, Hobbie.FOTOGRAFIA),
                  list(MateriaEscolar.COMUNICACION, MateriaEscolar.LITERATURA)),

            crear("Miguel Ángel Ruiz","miguel@mail.com",  "pass123", 22, "Guaymas",        "Masculino",
                  "Administración", "Emprendedor en proceso 📊",
                  "ITSON", "Universitario", "Aries", "@miguelruiz", "@miguel_r",
                  "Verano", "Cities: Skylines", "The Social Network", "Suits",
                  "Can't Stop the Feeling", "Justin Timberlake", "Carne asada", "Cerveza artesanal", "Perro", "Azul marino",
                  "🚀", "El éxito es la suma de pequeños esfuerzos.", "Café y hablar de negocios",
                  "Perro", "Duplicar el tiempo", "Básquetbol", "Dubái",
                  list(GeneroMusical.POP, GeneroMusical.HIP_HOP),
                  list(Hobbie.DEPORTES, Hobbie.LEER, Hobbie.VIDEOJUEGOS),
                  list(MateriaEscolar.ADMINISTRACION, MateriaEscolar.ECONOMIA)),

            crear("Daniela Flores",   "dani@mail.com",    "pass123", 19, "Los Mochis",     "Femenino",
                  "Nutrición", "Cocinando el mundo una receta a la vez 🥗",
                  "UNISON", "Universitario", "Virgo", "@daniflores", "@dani_fl",
                  "Otoño", "Cooking Mama", "Julie & Julia", "MasterChef",
                  "good 4 u", "Olivia Rodrigo", "Todo lo que cocina ella", "Agua de limón", "Gato", "Verde olivo",
                  "🌿", "Eres lo que comes, así que come rico.", "Cocinar juntos en casa",
                  "Gato", "Nunca tener hambre", "Natación", "Italia",
                  list(GeneroMusical.POP, GeneroMusical.LATIN),
                  list(Hobbie.COCINAR, Hobbie.NATACION, Hobbie.YOGA),
                  list(MateriaEscolar.BIOLOGIA, MateriaEscolar.QUIMICA)),

            crear("Emilio Vega",      "emilio@mail.com",  "pass123", 23, "Ciudad Obregón", "Masculino",
                  "Ingeniería en Software", "Dev de día, DJ de noche 🎧",
                  "ITSON", "Universitario", "Acuario", "@emiliovega", "@emilio_dj",
                  "Verano", "Beat Saber", "Tron Legacy", "Black Mirror",
                  "Levels", "Avicii", "Ramen", "Red Bull", "Gato negro", "Neón",
                  "🎧", "El código también tiene ritmo.", "Festival de música + after",
                  "Gato", "Hackear cualquier sistema", "Programación", "Tokio",
                  list(GeneroMusical.ELECTRONICA, GeneroMusical.HIP_HOP, GeneroMusical.TRAP),
                  list(Hobbie.PROGRAMACION, Hobbie.MUSICA, Hobbie.VIDEOJUEGOS),
                  list(MateriaEscolar.INGENIERIA_SOFTWARE, MateriaEscolar.BASE_DE_DATOS)),

            crear("Lucía Mendoza",    "lucia@mail.com",   "pass123", 21, "Hermosillo",     "Femenino",
                  "Arquitectura", "Construyendo sueños, literalmente 🏛️",
                  "UNISON", "Universitario", "Tauro", "@luciamendoza", "@lucia_arq",
                  "Primavera", "The Sims 4", "Inception", "Suits",
                  "Someone Like You", "Adele", "Pasta", "Vino tinto", "Gato persa", "Blanco",
                  "🏛️", "Los detalles hacen la perfección.", "Visita a museo + cena fina",
                  "Gato", "Construir con la mente", "Senderismo", "Florencia",
                  list(GeneroMusical.CLASICA, GeneroMusical.POP),
                  list(Hobbie.DIBUJO, Hobbie.SENDERISMO, Hobbie.LEER),
                  list(MateriaEscolar.ARQUITECTURA, MateriaEscolar.DISEÑO)),

            crear("Sebastián Ríos",   "sebas@mail.com",   "pass123", 22, "Cajeme",         "Masculino",
                  "Contabilidad", "Números de día, estrellas de noche 🌌",
                  "ITSON", "Universitario", "Sagitario", "@sebasrios", "@sebas_r",
                  "Invierno", "No Man's Sky", "Interstellar", "The Office",
                  "September", "Earth Wind & Fire", "Birria", "Mezcal", "Perro labrador", "Naranja",
                  "🌌", "El universo tiene su propio plan.", "Stargazing en el desierto",
                  "Perro", "Viajar en el tiempo", "Camping", "Islandia",
                  list(GeneroMusical.JAZZ, GeneroMusical.ROCK, GeneroMusical.BLUES),
                  list(Hobbie.CAMPING, Hobbie.FOTOGRAFIA, Hobbie.CICLISMO),
                  list(MateriaEscolar.CONTABILIDAD, MateriaEscolar.MATEMATICAS)),

            crear("Mariana Castro",   "mari@mail.com",    "pass123", 20, "Ciudad Obregón", "Femenino",
                  "Derecho", "Futura abogada que defiende causas justas ⚖️",
                  "UNISON", "Universitario", "Escorpión", "@maricastro", "@mari_c",
                  "Otoño", "Ace Attorney", "Lincoln Lawyer", "Better Call Saul",
                  "Woman King", "Estelle", "Mariscos", "Limonada", "Pez betta", "Burdeos",
                  "⚖️", "La justicia no es opcional.", "Debate filosófico + sushi",
                  "Pez", "Convencer a cualquiera", "Lectura", "Washington D.C.",
                  list(GeneroMusical.R_AND_B, GeneroMusical.HIP_HOP),
                  list(Hobbie.LEER, Hobbie.ESCRITURA, Hobbie.VOLUNTARIADO),
                  list(MateriaEscolar.DERECHO, MateriaEscolar.HISTORIA)),

            crear("Javier Moreno",    "javi@mail.com",    "pass123", 24, "Obregón",        "Masculino",
                  "Negocios Internacionales", "Políglota en progreso 🌎",
                  "ITSON", "Universitario", "Leo", "@javimoreno", "@javi_m",
                  "Verano", "Civilization VI", "The Imitation Game", "Money Heist",
                  "Shape of You", "Ed Sheeran", "Pad Thai", "Té matcha", "Husky", "Azul rey",
                  "🌍", "El mundo es tuyo si hablas su idioma.", "Restaurante internacional + idiomas",
                  "Husky", "Hablar todos los idiomas nativamente", "Tenis", "Tailandia",
                  list(GeneroMusical.POP, GeneroMusical.LATIN, GeneroMusical.JAZZ),
                  list(Hobbie.IDIOMAS, Hobbie.VIAJAR, Hobbie.LEER),
                  list(MateriaEscolar.ECONOMIA, MateriaEscolar.HISTORIA)),

            crear("Gabriela Núñez",   "gaby@mail.com",    "pass123", 19, "Hermosillo",     "Femenino",
                  "Biología Marina", "Salvando océanos, una tortuga a la vez 🌊🐢",
                  "UNISON", "Universitario", "Piscis", "@gabynu", "@gaby_ocean",
                  "Verano", "Subnautica", "The Blue Planet", "Our Planet",
                  "Ocean Eyes", "Billie Eilish", "Ceviche", "Agua de coco", "Tortuga marina", "Turquesa",
                  "🐢", "El océano me llama y debo irme.", "Snorkel + ceviche",
                  "Tortuga", "Respirar bajo el agua", "Natación", "Maldivas",
                  list(GeneroMusical.INDIE, GeneroMusical.POP),
                  list(Hobbie.NATACION, Hobbie.SENDERISMO, Hobbie.FOTOGRAFIA),
                  list(MateriaEscolar.BIOLOGIA, MateriaEscolar.QUIMICA)),

            crear("Alan Gutiérrez",   "alan@mail.com",    "pass123", 21, "Navojoa",        "Masculino",
                  "Mecatrónica", "Robots y café, la combinación perfecta 🤖",
                  "ITSON", "Universitario", "Géminis", "@alangtz", "@alan_meca",
                  "Invierno", "Kerbal Space Program", "Iron Man", "Rick and Morty",
                  "Digital Love", "Daft Punk", "Hamburguesa", "Café espresso", "Robot roomba", "Plata",
                  "🤖", "La tecnología es magia que funciona.", "Maker faire + café",
                  "Ninguno (robots)", "Interfaz neural con máquinas", "Programación", "Silicon Valley",
                  list(GeneroMusical.ELECTRONICA, GeneroMusical.ROCK),
                  list(Hobbie.PROGRAMACION, Hobbie.VIDEOJUEGOS, Hobbie.MANUALIDADES),
                  list(MateriaEscolar.ELECTRONICA, MateriaEscolar.MATEMATICAS)),

            crear("Paola Ibarra",     "paola@mail.com",   "pass123", 22, "Ciudad Obregón", "Femenino",
                  "Educación Primaria", "Maestra de corazón, estudiante para siempre 📚",
                  "UPN", "Universitario", "Cáncer", "@paolaibarra", "@paola_edu",
                  "Primavera", "Professor Layton", "Freedom Writers", "Abbott Elementary",
                  "Hall of Fame", "will.i.am", "Enchiladas", "Agua de horchata", "Cachorro golden", "Amarillo sol",
                  "📚", "Cada niño es una diferente clase de flor.", "Picnic y juegos de mesa",
                  "Perro", "Entender a cualquier persona", "Bailar", "Finlandia",
                  list(GeneroMusical.POP, GeneroMusical.CUMBIA),
                  list(Hobbie.LEER, Hobbie.BAILAR, Hobbie.MANUALIDADES),
                  list(MateriaEscolar.PSICOLOGIA, MateriaEscolar.LITERATURA)),

            crear("Fernando Salazar", "fer@mail.com",     "pass123", 23, "Ciudad Obregón", "Masculino",
                  "Música", "Guitarrista buscando su banda de por vida 🎸",
                  "UNISON", "Universitario", "Libra", "@fernandosalazar", "@fer_guitar",
                  "Otoño", "Guitar Hero", "Almost Famous", "Skins UK",
                  "Bohemian Rhapsody", "Queen", "Spaghetti", "Vino tinto", "Gato callejero", "Rojo",
                  "🎸", "La música cura lo que las palabras no pueden.", "Concierto en vivo siempre",
                  "Gato", "Tocar cualquier instrumento perfecto", "Guitarra", "Liverpool",
                  list(GeneroMusical.ROCK, GeneroMusical.METAL, GeneroMusical.BLUES),
                  list(Hobbie.MUSICA, Hobbie.CINE, Hobbie.ESCRITURA),
                  list(MateriaEscolar.MUSICA, MateriaEscolar.HISTORIA)),

            crear("Renata Espinoza",  "renata@mail.com",  "pass123", 20, "Obregón",        "Femenino",
                  "Moda y Diseño", "Fashionista con consciencia 🌱👗",
                  "CESUES", "Universitario", "Acuario", "@renataespinoza", "@renata_moda",
                  "Primavera", "Fashion Dreamer", "Devil Wears Prada", "Emily in Paris",
                  "Vogue", "Madonna", "Ensalada de quinoa", "Kombucha", "Galgo", "Rosa palo",
                  "👗", "La moda pasa, el estilo permanece.", "Tiendas vintage + café de especialidad",
                  "Galgo", "Crear cualquier prenda perfecta", "Yoga", "Milán",
                  list(GeneroMusical.POP, GeneroMusical.ELECTRONICA, GeneroMusical.KPOP),
                  list(Hobbie.DIBUJO, Hobbie.FOTOGRAFIA, Hobbie.YOGA),
                  list(MateriaEscolar.DISEÑO, MateriaEscolar.ARTE)),

            crear("Omar Delgado",     "omar@mail.com",    "pass123", 25, "Ciudad Obregón", "Masculino",
                  "Agronomía", "Agricultor del futuro 🌾",
                  "ITSON", "Universitario", "Tauro", "@omardelgado", "@omar_agro",
                  "Verano", "Stardew Valley", "Field of Dreams", "Yellowstone",
                  "Take Me Home Country Roads", "John Denver", "Carne asada norteña", "Agua fresca", "Caballo", "Café tierra",
                  "🌾", "Del campo para el mundo.", "Paseo a caballo al atardecer",
                  "Caballo", "Hacer florecer cualquier planta", "Fútbol", "Países Bajos",
                  list(GeneroMusical.COUNTRY, GeneroMusical.CORRIDOS, GeneroMusical.BANDA),
                  list(Hobbie.CAMPING, Hobbie.DEPORTES, Hobbie.SENDERISMO),
                  list(MateriaEscolar.BIOLOGIA, MateriaEscolar.QUIMICA))
        );

        System.out.println("═══════════════════════════════════════════════");
        System.out.println("  Insertando 20 usuarios de prueba en MongoDB  ");
        System.out.println("═══════════════════════════════════════════════");

        int exito = 0;
        for (UsuarioDTO u : usuarios) {
            try {
                Long id = bd.guardarUsuario(u);
                System.out.println("✅ Insertado: " + u.getNombre() + " (ID=" + id + ")");
                exito++;
            } catch (Exception e) {
                System.err.println("❌ Error con " + u.getNombre() + ": " + e.getMessage());
            }
        }

        System.out.println("───────────────────────────────────────────────");
        System.out.println("  Total insertados: " + exito + "/" + usuarios.size());
        System.out.println("═══════════════════════════════════════════════");
    }

    @SuppressWarnings("unchecked")
    private static <T> List<T> list(T... items) {
        return Arrays.asList(items);
    }

    private static UsuarioDTO crear(
            String nombre, String correo, String pass, int edad,
            String ciudad, String genero, String profesion, String descripcion,
            String escuela, String nivel, String signo,
            String instagram, String tiktok, String estacion,
            String videojuego, String pelicula, String serie,
            String cancion, String artista, String comida,
            String bebida, String animal, String color,
            String emoji, String frase, String planCita,
            String mascota, String superpoder, String deporte, String lugar,
            List<GeneroMusical> generos, List<Hobbie> hobbies,
            List<MateriaEscolar> materias) {

        UsuarioDTO u = new UsuarioDTO();
        u.setNombre(nombre);
        u.setCorreo(correo);
        u.setContrasena(pass);
        u.setEdad(edad);
        u.setCiudad(ciudad);
        u.setGenero(genero);
        u.setProfesion(profesion);
        u.setDescripcionPersonal(descripcion);
        u.setEscuela(escuela);
        u.setNivelEstudios(nivel);
        u.setSignoZodiacal(signo);
        u.setRedSocialInstagram(instagram);
        u.setRedSocialTiktok(tiktok);
        u.setEstacionFavorita(estacion);
        u.setVideojuegoFavorito(videojuego);
        u.setPeliculaFavorita(pelicula);
        u.setSerieFavorita(serie);
        u.setCancionFavorita(cancion);
        u.setArtistaFavorito(artista);
        u.setComidaFavorita(comida);
        u.setBebidaFavorita(bebida);
        u.setAnimalFavorito(animal);
        u.setColorFavorito(color);
        u.setEmojiFavorito(emoji);
        u.setFrasePersonal(frase);
        u.setPlanIdealCita(planCita);
        u.setTipoMascota(mascota);
        u.setSuperpoder(superpoder);
        u.setDeporteFavorito(deporte);
        u.setLugarSonado(lugar);
        u.setActivo(true);
        u.setFechaRegistro(LocalDateTime.now());
        u.setGenerosMusicalFavoritos(generos);
        u.setHobbies(hobbies);
        u.setMateriasFavoritas(materias);
        return u;
    }
}
