package persistencia;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;
import dto.LikeDTO;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;

/**
 * Implementacion MongoDB de ILikeDAO.
 * Coleccion: "likes"
 *
 * @author USUARIO
 */
public class LikeDAO implements ILikeDAO {

    private final MongoCollection<Document> col;

    public LikeDAO() {
        this.col = MongoConexion.getInstance().getBase().getCollection("likes");
    }

    private long siguienteId() {
        MongoCollection<Document> seq = MongoConexion.getInstance().getBase().getCollection("sequences");
        Document res = seq.findOneAndUpdate(
                new Document("_id", "likes"),
                new Document("$inc", new Document("valor", 1L)),
                new FindOneAndUpdateOptions().upsert(true).returnDocument(ReturnDocument.AFTER));
        return res.getLong("valor");
    }

    @Override
    public Long insertar(LikeDTO like) {
        long id = siguienteId();
        like.setId(id);
        Document doc = new Document("_id", id)
                .append("usuarioOrigenId", like.getUsuarioOrigenId())
                .append("usuarioDestinoId", like.getUsuarioDestinoId())
                .append("esLike", like.isEsLike())
                .append("fechaLike", like.getFechaLike() != null ? like.getFechaLike().toString() : LocalDateTime.now().toString());
        col.insertOne(doc);
        return id;
    }

    @Override
    public boolean existeLikeMutuo(Long usuarioOrigenId, Long usuarioDestinoId) {
        // Busca si la persona destino tambien le dio like al origen
        Document doc = col.find(Filters.and(
                Filters.eq("usuarioOrigenId", usuarioDestinoId),
                Filters.eq("usuarioDestinoId", usuarioOrigenId),
                Filters.eq("esLike", true)
        )).first();
        return doc != null;
    }

    @Override
    public List<Long> obtenerIdsYaVistos(Long usuarioId) {
        List<Long> vistos = new ArrayList<>();
        col.find(Filters.eq("usuarioOrigenId", usuarioId))
                .forEach(doc -> {
                    Long destId = doc.getLong("usuarioDestinoId");
                    if (destId != null) vistos.add(destId);
                });
        return vistos;
    }

    @Override
    public boolean yaHizoSwipe(Long usuarioOrigenId, Long usuarioDestinoId) {
        Document doc = col.find(Filters.and(
                Filters.eq("usuarioOrigenId", usuarioOrigenId),
                Filters.eq("usuarioDestinoId", usuarioDestinoId)
        )).first();
        return doc != null;
    }
}
