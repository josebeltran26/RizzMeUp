package persistencia;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import java.time.LocalDateTime;
import org.bson.Document;

/**
 * Inicializa la base de datos rizzmeup en MongoDB.
 *
 * Crea las colecciones, los indices y los contadores de sequences.
 * Es seguro ejecutarlo multiples veces: verifica antes de crear.
 *
 * Para ejecutar desde NetBeans: clic derecho -> Run File
 *
 * @author USUARIO
 */
public class InicializadorBD {

    public static void main(String[] args) {
        System.out.println("=== Inicializando BD rizzmeup ===");
        InicializadorBD init = new InicializadorBD();
        init.inicializar();
        MongoConexion.getInstance().cerrar();
        System.out.println("=== Listo ===");
    }

    public void inicializar() {
        MongoDatabase bd = MongoConexion.getInstance().getBase();
        crearColecciones(bd);
        crearIndices(bd);
        inicializarSequences(bd);
        System.out.println("[BD] Inicializacion completada.");
    }

    // =========================================================================
    // Colecciones
    // =========================================================================

    private void crearColecciones(MongoDatabase bd) {
        java.util.Set<String> existentes = new java.util.HashSet<>();
        bd.listCollectionNames().forEach(existentes::add);

        String[] colecciones = {"usuarios", "preferencias", "likes", "matches", "mensajes", "sequences"};
        for (String col : colecciones) {
            if (!existentes.contains(col)) {
                bd.createCollection(col);
                System.out.println("  [OK] Coleccion creada: " + col);
            } else {
                System.out.println("  [--] Coleccion ya existe: " + col);
            }
        }
    }

    // =========================================================================
    // Indices
    // =========================================================================

    private void crearIndices(MongoDatabase bd) {
        System.out.println("  Creando indices...");

        // usuarios
        MongoCollection<Document> usuarios = bd.getCollection("usuarios");
        usuarios.createIndex(Indexes.ascending("correo"),
                new IndexOptions().unique(true).name("idx_correo_unico"));
        usuarios.createIndex(Indexes.ascending("activo"),
                new IndexOptions().name("idx_activo"));
        usuarios.createIndex(Indexes.ascending("ciudad", "genero", "activo"),
                new IndexOptions().name("idx_ciudad_genero_activo"));
        usuarios.createIndex(Indexes.ascending("edad"),
                new IndexOptions().name("idx_edad"));

        // preferencias
        bd.getCollection("preferencias").createIndex(Indexes.ascending("usuarioId"),
                new IndexOptions().unique(true).name("idx_preferencias_usuario"));

        // likes
        MongoCollection<Document> likes = bd.getCollection("likes");
        likes.createIndex(Indexes.ascending("usuarioOrigenId"),
                new IndexOptions().name("idx_likes_origen"));
        likes.createIndex(Indexes.ascending("usuarioOrigenId", "usuarioDestinoId"),
                new IndexOptions().unique(true).name("idx_likes_par_unico"));

        // matches
        MongoCollection<Document> matches = bd.getCollection("matches");
        matches.createIndex(Indexes.ascending("usuario1Id", "usuario2Id"),
                new IndexOptions().name("idx_match_par"));
        matches.createIndex(Indexes.ascending("usuario1Id", "activo"),
                new IndexOptions().name("idx_match_u1"));
        matches.createIndex(Indexes.ascending("usuario2Id", "activo"),
                new IndexOptions().name("idx_match_u2"));

        // mensajes
        MongoCollection<Document> mensajes = bd.getCollection("mensajes");
        mensajes.createIndex(Indexes.ascending("matchId", "fechaEnvio"),
                new IndexOptions().name("idx_mensajes_match_fecha"));
        mensajes.createIndex(Indexes.ascending("matchId", "destinatarioId", "leido"),
                new IndexOptions().name("idx_mensajes_no_leidos"));

        System.out.println("  [OK] Indices creados");
    }

    // =========================================================================
    // Contadores de auto-incremento
    // =========================================================================

    private void inicializarSequences(MongoDatabase bd) {
        MongoCollection<Document> seq = bd.getCollection("sequences");
        String[] nombres = {"usuarios", "preferencias", "likes", "matches", "mensajes"};
        for (String nombre : nombres) {
            boolean existe = seq.find(new Document("_id", nombre)).first() != null;
            if (!existe) {
                seq.insertOne(new Document("_id", nombre).append("valor", 0L));
                System.out.println("  [OK] Contador inicializado: " + nombre);
            }
        }
    }

    // =========================================================================
    // Metodo auxiliar para insertar datos de prueba (opcional)
    // =========================================================================

    public void insertarDatosPrueba() {
        AccesoDatos bd = new AccesoDatos();
        System.out.println("  Insertando datos de prueba...");

        dto.UsuarioDTO valentina = new dto.UsuarioDTO();
        valentina.setCorreo("valentina@rizzmeup.com");
        valentina.setContrasena("1234");
        valentina.setNombre("Valentina");
        valentina.setEdad(21);
        valentina.setProfesion("Arquitectura");
        valentina.setDescripcionPersonal("Me gusta el cafe, los museos y los atardeceres.");
        valentina.setCiudad("Guadalajara");
        valentina.setGenero("Femenino");
        valentina.setSignoZodiacal("Piscis");
        valentina.setFechaRegistro(LocalDateTime.now());
        valentina.setActivo(true);
        valentina.setHobbies(java.util.Arrays.asList(
                dto.Hobbie.LEER, dto.Hobbie.FOTOGRAFIA, dto.Hobbie.YOGA));
        valentina.setGenerosMusicalFavoritos(java.util.Arrays.asList(
                dto.GeneroMusical.POP, dto.GeneroMusical.INDIE));
        Long id1 = bd.guardarUsuario(valentina);
        System.out.println("  [OK] Usuario insertado: Valentina (ID=" + id1 + ")");

        dto.UsuarioDTO marcos = new dto.UsuarioDTO();
        marcos.setCorreo("marcos@rizzmeup.com");
        marcos.setContrasena("1234");
        marcos.setNombre("Marcos");
        marcos.setEdad(23);
        marcos.setProfesion("Mesero");
        marcos.setDescripcionPersonal("Me gusta viajar y conocer gente nueva.");
        marcos.setCiudad("Guadalajara");
        marcos.setGenero("Masculino");
        marcos.setSignoZodiacal("Leo");
        marcos.setFechaRegistro(LocalDateTime.now());
        marcos.setActivo(true);
        marcos.setHobbies(java.util.Arrays.asList(
                dto.Hobbie.VIAJAR, dto.Hobbie.DEPORTES, dto.Hobbie.CINE));
        marcos.setGenerosMusicalFavoritos(java.util.Arrays.asList(
                dto.GeneroMusical.POP, dto.GeneroMusical.REGGAETON));
        Long id2 = bd.guardarUsuario(marcos);
        System.out.println("  [OK] Usuario insertado: Marcos (ID=" + id2 + ")");
    }
}
