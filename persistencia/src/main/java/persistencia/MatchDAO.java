package persistencia;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;
import com.mongodb.client.model.Updates;
import dto.MatchDTO;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;

/**
 * Implementacion MongoDB de IMatchDAO.
 * Coleccion: "matches"
 *
 * @author USUARIO
 */
public class MatchDAO implements IMatchDAO {

    private final MongoCollection<Document> col;

    public MatchDAO() {
        this.col = MongoConexion.getInstance().getBase().getCollection("matches");
    }

    private long siguienteId() {
        MongoCollection<Document> seq = MongoConexion.getInstance().getBase().getCollection("sequences");
        Document res = seq.findOneAndUpdate(
                new Document("_id", "matches"),
                new Document("$inc", new Document("valor", 1L)),
                new FindOneAndUpdateOptions().upsert(true).returnDocument(ReturnDocument.AFTER));
        return res.getLong("valor");
    }

    @Override
    public Long insertar(MatchDTO match) {
        long id = siguienteId();
        match.setId(id);
        Document doc = new Document("_id", id)
                .append("usuario1Id", match.getUsuario1Id())
                .append("usuario2Id", match.getUsuario2Id())
                .append("fechaMatch", match.getFechaMatch() != null
                        ? match.getFechaMatch().toString()
                        : LocalDateTime.now().toString())
                .append("activo", match.isActivo());
        col.insertOne(doc);
        return id;
    }

    @Override
    public List<MatchDTO> buscarPorUsuario(Long usuarioId) {
        List<MatchDTO> resultado = new ArrayList<>();
        col.find(Filters.and(
                Filters.or(
                        Filters.eq("usuario1Id", usuarioId),
                        Filters.eq("usuario2Id", usuarioId)),
                Filters.eq("activo", true)
        )).forEach(doc -> resultado.add(toDTO(doc)));
        return resultado;
    }

    @Override
    public MatchDTO buscarEntre(Long usuario1Id, Long usuario2Id) {
        Document doc = col.find(Filters.or(
                Filters.and(
                        Filters.eq("usuario1Id", usuario1Id),
                        Filters.eq("usuario2Id", usuario2Id)),
                Filters.and(
                        Filters.eq("usuario1Id", usuario2Id),
                        Filters.eq("usuario2Id", usuario1Id))
        )).first();
        return toDTO(doc);
    }

    @Override
    public void desactivar(Long matchId) {
        col.updateOne(Filters.eq("_id", matchId), Updates.set("activo", false));
    }

    private MatchDTO toDTO(Document doc) {
        if (doc == null) return null;
        MatchDTO m = new MatchDTO();
        m.setId(doc.getLong("_id"));
        m.setUsuario1Id(doc.getLong("usuario1Id"));
        m.setUsuario2Id(doc.getLong("usuario2Id"));
        String fecha = doc.getString("fechaMatch");
        if (fecha != null) m.setFechaMatch(LocalDateTime.parse(fecha));
        Boolean activo = doc.getBoolean("activo");
        m.setActivo(activo != null && activo);
        return m;
    }
}
