package persistencia;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;
import com.mongodb.client.model.Updates;
import dto.MensajeDTO;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;

/**
 * Implementacion MongoDB de IMensajeDAO.
 * Coleccion: "mensajes"
 *
 * @author USUARIO
 */
public class MensajeDAO implements IMensajeDAO {

    private final MongoCollection<Document> col;

    public MensajeDAO() {
        this.col = MongoConexion.getInstance().getBase().getCollection("mensajes");
    }

    private long siguienteId() {
        MongoCollection<Document> seq = MongoConexion.getInstance().getBase().getCollection("sequences");
        Document res = seq.findOneAndUpdate(
                new Document("_id", "mensajes"),
                new Document("$inc", new Document("valor", 1L)),
                new FindOneAndUpdateOptions().upsert(true).returnDocument(ReturnDocument.AFTER));
        return res.getLong("valor");
    }

    @Override
    public Long insertar(MensajeDTO mensaje) {
        long id = siguienteId();
        mensaje.setId(id);
        Document doc = new Document("_id", id)
                .append("matchId", mensaje.getMatchId())
                .append("remitenteId", mensaje.getRemitenteId())
                .append("destinatarioId", mensaje.getDestinatarioId())
                .append("contenido", mensaje.getContenido())
                .append("fechaEnvio", mensaje.getFechaEnvio() != null
                        ? mensaje.getFechaEnvio().toString()
                        : LocalDateTime.now().toString())
                .append("leido", false);
        col.insertOne(doc);
        return id;
    }

    @Override
    public List<MensajeDTO> buscarPorMatch(Long matchId) {
        List<MensajeDTO> resultado = new ArrayList<>();
        col.find(Filters.eq("matchId", matchId))
                .forEach(doc -> resultado.add(toDTO(doc)));
        // Ordenar cronologicamente
        resultado.sort((a, b) -> {
            if (a.getFechaEnvio() == null) return -1;
            if (b.getFechaEnvio() == null) return 1;
            return a.getFechaEnvio().compareTo(b.getFechaEnvio());
        });
        return resultado;
    }

    @Override
    public void marcarComoLeidos(Long matchId, Long destinatarioId) {
        col.updateMany(
                Filters.and(
                        Filters.eq("matchId", matchId),
                        Filters.eq("destinatarioId", destinatarioId),
                        Filters.eq("leido", false)),
                Updates.set("leido", true));
    }

    @Override
    public int contarNoLeidos(Long usuarioId) {
        return (int) col.countDocuments(Filters.and(
                Filters.eq("destinatarioId", usuarioId),
                Filters.eq("leido", false)));
    }

    private MensajeDTO toDTO(Document doc) {
        if (doc == null) return null;
        MensajeDTO m = new MensajeDTO();
        m.setId(doc.getLong("_id"));
        m.setMatchId(doc.getLong("matchId"));
        m.setRemitenteId(doc.getLong("remitenteId"));
        m.setDestinatarioId(doc.getLong("destinatarioId"));
        m.setContenido(doc.getString("contenido"));
        String fecha = doc.getString("fechaEnvio");
        if (fecha != null) m.setFechaEnvio(LocalDateTime.parse(fecha));
        Boolean leido = doc.getBoolean("leido");
        m.setLeido(leido != null && leido);
        return m;
    }
}
