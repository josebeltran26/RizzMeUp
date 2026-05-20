/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio.bo;

import datos.dao.IUsuarioDAO;
import datos.dao.UsuarioDAO;
import negocio.dto.UsuarioDTO;
import negocio.mapper.IUsuarioMapper;
import negocio.mapper.UsuarioMapper;
import org.bson.Document;

/**
 *
 * @author Roger Jr
 */
public class UsuarioBO implements IUsuarioBO
{
    private static UsuarioBO instancia;
    private final IUsuarioDAO usuarioDAO;
    private final IUsuarioMapper usuarioMapper;

    private UsuarioBO()
    {
        usuarioDAO = UsuarioDAO.getInstancia();
        usuarioMapper = UsuarioMapper.getInstancia();
    }

    public static UsuarioBO getInstancia()
    {
        if (instancia == null)
        {
            instancia = new UsuarioBO();
        }
        return instancia;
    }

    @Override
    public UsuarioDTO obtenerDatos(String id)
    {
        try
        {
            Document doc = usuarioDAO.obtenerPorId(id);
            if (doc == null)
            {
                return null;
            }
            return construirUsuarioDTO(doc);
        }
        catch (Exception e)
        {
            System.err.println("error al obtener datos de usuario: " + e.getMessage());
            return null;
        }
    }

    @Override
    public boolean validarEstado(String id)
    {
        try
        {
            Document doc = usuarioDAO.obtenerPorId(id);
            if (doc == null)
            {
                return false;
            }
            // retorna true solo si la cuenta esta activa
            String estado = doc.getString("estadoCuenta");
            return "ACTIVA".equals(estado);
        }
        catch (Exception e)
        {
            System.err.println("error al validar estado de cuenta: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean incrementarAdvertencias(String id)
    {
        try
        {
            return usuarioDAO.incrementarAdvertencias(id);
        }
        catch (Exception e)
        {
            System.err.println("error al incrementar advertencias: " + e.getMessage());
            return false;
        }
    }

    @Override
    public UsuarioDTO obtenerPorCorreo(String correo)
    {
        try
        {
            Document doc = usuarioDAO.obtenerPorCorreo(correo);
            if (doc == null)
            {
                return null;
            }
            return construirUsuarioDTO(doc);
        }
        catch (Exception e)
        {
            System.err.println("error al obtener usuario por correo: " + e.getMessage());
            return null;
        }
    }

    // construye usuariodto desde document mongo sin usar mapper complejo
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
