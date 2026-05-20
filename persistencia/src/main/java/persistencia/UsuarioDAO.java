package persistencia;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;
import com.mongodb.client.model.Updates;
import dto.GeneroMusical;
import dto.Hobbie;
import dto.MateriaEscolar;
import dto.UsuarioDTO;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import org.bson.conversions.Bson;

/**
 * Implementacion MongoDB de IUsuarioDAO.
 * Coleccion: "usuarios"
 *
 * @author USUARIO
 */
public class UsuarioDAO implements IUsuarioDAO {

    private final MongoCollection<Document> col;

    public UsuarioDAO() {
        this.col = MongoConexion.getInstance().getBase().getCollection("usuarios");
    }

    // -------------------------------------------------------------------------
    // Auto-incremento
    // -------------------------------------------------------------------------

    private long siguienteId() {
        MongoCollection<Document> seq = MongoConexion.getInstance().getBase().getCollection("sequences");
        Document res = seq.findOneAndUpdate(
                new Document("_id", "usuarios"),
                new Document("$inc", new Document("valor", 1L)),
                new FindOneAndUpdateOptions().upsert(true).returnDocument(ReturnDocument.AFTER));
        return res.getLong("valor");
    }

    // -------------------------------------------------------------------------
    // CRUD
    // -------------------------------------------------------------------------

    @Override
    public Long insertar(UsuarioDTO u) {
        long id = siguienteId();
        u.setId(id);
        col.insertOne(toDoc(u));
        return id;
    }

    @Override
    public void actualizar(UsuarioDTO u) {
        col.replaceOne(Filters.eq("_id", u.getId()), toDoc(u));
    }

    @Override
    public UsuarioDTO buscarPorId(Long id) {
        return toDTO(col.find(Filters.eq("_id", id)).first());
    }

    @Override
    public UsuarioDTO buscarPorCorreo(String correo) {
        return toDTO(col.find(Filters.eq("correo", correo)).first());
    }

    @Override
    public List<UsuarioDTO> obtenerCandidatos(Long usuarioId, List<Long> yaVistos) {
        List<Bson> filtros = new ArrayList<>();
        filtros.add(Filters.ne("_id", usuarioId));
        filtros.add(Filters.eq("activo", true));
        if (yaVistos != null && !yaVistos.isEmpty()) {
            filtros.add(Filters.nin("_id", yaVistos));
        }
        List<UsuarioDTO> resultado = new ArrayList<>();
        col.find(Filters.and(filtros)).forEach(doc -> resultado.add(toDTO(doc)));
        return resultado;
    }

    @Override
    public void desactivar(Long id) {
        col.updateOne(Filters.eq("_id", id), Updates.set("activo", false));
    }

    @Override
    public boolean existeCorreo(String correo) {
        return col.find(Filters.eq("correo", correo)).first() != null;
    }

    // -------------------------------------------------------------------------
    // Mapeo UsuarioDTO <-> Document
    // -------------------------------------------------------------------------

    public static Document toDoc(UsuarioDTO u) {
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
    public static UsuarioDTO toDTO(Document doc) {
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

        List<GeneroMusical> generos = new ArrayList<>();
        List<String> gStr = (List<String>) doc.get("generosMusicalFavoritos");
        if (gStr != null) gStr.forEach(s -> { try { generos.add(GeneroMusical.valueOf(s)); } catch (Exception ignored) {} });
        u.setGenerosMusicalFavoritos(generos);

        List<Hobbie> hobbies = new ArrayList<>();
        List<String> hStr = (List<String>) doc.get("hobbies");
        if (hStr != null) hStr.forEach(s -> { try { hobbies.add(Hobbie.valueOf(s)); } catch (Exception ignored) {} });
        u.setHobbies(hobbies);

        List<MateriaEscolar> materias = new ArrayList<>();
        List<String> mStr = (List<String>) doc.get("materiasFavoritas");
        if (mStr != null) mStr.forEach(s -> { try { materias.add(MateriaEscolar.valueOf(s)); } catch (Exception ignored) {} });
        u.setMateriasFavoritas(materias);

        return u;
    }
}
