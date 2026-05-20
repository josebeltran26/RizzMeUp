/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio.mapper;

import datos.entidades.Reporte;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 *
 * @author Roger Jr
 */
public class ReporteMapper implements IReporteMapper
{
    private static ReporteMapper instancia;
    // mapper de sancion para convertir el subdocumento incrustado
    private final ISancionMapper sancionMapper;

    private ReporteMapper()
    {
        sancionMapper = SancionMapper.getInstancia();
    }

    public static ReporteMapper getInstancia()
    {
        if (instancia == null)
        {
            instancia = new ReporteMapper();
        }
        return instancia;
    }

    @Override
    public Document toDocument(Reporte r)
    {
        Document doc = new Document();

        // si ya tiene id lo conservamos, si no mongo lo genera
        if (r.getId() != null)
        {
            doc.append("_id", new ObjectId(r.getId()));
        }

        doc.append("idUsuarioReportante", r.getIdUsuarioReportante());
        doc.append("idUsuarioReportado", r.getIdUsuarioReportado());
        doc.append("motivo", r.getMotivo());
        doc.append("estado", r.getEstado());
        doc.append("fecha", r.getFecha());

        // lista de evidencias incrustada directamente
        if (r.getEvidencias() != null)
        {
            doc.append("evidencias", r.getEvidencias());
        }
        else
        {
            doc.append("evidencias", new ArrayList<>());
        }

        // sancion incrustada como subdocumento
        if (r.getSancion() != null)
        {
            doc.append("sancion", sancionMapper.toDocument(r.getSancion()));
        }

        return doc;
    }

    @Override
    public Reporte toEntity(Document d)
    {
        if (d == null)
        {
            return null;
        }

        Reporte r = new Reporte();

        r.setId(d.getObjectId("_id").toHexString());
        r.setIdUsuarioReportante(d.getString("idUsuarioReportante"));
        r.setIdUsuarioReportado(d.getString("idUsuarioReportado"));
        r.setMotivo(d.getString("motivo"));
        r.setEstado(d.getString("estado"));
        r.setFecha(d.getDate("fecha"));

        // recupera lista de evidencias incrustada
        List<String> evidencias = (List<String>) d.get("evidencias");
        r.setEvidencias(evidencias != null ? evidencias : new ArrayList<>());

        // recupera sancion incrustada si existe
        Document docSancion = (Document) d.get("sancion");
        if (docSancion != null)
        {
            r.setSancion(sancionMapper.toEntity(docSancion));
        }

        return r;
    }

    @Override
    public List<Reporte> toListEntity(List<Document> lista)
    {
        List<Reporte> resultado = new ArrayList<>();
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
