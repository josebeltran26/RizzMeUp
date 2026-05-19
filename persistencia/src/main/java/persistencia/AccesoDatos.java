package persistencia;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;
import com.mongodb.client.model.Updates;
import dto.GeneroMusical;
import dto.Hobbie;
import dto.LikeDTO;
import dto.MatchDTO;
import dto.MateriaEscolar;
import dto.MensajeDTO;
import dto.PreferenciasUsuarioDTO;
import dto.UsuarioDTO;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import org.bson.conversions.Bson;

/**
 * Implementacion real de IAccesoDatos usando MongoDB.
 * Base de datos: rizzmeup
 * Colecciones: usuarios, preferencias, likes, matches, mensajes, sequences
 *
 * @author USUARIO
 */
public class AccesoDatos implements IAccesoDatos {

    private final MongoDatabase bd;

    public AccesoDatos() {
        this.bd = MongoConexion.getInstance().getBase();
    }

    // =========================================================================
    // Auto-incremento de IDs con coleccion "sequences"
    // =========================================================================

    private long siguienteId(String nombre) {
        MongoCollection<Document> seq = bd.getCollection("sequences");
        Document filtro = new Document("_id", nombre);
        Document update = new Document("$inc", new Document("valor", 1L));
        FindOneAndUpdateOptions opts = new FindOneAndUpdateOptions()
                .upsert(true)
                .returnDocument(ReturnDocument.AFTER);
        Document resultado = seq.findOneAndUpdate(filtro, update, opts);
        return resultado.getLong("valor");
    }

    // =========================================================================
    // Mapeo UsuarioDTO <-> Document
    // =========================================================================

    private Document usuarioToDoc(UsuarioDTO u) {
        Document doc = new Document();
        if (u.getId() != null) doc.put("_id", u.getId());
        doc.put("correo", u.getCorreo());
        doc.put("contrasena", u.getContrasena());
        doc.put("nombre", u.getNombre());
        doc.put("edad", u.getEdad());
        doc.put("profesion", u.getProfesion());
        doc.put("descripcionPersonal", u.getDescripcionPersonal());
        doc.put("ciudad", u.getCiudad());
        doc.put("fotoPerfilBase64", u.getFotoPerfilBase64());
        doc.put("genero", u.getGenero());
        doc.put("fechaNacimiento", u.getFechaNacimiento() != null ? u.getFechaNacimiento().toString() : null);
        doc.put("fechaRegistro", u.getFechaRegistro() != null ? u.getFechaRegistro().toString() : null);
        doc.put("activo", u.isActivo());
        doc.put("escuela", u.getEscuela());
        doc.put("nivelEstudios", u.getNivelEstudios());
        doc.put("signoZodiacal", u.getSignoZodiacal());
        doc.put("redSocialInstagram", u.getRedSocialInstagram());
        doc.put("redSocialTiktok", u.getRedSocialTiktok());
        doc.put("estacionFavorita", u.getEstacionFavorita());
        doc.put("videojuegoFavorito", u.getVideojuegoFavorito());
        doc.put("peliculaFavorita", u.getPeliculaFavorita());
        doc.put("serieFavorita", u.getSerieFavorita());
        doc.put("cancionFavorita", u.getCancionFavorita());
        doc.put("artistaFavorito", u.getArtistaFavorito());
        doc.put("comidaFavorita", u.getComidaFavorita());
        doc.put("bebidaFavorita", u.getBebidaFavorita());
        doc.put("animalFavorito", u.getAnimalFavorito());
        doc.put("colorFavorito", u.getColorFavorito());
        doc.put("emojiFavorito", u.getEmojiFavorito());
        doc.put("frasePersonal", u.getFrasePersonal());
        doc.put("planIdealCita", u.getPlanIdealCita());
        doc.put("tipoMascota", u.getTipoMascota());
        doc.put("superpoder", u.getSuperpoder());
        doc.put("deporteFavorito", u.getDeporteFavorito());
        doc.put("lugarSonado", u.getLugarSonado());

        // Enums como strings
        List<String> generos = new ArrayList<>();
        if (u.getGenerosMusicalFavoritos() != null)
            u.getGenerosMusicalFavoritos().forEach(g -> generos.add(g.name()));
        doc.put("generosMusicalFavoritos", generos);

        List<String> hobbies = new ArrayList<>();
        if (u.getHobbies() != null)
            u.getHobbies().forEach(h -> hobbies.add(h.name()));
        doc.put("hobbies", hobbies);

        List<String> materias = new ArrayList<>();
        if (u.getMateriasFavoritas() != null)
            u.getMateriasFavoritas().forEach(m -> materias.add(m.name()));
        doc.put("materiasFavoritas", materias);

        return doc;
    }

