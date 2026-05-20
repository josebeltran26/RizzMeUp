/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio.dto;

/**
 *
 * @author Roger Jr
 */
public class MotivoReporteDTO 
{
    private String id;
    private String descripcion;

    public MotivoReporteDTO()
    {
    }

    public MotivoReporteDTO(String id, String descripcion)
    {
        this.id = id;
        this.descripcion = descripcion;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getDescripcion()
    {
        return descripcion;
    }

    public void setDescripcion(String descripcion)
    {
        this.descripcion = descripcion;
    }
}
