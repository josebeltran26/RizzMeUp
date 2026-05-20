/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio.dto;

/**
 *
 * @author Roger Jr
 */
public class UsuarioDTO 
{
    private String id;
    private String nombre;
    private String estadoCuenta;
    private int advertencias;
    private String correo;
    private String fotoPerfil;

    public UsuarioDTO()
    {
    }

    public UsuarioDTO(
            String id,
            String nombre,
            String estadoCuenta,
            int advertencias,
            String correo,
            String fotoPerfil)
    {
        this.id = id;
        this.nombre = nombre;
        this.estadoCuenta = estadoCuenta;
        this.advertencias = advertencias;
        this.correo = correo;
        this.fotoPerfil = fotoPerfil;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public String getEstadoCuenta()
    {
        return estadoCuenta;
    }

    public void setEstadoCuenta(String estadoCuenta)
    {
        this.estadoCuenta = estadoCuenta;
    }

    public int getAdvertencias()
    {
        return advertencias;
    }

    public void setAdvertencias(int advertencias)
    {
        this.advertencias = advertencias;
    }

    public String getCorreo()
    {
        return correo;
    }

    public void setCorreo(String correo)
    {
        this.correo = correo;
    }

    public String getFotoPerfil()
    {
        return fotoPerfil;
    }

    public void setFotoPerfil(String fotoPerfil)
    {
        this.fotoPerfil = fotoPerfil;
    }
}