    @SuppressWarnings("unchecked")
    private UsuarioDTO docToUsuario(Document doc) {
        if (doc == null) return null;
        UsuarioDTO u = new UsuarioDTO();
        u.setId(doc.getLong("_id"));
        u.setCorreo(doc.getString("correo"));
        u.setContrasena(doc.getString("contrasena"));
        u.setNombre(doc.getString("nombre"));
        Integer edad = doc.getInteger("edad");
        u.setEdad(edad != null ? edad : 0);
        u.setProfesion(doc.getString("profesion"));
        u.setDescripcionPersonal(doc.getString("descripcionPersonal"));
        u.setCiudad(doc.getString("ciudad"));
        u.setFotoPerfilBase64(doc.getString("fotoPerfilBase64"));
        u.setGenero(doc.getString("genero"));
        String fn = doc.getString("fechaNacimiento");
        if (fn != null) u.setFechaNacimiento(LocalDate.parse(fn));
        String fr = doc.getString("fechaRegistro");
        if (fr != null) u.setFechaRegistro(LocalDateTime.parse(fr));
        Boolean activo = doc.getBoolean("activo");
        u.setActivo(activo != null && activo);
        u.setEscuela(doc.getString("escuela"));
        u.setNivelEstudios(doc.getString("nivelEstudios"));
        u.setSignoZodiacal(doc.getString("signoZodiacal"));
        u.setRedSocialInstagram(doc.getString("redSocialInstagram"));
        u.setRedSocialTiktok(doc.getString("redSocialTiktok"));
        u.setEstacionFavorita(doc.getString("estacionFavorita"));
        u.setVideojuegoFavorito(doc.getString("videojuegoFavorito"));
        u.setPeliculaFavorita(doc.getString("peliculaFavorita"));
        u.setSerieFavorita(doc.getString("serieFavorita"));
        u.setCancionFavorita(doc.getString("cancionFavorita"));
        u.setArtistaFavorito(doc.getString("artistaFavorito"));
        u.setComidaFavorita(doc.getString("comidaFavorita"));
        u.setBebidaFavorita(doc.getString("bebidaFavorita"));
        u.setAnimalFavorito(doc.getString("animalFavorito"));
        u.setColorFavorito(doc.getString("colorFavorito"));
        u.setEmojiFavorito(doc.getString("emojiFavorito"));
        u.setFrasePersonal(doc.getString("frasePersonal"));
        u.setPlanIdealCita(doc.getString("planIdealCita"));
        u.setTipoMascota(doc.getString("tipoMascota"));
        u.setSuperpoder(doc.getString("superpoder"));
        u.setDeporteFavorito(doc.getString("deporteFavorito"));
        u.setLugarSonado(doc.getString("lugarSonado"));

        // Enums desde strings
        List<GeneroMusical> generos = new ArrayList<>();
        List<String> generosStr = (List<String>) doc.get("generosMusicalFavoritos");
        if (generosStr != null) {
            for (String s : generosStr) {
                try { generos.add(GeneroMusical.valueOf(s)); } catch (Exception ignored) {}
            }
        }
        u.setGenerosMusicalFavoritos(generos);

        List<Hobbie> hobbies = new ArrayList<>();
        List<String> hobbiesStr = (List<String>) doc.get("hobbies");
        if (hobbiesStr != null) {
            for (String s : hobbiesStr) {
                try { hobbies.add(Hobbie.valueOf(s)); } catch (Exception ignored) {}
            }
        }
        u.setHobbies(hobbies);

        List<MateriaEscolar> materias = new ArrayList<>();
        List<String> materiasStr = (List<String>) doc.get("materiasFavoritas");
        if (materiasStr != null) {
            for (String s : materiasStr) {
                try { materias.add(MateriaEscolar.valueOf(s)); } catch (Exception ignored) {}
            }
        }
        u.setMateriasFavoritas(materias);

        return u;
    }

