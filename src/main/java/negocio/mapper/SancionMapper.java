/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio.mapper;

import datos.entidades.Sancion;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 *
 * @author Roger Jr
 */
public class SancionMapper implements ISancionMapper
{
    private static SancionMapper instancia;

    private SancionMapper()
    {
    }

    public static SancionMapper getInstancia()
    {
        if (instancia == null)
        {
            instancia = new SancionMapper();
        }
        return instancia;
    }

    @Override
    public Document toDocument(Sancion s)
    {
        Document doc = new Document();

        if (s.getId() != null)
        {
            doc.append("_id", new ObjectId(s.getId()));
        }

        doc.append("idReporte", s.getIdReporte());
        doc.append("idUsuarioSancionado", s.getIdUsuarioSancionado());
        doc.append("tipoSancion", s.getTipoSancion());
        doc.append("fechaSancion", s.getFechaSancion());
        doc.append("descripcion", s.getDescripcion());

        return doc;
    }

    @Override
    public Sancion toEntity(Document d)
    {
        if (d == null)
        {
            return null;
        }

        Sancion s = new Sancion();

        // el id puede no existir si es subdocumento incrustado
        ObjectId oid = d.getObjectId("_id");
        if (oid != null)
        {
            s.setId(oid.toHexString());
        }

        s.setIdReporte(d.getString("idReporte"));
        s.setIdUsuarioSancionado(d.getString("idUsuarioSancionado"));
        s.setTipoSancion(d.getString("tipoSancion"));
        s.setFechaSancion(d.getDate("fechaSancion"));
        s.setDescripcion(d.getString("descripcion"));

        return s;
    }

    @Override
    public List<Sancion> toListEntity(List<Document> lista)
    {
        List<Sancion> resultado = new ArrayList<>();
        if (lista == null)
        {
            return resultado;
        }
        for (Document d : lista)
        {
            resultado.add(toEntity(d));
        }
        return resultado;
    }
}