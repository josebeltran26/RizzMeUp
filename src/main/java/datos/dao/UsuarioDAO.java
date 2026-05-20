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
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 *
 * @author Roger Jr
 */
public class UsuarioDAO implements IUsuarioDAO {

    private static UsuarioDAO instancia;
    private final MongoCollection<Document> coleccion;

    private static final String COLECCION = "usuarios";

    private UsuarioDAO() {
        MongoDatabase bd = MongoConexion.getInstancia().getBaseDatos();
        coleccion = bd.getCollection(COLECCION);
    }

    public static UsuarioDAO getInstancia() {
        if (instancia == null) {
            instancia = new UsuarioDAO();
        }
        return instancia;
    }

    @Override
    public Document obtenerPorId(String id) {
        try {
            return coleccion.find(
                Filters.eq("_id", new ObjectId(id))
            ).first();
        } catch (Exception e) {
            System.err.println("error al obtener usuario por id: " + e.getMessage());
            return null;
        }
    }

    @Override
    public boolean actualizarEstado(String id, String estado) {
        try {
            coleccion.updateOne(
                Filters.eq("_id", new ObjectId(id)),
                Updates.set("estadoCuenta", estado)
            );
            return true;
        } catch (Exception e) {
            System.err.println("error al actualizar estado de cuenta: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean incrementarAdvertencias(String id) {
        try {
            coleccion.updateOne(
                Filters.eq("_id", new ObjectId(id)),
                Updates.inc("advertencias", 1)
            );
            return true;
        } catch (Exception e) {
            System.err.println("error al incrementar advertencias: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Document obtenerPorCorreo(String correo) {
        try {
            return coleccion.find(
                Filters.eq("correo", correo)
            ).first();
        } catch (Exception e) {
            System.err.println("error al obtener usuario por correo: " + e.getMessage());
            return null;
        }
    }
}
