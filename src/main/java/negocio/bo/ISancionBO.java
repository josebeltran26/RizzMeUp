/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package negocio.bo;

import java.util.List;
import negocio.dto.SancionDTO;

/**
 *
 * @author Roger Jr
 */
public interface ISancionBO 
{
    boolean procesarSancion(SancionDTO dto);

    List<SancionDTO> obtenerSanciones(String idUsuario);
}
