/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio.bo;

import datos.dao.IReporteDAO;
import datos.dao.IUsuarioDAO;
import datos.dao.ReporteDAO;
import datos.dao.UsuarioDAO;
import java.util.ArrayList;
import java.util.List;
import negocio.dto.ReporteDTO;
import negocio.dto.ReporteDetalleDTO;
import negocio.dto.SancionDTO;
import negocio.dto.UsuarioDTO;
import negocio.mapper.IReporteMapper;
import negocio.mapper.ISancionMapper;
import negocio.mapper.IUsuarioMapper;
import negocio.mapper.ReporteMapper;
import negocio.mapper.SancionMapper;
import negocio.mapper.UsuarioMapper;
import org.bson.Document;

/**
 *
 * @author Roger Jr
 */
public class ReporteBO implements IReporteBO
{
    private static ReporteBO instancia;
    private final IReporteDAO reporteDAO;
    private final IUsuarioDAO usuarioDAO;
    private final IReporteMapper reporteMapper;
    private final IUsuarioMapper usuarioMapper;
    private final ISancionMapper sancionMapper;

    private ReporteBO()
    {
        reporteDAO = ReporteDAO.getInstancia();
        usuarioDAO = UsuarioDAO.getInstancia();
        reporteMapper = ReporteMapper.getInstancia();
        usuarioMapper = UsuarioMapper.getInstancia();
        sancionMapper = SancionMapper.getInstancia();
    }

    public static ReporteBO getInstancia()
    {
        if (instancia == null)
        {
            instancia = new ReporteBO();
        }
        return instancia;
    }

    @Override
    public boolean crearReporte(ReporteDTO dto)
    {
        try
        {
            // construye el documento bson con evidencias incrustadas
            Document doc = new Document();
            doc.append("idUsuarioReportante", dto.getIdUsuarioReportante());
            doc.append("idUsuarioReportado", dto.getIdUsuarioReportado());
            doc.append("motivo", dto.getMotivo());
            doc.append("estado", "PENDIENTE");
            doc.append("fecha", new java.util.Date());

            // lista de evidencias incrustada directamente en el documento
            if (dto.getEvidencias() != null)
            {
                doc.append("evidencias", dto.getEvidencias());
            }
            else
            {
                doc.append("evidencias", new ArrayList<>());
            }

            return reporteDAO.insertarReporte(doc);
        }
        catch (Exception e)
        {
            System.err.println("error al crear reporte: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean validarDuplicado(String idReportante, String idReportado)
    {
        try
        {
            return reporteDAO.existeReporte(idReportante, idReportado);
        }
        catch (Exception e)
        {
            System.err.println("error al validar duplicado: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<ReporteDetalleDTO> obtenerPendientes()
    {
        List<ReporteDetalleDTO> resultado = new ArrayList<>();
        try
        {
            List<Document> docs = reporteDAO.obtenerPendientes();
            if (docs == null || docs.isEmpty())
            {
                return resultado;
            }

            for (Document doc : docs)
            {
                ReporteDetalleDTO detalle = construirDetalleDTO(doc);
                if (detalle != null)
                {
                    resultado.add(detalle);
                }
            }
        }
        catch (Exception e)
        {
            System.err.println("error al obtener pendientes: " + e.getMessage());
        }
        return resultado;
    }

    @Override
    public ReporteDetalleDTO consultarDetalle(String idReporte)
    {
        try
        {
            Document doc = reporteDAO.obtenerPorId(idReporte);
            return construirDetalleDTO(doc);
        }
        catch (Exception e)
        {
            System.err.println("error al consultar detalle: " + e.getMessage());
            return null;
        }
    }

    // metodo privado que arma el dto de detalle con los datos de reportante y reportado
    private ReporteDetalleDTO construirDetalleDTO(Document doc)
    {
        if (doc == null)
        {
            return null;
        }

        ReporteDetalleDTO detalle = new ReporteDetalleDTO();
        detalle.setId(doc.getObjectId("_id").toHexString());
        detalle.setMotivo(doc.getString("motivo"));
        detalle.setEstado(doc.getString("estado"));
        detalle.setFecha(doc.getDate("fecha"));

        // recupera lista de evidencias incrustada
        List<String> evidencias = (List<String>) doc.get("evidencias");
        detalle.setEvidencias(evidencias != null ? evidencias : new ArrayList<>());

        // obtiene datos del reportante
        String idReportante = doc.getString("idUsuarioReportante");
        if (idReportante != null)
        {
            Document docReportante = usuarioDAO.obtenerPorId(idReportante);
            if (docReportante != null)
            {
                detalle.setReportante(usuarioMapper.toEntity(docReportante) != null
                        ? construirUsuarioDTO(docReportante) : null);
            }
        }

        // obtiene datos del reportado
        String idReportado = doc.getString("idUsuarioReportado");
        if (idReportado != null)
        {
            Document docReportado = usuarioDAO.obtenerPorId(idReportado);
            if (docReportado != null)
            {
                detalle.setReportado(construirUsuarioDTO(docReportado));
            }
        }

        // sancion incrustada si existe
        Document docSancion = (Document) doc.get("sancion");
        if (docSancion != null)
        {
            SancionDTO sancionDTO = new SancionDTO();
            sancionDTO.setTipoSancion(docSancion.getString("tipoSancion"));
            sancionDTO.setDescripcion(docSancion.getString("descripcion"));
            sancionDTO.setFechaSancion(docSancion.getDate("fechaSancion"));
            detalle.setSancion(sancionDTO);
        }

        return detalle;
    }

    // construye un usuarioDTO desde un document mongo
    private UsuarioDTO construirUsuarioDTO(Document doc)
    {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(doc.getObjectId("_id").toHexString());
        dto.setNombre(doc.getString("nombre"));
        dto.setEstadoCuenta(doc.getString("estadoCuenta"));
        dto.setCorreo(doc.getString("correo"));
        dto.setFotoPerfil(doc.getString("fotoPerfilUrl"));
        Integer adv = doc.getInteger("advertencias");
        dto.setAdvertencias(adv != null ? adv : 0);
        return dto;
    }
}
