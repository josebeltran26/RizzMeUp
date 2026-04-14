package parejaideal;

import dto.ResultadoCompatibilidadDTO;
import java.util.List;

/**
 * Interfaz para el modulo de Pareja Ideal.
 * Calcula la compatibilidad entre un usuario y los candidatos disponibles
 * usando las preferencias del usuario para rankear las mejores opciones.
 *
 * @author USUARIO
 */
public interface IParejaIdeal {

    /**
     * Obtiene la lista de candidatos rankeados por compatibilidad
     * segun las preferencias del usuario.
     *
     * @param usuarioId el ID del usuario logueado
     * @return lista de resultados ordenados de mayor a menor compatibilidad
     */
    List<ResultadoCompatibilidadDTO> obtenerMejoresOpciones(Long usuarioId);

    /**
     * Calcula el puntaje de compatibilidad entre el usuario y un candidato especifico.
     *
     * @param usuarioId el ID del usuario logueado
     * @param candidatoId el ID del candidato a evaluar
     * @return el resultado de compatibilidad detallado
     */
    ResultadoCompatibilidadDTO calcularCompatibilidad(Long usuarioId, Long candidatoId);
}
