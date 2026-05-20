/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio.bo;

import datos.dao.ISancionDAO;
import datos.dao.IUsuarioDAO;
import datos.dao.SancionDAO;
import datos.dao.UsuarioDAO;
import java.util.ArrayList;
import java.util.List;
import negocio.dto.SancionDTO;
import negocio.mapper.ISancionMapper;
import negocio.mapper.SancionMapper;
import org.bson.Document;

/**
 *
 * @author Roger Jr
 */
public class SancionBO implements ISancionBO
{
    private static SancionBO instancia;
    private final ISancionDAO sancionDAO;
    private final IUsuarioDAO usuarioDAO;
    private final ISancionMapper sancionMapper;

    private SancionBO()
    {
        sancionDAO = SancionDAO.getInstancia();
        usuarioDAO = UsuarioDAO.getInstancia();
        sancionMapper = SancionMapper.getInstancia();
    }

    public static SancionBO getInstancia()
    {
        if (instancia == null)
        {
            instancia = new SancionBO();
        }
        return instancia;
    }

    @Override
    public boolean procesarSancion(SancionDTO dto)
    {
        try
        {
            // construye documento bson de sancion
            Document doc = new Document();
            doc.append("idReporte", dto.getIdReporte());
            doc.append("idUsuarioSancionado", dto.getIdUsuarioSancionado());
            doc.append("tipoSancion", dto.getTipoSancion());
            doc.append("fechaSancion", new java.util.Date());
            doc.append("descripcion", dto.getDescripcion());

            boolean insertado = sancionDAO.insertarSancion(doc);
            if (!insertado)
            {
                return false;
            }

            // incrementa advertencias del usuario sancionado
            boolean incrementado = usuarioDAO.incrementarAdvertencias(
                    dto.getIdUsuarioSancionado());

            // si llega a 3 advertencias suspende la cuenta automaticamente
            if (incrementado)
            {
                Document docUsuario = usuarioDAO.obtenerPorId(
                        dto.getIdUsuarioSancionado());
                if (docUsuario != null)
                {
                    Integer adv = docUsuario.getInteger("advertencias");
                    if (adv != null && adv >= 3)
                    {
                        usuarioDAO.actualizarEstado(
                                dto.getIdUsuarioSancionado(), "SUSPENDIDO");
                    }
                }
            }

            return true;
        }
        catch (Exception e)
        {
            System.err.println("error al procesar sancion: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<SancionDTO> obtenerSanciones(String idUsuario)
    {
        List<SancionDTO> resultado = new ArrayList<>();
        try
        {
            List<Document> docs = sancionDAO.obtenerPorUsuario(idUsuario);
            if (docs == null || docs.isEmpty())
            {
                return resultado;
            }
            for (Document doc : docs)
            {
                SancionDTO dto = new SancionDTO();
                dto.setIdReporte(doc.getString("idReporte"));
                dto.setIdUsuarioSancionado(doc.getString("idUsuarioSancionado"));
                dto.setTipoSancion(doc.getString("tipoSancion"));
                dto.setFechaSancion(doc.getDate("fechaSancion"));
                dto.setDescripcion(doc.getString("descripcion"));
                resultado.add(dto);
            }
        }
        catch (Exception e)
        {
            System.err.println("error al obtener sanciones: " + e.getMessage());
        }
        return resultado;
    }
}