    // =========================================================================
    // Usuario
    // =========================================================================

    @Override
    public Long guardarUsuario(UsuarioDTO usuario) {
        MongoCollection<Document> col = bd.getCollection("usuarios");
        long id = siguienteId("usuarios");
        usuario.setId(id);
        col.insertOne(usuarioToDoc(usuario));
        return id;
    }

    @Override
    public void actualizarUsuario(UsuarioDTO usuario) {
        MongoCollection<Document> col = bd.getCollection("usuarios");
        Document filtro = new Document("_id", usuario.getId());
        Document reemplazo = usuarioToDoc(usuario);
        col.replaceOne(filtro, reemplazo);
    }

    @Override
    public UsuarioDTO obtenerUsuarioPorId(Long usuarioId) {
        MongoCollection<Document> col = bd.getCollection("usuarios");
        Document doc = col.find(Filters.eq("_id", usuarioId)).first();
        return docToUsuario(doc);
    }

    @Override
    public UsuarioDTO obtenerUsuarioPorCorreo(String correo) {
        MongoCollection<Document> col = bd.getCollection("usuarios");
        Document doc = col.find(Filters.eq("correo", correo)).first();
        return docToUsuario(doc);
    }

    @Override
    public List<UsuarioDTO> obtenerCandidatos(Long usuarioId) {
        MongoCollection<Document> col = bd.getCollection("usuarios");
        List<Long> yaVistos = obtenerYaVistos(usuarioId);

        List<Bson> filtros = new ArrayList<>();
        filtros.add(Filters.ne("_id", usuarioId));
        filtros.add(Filters.eq("activo", true));
        if (!yaVistos.isEmpty()) {
            filtros.add(Filters.nin("_id", yaVistos));
        }

        List<UsuarioDTO> resultado = new ArrayList<>();
        col.find(Filters.and(filtros)).forEach(doc -> resultado.add(docToUsuario(doc)));
        return resultado;
    }

    @Override
    public void eliminarUsuario(Long usuarioId) {
        MongoCollection<Document> col = bd.getCollection("usuarios");
        col.updateOne(Filters.eq("_id", usuarioId), Updates.set("activo", false));
    }

    // =========================================================================
    // Preferencias
    // =========================================================================

    @SuppressWarnings("unchecked")
    private PreferenciasUsuarioDTO docToPreferencias(Document doc) {
        if (doc == null) return null;
        PreferenciasUsuarioDTO p = new PreferenciasUsuarioDTO();
        p.setId(doc.getLong("_id"));
        p.setUsuarioId(doc.getLong("usuarioId"));
        p.setGeneroBuscado(doc.getString("generoBuscado"));
        Integer eMin = doc.getInteger("edadMinima");
        p.setEdadMinima(eMin != null ? eMin : 18);
        Integer eMax = doc.getInteger("edadMaxima");
        p.setEdadMaxima(eMax != null ? eMax : 99);
        p.setCiudadPreferida(doc.getString("ciudadPreferida"));
        Boolean ie = doc.getBoolean("interesEstudios");
        p.setInteresEstudios(ie != null && ie);

        List<GeneroMusical> generos = new ArrayList<>();
        List<String> gStr = (List<String>) doc.get("generosMusicalPreferidos");
        if (gStr != null) gStr.forEach(s -> { try { generos.add(GeneroMusical.valueOf(s)); } catch (Exception ignored) {} });
        p.setGenerosMusicalPreferidos(generos);

        List<Hobbie> hobbies = new ArrayList<>();
        List<String> hStr = (List<String>) doc.get("hobbiesPreferidos");
        if (hStr != null) hStr.forEach(s -> { try { hobbies.add(Hobbie.valueOf(s)); } catch (Exception ignored) {} });
        p.setHobbiesPreferidos(hobbies);

        List<MateriaEscolar> materias = new ArrayList<>();
        List<String> mStr = (List<String>) doc.get("materiasPreferidas");
        if (mStr != null) mStr.forEach(s -> { try { materias.add(MateriaEscolar.valueOf(s)); } catch (Exception ignored) {} });
        p.setMateriasPreferidas(materias);

        List<String> signos = (List<String>) doc.get("signosPreferidos");
        p.setSignosPreferidos(signos != null ? signos : new ArrayList<>());

        return p;
    }

