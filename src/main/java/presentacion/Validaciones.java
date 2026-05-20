/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentacion;

/**
 *
 * @author Roger Jr
 */
public class Validaciones 
{
    // regex para validar que el motivo no este vacio y tenga minimo 10 caracteres
    private static final String REGEX_VALIDO = "^.{10,}$";

    private Validaciones()
    {
    }

    public static boolean validarMotivo(String motivo)
    {
        if (motivo == null)
        {
            return false;
        }
        return motivo.trim().matches(REGEX_VALIDO);
    }

    public static boolean validarUsuario(String id)
    {
        if (id == null || id.trim().isEmpty())
        {
            return false;
        }
        // valida que el id tenga formato de objectid de mongo (24 hex)
        return id.trim().matches("^[a-fA-F0-9]{24}$");
    }
}
