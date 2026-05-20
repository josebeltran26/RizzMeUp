/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datos.dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import infraestructura.MongoConexion;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 *
 * @author Roger Jr
 */
public class SancionDAO implements ISancionDAO {

    private static SancionDAO instancia;
    private final MongoCollection<Document> coleccion;

    private static final String COLECCION = "sanciones";

    private SancionDAO() {
        MongoDatabase bd = MongoConexion.getInstancia().getBaseDatos();
        coleccion = bd.getCollection(COLECCION);
    }

    public static SancionDAO getInstancia() {
        if (instancia == null) {
            instancia = new SancionDAO();
        }
        return instancia;
    }

    @Override
    public boolean insertarSancion(Document doc) {
        try {
            coleccion.insertOne(doc);
            return true;
        } catch (Exception e) {
            System.err.println("error al insertar sancion: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Document> obtenerPorUsuario(String idUsuario) {
        List<Document> lista = new ArrayList<>();
        try {
            coleccion.find(
                Filters.eq("idUsuarioSancionado", idUsuario)
            ).into(lista);
        } catch (Exception e) {
            System.err.println("error al obtener sanciones por usuario: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public boolean actualizarEstado(String id, String estado) {
        try {
            coleccion.updateOne(
                Filters.eq("_id", new ObjectId(id)),
                Updates.set("estado", estado)
            );
            return true;
        } catch (Exception e) {
            System.err.println("error al actualizar estado de sancion: " + e.getMessage());
            return false;
        }
    }
}