    private Document preferenciasToDoc(PreferenciasUsuarioDTO p) {
        Document doc = new Document();
        if (p.getId() != null) doc.put("_id", p.getId());
        doc.put("usuarioId", p.getUsuarioId());
        doc.put("generoBuscado", p.getGeneroBuscado());
        doc.put("edadMinima", p.getEdadMinima());
        doc.put("edadMaxima", p.getEdadMaxima());
        doc.put("ciudadPreferida", p.getCiudadPreferida());
        doc.put("interesEstudios", p.isInteresEstudios());
        List<String> generos = new ArrayList<>();
        if (p.getGenerosMusicalPreferidos() != null) p.getGenerosMusicalPreferidos().forEach(g -> generos.add(g.name()));
        doc.put("generosMusicalPreferidos", generos);
        List<String> hobbies = new ArrayList<>();
        if (p.getHobbiesPreferidos() != null) p.getHobbiesPreferidos().forEach(h -> hobbies.add(h.name()));
        doc.put("hobbiesPreferidos", hobbies);
        List<String> materias = new ArrayList<>();
        if (p.getMateriasPreferidas() != null) p.getMateriasPreferidas().forEach(m -> materias.add(m.name()));
        doc.put("materiasPreferidas", materias);
        doc.put("signosPreferidos", p.getSignosPreferidos() != null ? p.getSignosPreferidos() : new ArrayList<>());
        return doc;
    }

    @Override
    public void guardarPreferencias(PreferenciasUsuarioDTO preferencias) {
        MongoCollection<Document> col = bd.getCollection("preferencias");
        Document filtro = new Document("usuarioId", preferencias.getUsuarioId());
        Document existente = col.find(filtro).first();
        if (existente == null) {
            long id = siguienteId("preferencias");
            preferencias.setId(id);
            col.insertOne(preferenciasToDoc(preferencias));
        } else {
            col.replaceOne(filtro, preferenciasToDoc(preferencias));
        }
    }

    @Override
    public PreferenciasUsuarioDTO obtenerPreferencias(Long usuarioId) {
        MongoCollection<Document> col = bd.getCollection("preferencias");
        Document doc = col.find(Filters.eq("usuarioId", usuarioId)).first();
        return docToPreferencias(doc);
    }

    // =========================================================================
    // Like
    // =========================================================================

    @Override
    public Long guardarLike(LikeDTO like) {
        MongoCollection<Document> col = bd.getCollection("likes");
        long id = siguienteId("likes");
        like.setId(id);
        Document doc = new Document("_id", id)
                .append("usuarioOrigenId", like.getUsuarioOrigenId())
                .append("usuarioDestinoId", like.getUsuarioDestinoId())
                .append("esLike", like.isEsLike())
                .append("fechaLike", like.getFechaLike() != null ? like.getFechaLike().toString() : null);
        col.insertOne(doc);
        return id;
    }

    @Override
    public boolean existeLikeMutuo(Long usuarioOrigenId, Long usuarioDestinoId) {
        MongoCollection<Document> col = bd.getCollection("likes");
        Document doc = col.find(Filters.and(
                Filters.eq("usuarioOrigenId", usuarioDestinoId),
                Filters.eq("usuarioDestinoId", usuarioOrigenId),
                Filters.eq("esLike", true)
        )).first();
        return doc != null;
    }

    @Override
    public List<Long> obtenerYaVistos(Long usuarioId) {
        MongoCollection<Document> col = bd.getCollection("likes");
        List<Long> vistos = new ArrayList<>();
        col.find(Filters.eq("usuarioOrigenId", usuarioId))
                .forEach(doc -> vistos.add(doc.getLong("usuarioDestinoId")));
        return vistos;
    }

    // =========================================================================
    // Match
    // =========================================================================

    @Override
    public Long guardarMatch(MatchDTO match) {
        MongoCollection<Document> col = bd.getCollection("matches");
        long id = siguienteId("matches");
        match.setId(id);
        Document doc = new Document("_id", id)
                .append("usuario1Id", match.getUsuario1Id())
                .append("usuario2Id", match.getUsuario2Id())
                .append("fechaMatch", match.getFechaMatch() != null ? match.getFechaMatch().toString() : null)
                .append("activo", match.isActivo());
        col.insertOne(doc);
        return id;
    }

