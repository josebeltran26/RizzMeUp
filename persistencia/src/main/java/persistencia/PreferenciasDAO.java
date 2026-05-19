package persistencia;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;
import dto.GeneroMusical;
import dto.Hobbie;
import dto.MateriaEscolar;
import dto.PreferenciasUsuarioDTO;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;

/**
 * Implementacion MongoDB de IPreferenciasDAO.
 * Coleccion: "preferencias"
 *
 * @author USUARIO
 */
public class PreferenciasDAO implements IPreferenciasDAO {

    private final MongoCollection<Document> col;

    public PreferenciasDAO() {
        this.col = MongoConexion.getInstance().getBase().getCollection("preferencias");
    }

    private long siguienteId() {
        MongoCollection<Document> seq = MongoConexion.getInstance().getBase().getCollection("sequences");
        Document res = seq.findOneAndUpdate(
                new Document("_id", "preferencias"),
                new Document("$inc", new Document("valor", 1L)),
                new FindOneAndUpdateOptions().upsert(true).returnDocument(ReturnDocument.AFTER));
        return res.getLong("valor");
    }

    @Override
    public void guardarOActualizar(PreferenciasUsuarioDTO p) {
        Document filtro = new Document("usuarioId", p.getUsuarioId());
        Document existente = col.find(filtro).first();
        if (existente == null) {
            long id = siguienteId();
            p.setId(id);
            col.insertOne(toDoc(p));
        } else {
            col.replaceOne(filtro, toDoc(p));
        }
    }

    @Override
    public PreferenciasUsuarioDTO buscarPorUsuario(Long usuarioId) {
        Document doc = col.find(Filters.eq("usuarioId", usuarioId)).first();
        return toDTO(doc);
    }

    // -------------------------------------------------------------------------
    // Mapeo
    // -------------------------------------------------------------------------

    private Document toDoc(PreferenciasUsuarioDTO p) {
        Document doc = new Document();
        if (p.getId() != null) doc.put("_id", p.getId());
        doc.put("usuarioId", p.getUsuarioId());
        doc.put("generoBuscado", p.getGeneroBuscado());
        doc.put("edadMinima", p.getEdadMinima());
        doc.put("edadMaxima", p.getEdadMaxima());
        doc.put("ciudadPreferida", p.getCiudadPreferida());
        doc.put("interesEstudios", p.isInteresEstudios());

        List<String> generos = new ArrayList<>();
        if (p.getGenerosMusicalPreferidos() != null)
            p.getGenerosMusicalPreferidos().forEach(g -> generos.add(g.name()));
        doc.put("generosMusicalPreferidos", generos);

        List<String> hobbies = new ArrayList<>();
        if (p.getHobbiesPreferidos() != null)
            p.getHobbiesPreferidos().forEach(h -> hobbies.add(h.name()));
        doc.put("hobbiesPreferidos", hobbies);

        List<String> materias = new ArrayList<>();
        if (p.getMateriasPreferidas() != null)
            p.getMateriasPreferidas().forEach(m -> materias.add(m.name()));
        doc.put("materiasPreferidas", materias);

        doc.put("signosPreferidos", p.getSignosPreferidos() != null ? p.getSignosPreferidos() : new ArrayList<>());
        return doc;
    }

    @SuppressWarnings("unchecked")
    private PreferenciasUsuarioDTO toDTO(Document doc) {
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
}
