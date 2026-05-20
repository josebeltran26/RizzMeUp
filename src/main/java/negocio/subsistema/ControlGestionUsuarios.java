/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio.subsistema;

import negocio.bo.ISancionBO;
import negocio.bo.IUsuarioBO;
import negocio.bo.SancionBO;
import negocio.bo.UsuarioBO;
import negocio.dto.SancionDTO;
import negocio.dto.UsuarioDTO;

/**
 *
 * @author Roger Jr
 */
public class ControlGestionUsuarios implements IGestionUsuarios
{
    private static ControlGestionUsuarios instancia;
    private final IUsuarioBO usuarioBO;
    private final ISancionBO sancionBO;

    private ControlGestionUsuarios()
    {
        usuarioBO = UsuarioBO.getInstancia();
        sancionBO = SancionBO.getInstancia();
    }

    public static ControlGestionUsuarios getInstancia()
    {
        if (instancia == null)
        {
            instancia = new ControlGestionUsuarios();
        }
        return instancia;
    }

    @Override
    public UsuarioDTO obtenerDatosUsuario(String id)
    {
        try
        {
            return usuarioBO.obtenerDatos(id);
        }
        catch (Exception e)
        {
            System.err.println("error al obtener datos de usuario: "
                    + e.getMessage());
            return null;
        }
    }

    @Override
    public boolean validarEstadoCuenta(String id)
    {
        try
        {
            return usuarioBO.validarEstado(id);
        }
        catch (Exception e)
        {
            System.err.println("error al validar estado de cuenta: "
                    + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean aplicarSancion(SancionDTO dto)
    {
        try
        {
            return sancionBO.procesarSancion(dto);
        }
        catch (Exception e)
        {
            System.err.println("error al aplicar sancion: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean incrementarAdvertencias(String id)
    {
        try
        {
            return usuarioBO.incrementarAdvertencias(id);
        }
        catch (Exception e)
        {
            System.err.println("error al incrementar advertencias: "
                    + e.getMessage());
            return false;
        }
    }

    @Override
    public UsuarioDTO buscarPorCorreo(String correo)
    {
        try
        {
            return usuarioBO.obtenerPorCorreo(correo);
        }
        catch (Exception e)
        {
            System.err.println("error al buscar usuario por correo: "
                    + e.getMessage());
            return null;
        }
    }

    @Override
    public boolean actualizarEstadoCuenta(String id, String nuevoEstado)
    {
        try
        {
            return datos.dao.UsuarioDAO.getInstancia().actualizarEstado(
                    id, nuevoEstado);
        }
        catch (Exception e)
        {
            System.err.println("error al actualizar estado de cuenta: "
                    + e.getMessage());
            return false;
        }
    }
}