/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio.dto;

/**
 *
 * @author Roger Jr
 */
public class ConfirmacionDTO 
{
    private boolean exitoso;
    private String mensaje;
    private int codigoError;

    public ConfirmacionDTO()
    {
    }

    public ConfirmacionDTO(boolean exitoso, String mensaje, int codigoError)
    {
        this.exitoso = exitoso;
        this.mensaje = mensaje;
        this.codigoError = codigoError;
    }

    public boolean isExitoso()
    {
        return exitoso;
    }

    public void setExitoso(boolean exitoso)
    {
        this.exitoso = exitoso;
    }

    public String getMensaje()
    {
        return mensaje;
    }

    public void setMensaje(String mensaje)
    {
        this.mensaje = mensaje;
    }

    public int getCodigoError()
    {
        return codigoError;
    }

    public void setCodigoError(int codigoError)
    {
        this.codigoError = codigoError;
    }
}