    @Override
    public List<MatchDTO> obtenerMatchesPorUsuario(Long usuarioId) {
        MongoCollection<Document> col = bd.getCollection("matches");
        List<MatchDTO> resultado = new ArrayList<>();
        col.find(Filters.and(
                Filters.or(
                        Filters.eq("usuario1Id", usuarioId),
                        Filters.eq("usuario2Id", usuarioId)),
                Filters.eq("activo", true)
        )).forEach(doc -> resultado.add(docToMatch(doc)));
        return resultado;
    }

    @Override
    public MatchDTO obtenerMatchEntre(Long usuario1Id, Long usuario2Id) {
        MongoCollection<Document> col = bd.getCollection("matches");
        Document doc = col.find(Filters.or(
                Filters.and(Filters.eq("usuario1Id", usuario1Id), Filters.eq("usuario2Id", usuario2Id)),
                Filters.and(Filters.eq("usuario1Id", usuario2Id), Filters.eq("usuario2Id", usuario1Id))
        )).first();
        return docToMatch(doc);
    }

    private MatchDTO docToMatch(Document doc) {
        if (doc == null) return null;
        MatchDTO m = new MatchDTO();
        m.setId(doc.getLong("_id"));
        m.setUsuario1Id(doc.getLong("usuario1Id"));
        m.setUsuario2Id(doc.getLong("usuario2Id"));
        String fm = doc.getString("fechaMatch");
        if (fm != null) m.setFechaMatch(LocalDateTime.parse(fm));
        Boolean activo = doc.getBoolean("activo");
        m.setActivo(activo != null && activo);
        return m;
    }

    // =========================================================================
    // Mensaje
    // =========================================================================

    @Override
    public Long guardarMensaje(MensajeDTO mensaje) {
        MongoCollection<Document> col = bd.getCollection("mensajes");
        long id = siguienteId("mensajes");
        mensaje.setId(id);
        Document doc = new Document("_id", id)
                .append("matchId", mensaje.getMatchId())
                .append("remitenteId", mensaje.getRemitenteId())
                .append("destinatarioId", mensaje.getDestinatarioId())
                .append("contenido", mensaje.getContenido())
                .append("fechaEnvio", mensaje.getFechaEnvio() != null ? mensaje.getFechaEnvio().toString() : null)
                .append("leido", mensaje.isLeido());
        col.insertOne(doc);
        return id;
    }

    @Override
    public List<MensajeDTO> obtenerMensajesPorMatch(Long matchId) {
        MongoCollection<Document> col = bd.getCollection("mensajes");
        List<MensajeDTO> resultado = new ArrayList<>();
        col.find(Filters.eq("matchId", matchId)).forEach(doc -> resultado.add(docToMensaje(doc)));
        resultado.sort((a, b) -> {
            if (a.getFechaEnvio() == null) return -1;
            if (b.getFechaEnvio() == null) return 1;
            return a.getFechaEnvio().compareTo(b.getFechaEnvio());
        });
        return resultado;
    }

    @Override
    public void marcarMensajesComoLeidos(Long matchId, Long usuarioId) {
        MongoCollection<Document> col = bd.getCollection("mensajes");
        col.updateMany(
                Filters.and(
                        Filters.eq("matchId", matchId),
                        Filters.eq("destinatarioId", usuarioId),
                        Filters.eq("leido", false)),
                Updates.set("leido", true));
    }

    private MensajeDTO docToMensaje(Document doc) {
        if (doc == null) return null;
        MensajeDTO m = new MensajeDTO();
        m.setId(doc.getLong("_id"));
        m.setMatchId(doc.getLong("matchId"));
        m.setRemitenteId(doc.getLong("remitenteId"));
        m.setDestinatarioId(doc.getLong("destinatarioId"));
        m.setContenido(doc.getString("contenido"));
        String fe = doc.getString("fechaEnvio");
        if (fe != null) m.setFechaEnvio(LocalDateTime.parse(fe));
        Boolean leido = doc.getBoolean("leido");
        m.setLeido(leido != null && leido);
        return m;
    }
}
