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
public class ReporteDAO implements IReporteDAO
{
    private static ReporteDAO instancia;
    private final MongoCollection<Document> coleccion;

    // nombre de la coleccion en mongo
    private static final String COLECCION = "reportes";

    private ReporteDAO() {
        MongoDatabase bd = MongoConexion.getInstancia().getBaseDatos();
        coleccion = bd.getCollection(COLECCION);
    }

    public static ReporteDAO getInstancia() {
        if (instancia == null) {
            instancia = new ReporteDAO();
        }
        return instancia;
    }

    @Override
    public boolean insertarReporte(Document doc) {
        try {
            coleccion.insertOne(doc);
            return true;
        } catch (Exception e) {
            System.err.println("error al insertar reporte: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean existeReporte(String idReportante, String idReportado) {
        try {
            Document resultado = coleccion.find(
                Filters.and(
                    Filters.eq("idUsuarioReportante", idReportante),
                    Filters.eq("idUsuarioReportado", idReportado),
                    Filters.eq("estado", "PENDIENTE")
                )
            ).first();
            return resultado != null;
        } catch (Exception e) {
            System.err.println("error al verificar reporte existente: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Document> obtenerPendientes() {
        List<Document> lista = new ArrayList<>();
        try {
            coleccion.find(
                Filters.eq("estado", "PENDIENTE")
            ).into(lista);
        } catch (Exception e) {
            System.err.println("error al obtener reportes pendientes: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public Document obtenerPorId(String id) {
        try {
            return coleccion.find(
                Filters.eq("_id", new ObjectId(id))
            ).first();
        } catch (Exception e) {
            System.err.println("error al obtener reporte por id: " + e.getMessage());
            return null;
        }
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
            System.err.println("error al actualizar estado del reporte: " + e.getMessage());
            return false;
        }
    }
}
