package parejaideal;

import dto.GeneroMusical;
import dto.Hobbie;
import dto.MateriaEscolar;
import dto.PreferenciasUsuarioDTO;
import dto.ResultadoCompatibilidadDTO;
import dto.UsuarioDTO;
import explorarperfiles.IExplorarPerfiles;
import preferencias.IPreferencias;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Implementacion del modulo de Pareja Ideal.
 * Calcula la compatibilidad entre el usuario logueado y los candidatos
 * disponibles, rankeandolos de mayor a menor compatibilidad segun
 * las preferencias del usuario.
 *
 * @author USUARIO
 */
public class BuscarParejaIdeal implements IParejaIdeal {

    private final IExplorarPerfiles explorarPerfiles;
    private final IPreferencias preferencias;

    /**
     * Constructor con inyeccion de dependencias.
     *
     * @param explorarPerfiles servicio para obtener la lista de candidatos
     * @param preferencias     servicio para obtener las preferencias del usuario
     */
    public BuscarParejaIdeal(IExplorarPerfiles explorarPerfiles, IPreferencias preferencias) {
        this.explorarPerfiles = explorarPerfiles;
        this.preferencias = preferencias;
    }

    /**
     * Obtiene la lista de candidatos rankeados por compatibilidad.
     *
     * @param usuarioId el ID del usuario logueado
     * @return lista de resultados ordenados de mayor a menor compatibilidad
     */
    @Override
    public List<ResultadoCompatibilidadDTO> obtenerMejoresOpciones(Long usuarioId) {
        List<UsuarioDTO> candidatos = explorarPerfiles.obtenerCandidatos(usuarioId);
        List<ResultadoCompatibilidadDTO> resultados = new ArrayList<>();

        for (UsuarioDTO candidato : candidatos) {
            ResultadoCompatibilidadDTO resultado = calcularCompatibilidad(usuarioId, candidato.getId());
            resultados.add(resultado);
        }

        // Ordena de mayor a menor puntaje
        resultados.sort(Comparator.comparingDouble(ResultadoCompatibilidadDTO::getPuntajeTotal).reversed());
        return resultados;
    }

    /**
     * Calcula el puntaje de compatibilidad entre el usuario y un candidato.
     *
     * El puntaje maximo es 100 puntos distribuidos asi:
     *   - Genero coincide:          20 pts
     *   - Rango de edad coincide:   15 pts
     *   - Ciudad coincide:          10 pts
     *   - Signo zodiacal coincide:   5 pts
     *   - Hobbies en comun:         hasta 20 pts (4 pts c/u, max 5)
     *   - Generos musicales comun:  hasta 15 pts (3 pts c/u, max 5)
     *   - Materias en comun:        hasta 15 pts (3 pts c/u, max 5)
     *
     * @param usuarioId   el ID del usuario logueado
     * @param candidatoId el ID del candidato a evaluar
     * @return el resultado de compatibilidad detallado
     */
    @Override
    public ResultadoCompatibilidadDTO calcularCompatibilidad(Long usuarioId, Long candidatoId) {
        UsuarioDTO candidato = explorarPerfiles.obtenerPerfilPorId(candidatoId);
        PreferenciasUsuarioDTO prefs = preferencias.obtenerPreferencias(usuarioId);
        UsuarioDTO usuario = explorarPerfiles.obtenerPerfilPorId(usuarioId);

        ResultadoCompatibilidadDTO resultado = new ResultadoCompatibilidadDTO();
        resultado.setCandidato(candidato);

        double puntaje = 0.0;

        // --- Genero ---
        boolean coincideGenero = prefs.getGeneroBuscado() != null
                && prefs.getGeneroBuscado().equalsIgnoreCase(candidato.getGenero());
        resultado.setCoincideGenero(coincideGenero);
        if (coincideGenero) puntaje += 20;

        // --- Edad ---
        boolean coincideEdad = candidato.getEdad() >= prefs.getEdadMinima()
                && candidato.getEdad() <= prefs.getEdadMaxima();
        resultado.setCoincideEdad(coincideEdad);
        if (coincideEdad) puntaje += 15;

        // --- Ciudad ---
        boolean coincideCiudad = prefs.getCiudadPreferida() != null
                && prefs.getCiudadPreferida().equalsIgnoreCase(candidato.getCiudad());
        resultado.setCoincideCiudad(coincideCiudad);
        if (coincideCiudad) puntaje += 10;

        // --- Signo zodiacal ---
        boolean coincideSigno = prefs.getSignosPreferidos() != null
                && candidato.getSignoZodiacal() != null
                && prefs.getSignosPreferidos().stream()
                        .anyMatch(s -> s.equalsIgnoreCase(candidato.getSignoZodiacal()));
        resultado.setCoincideSigno(coincideSigno);
        if (coincideSigno) puntaje += 5;

        // --- Hobbies en comun ---
        int hobbiesComun = contarHobbiesEnComun(usuario.getHobbies(), candidato.getHobbies());
        resultado.setHobbiesEnComun(hobbiesComun);
        puntaje += Math.min(hobbiesComun, 5) * 4;

        // --- Generos musicales en comun ---
        int generosComun = contarGenerosEnComun(
                usuario.getGenerosMusicalFavoritos(), candidato.getGenerosMusicalFavoritos());
        resultado.setGenerosMusicalEnComun(generosComun);
        puntaje += Math.min(generosComun, 5) * 3;

        // --- Materias en comun ---
        int materiasComun = contarMateriasEnComun(
                usuario.getMateriasFavoritas(), candidato.getMateriasFavoritas());
        resultado.setMateriasEnComun(materiasComun);
        puntaje += Math.min(materiasComun, 5) * 3;

        resultado.setPuntajeTotal(Math.min(puntaje, 100.0));
        resultado.setResumenCompatibilidad(generarResumen(resultado));
        return resultado;
    }

    // -------------------------------------------------------------------------
    // Metodos auxiliares privados
    // -------------------------------------------------------------------------

    private int contarHobbiesEnComun(List<Hobbie> propios, List<Hobbie> candidatoHobbies) {
        if (propios == null || candidatoHobbies == null) return 0;
        int count = 0;
        for (Hobbie h : propios) {
            if (candidatoHobbies.contains(h)) count++;
        }
        return count;
    }

    private int contarGenerosEnComun(List<GeneroMusical> propios, List<GeneroMusical> candidatoGeneros) {
        if (propios == null || candidatoGeneros == null) return 0;
        int count = 0;
        for (GeneroMusical g : propios) {
            if (candidatoGeneros.contains(g)) count++;
        }
        return count;
    }

    private int contarMateriasEnComun(List<MateriaEscolar> propias, List<MateriaEscolar> candidatoMaterias) {
        if (propias == null || candidatoMaterias == null) return 0;
        int count = 0;
        for (MateriaEscolar m : propias) {
            if (candidatoMaterias.contains(m)) count++;
        }
        return count;
    }

    private String generarResumen(ResultadoCompatibilidadDTO r) {
        StringBuilder sb = new StringBuilder();
        if (r.getPuntajeTotal() >= 80) {
            sb.append("¡Compatibilidad alta! ");
        } else if (r.getPuntajeTotal() >= 50) {
            sb.append("Buena compatibilidad. ");
        } else {
            sb.append("Compatibilidad baja. ");
        }
        if (r.getHobbiesEnComun() > 0) {
            sb.append(r.getHobbiesEnComun()).append(" hobbie(s) en comun. ");
        }
        if (r.getGenerosMusicalEnComun() > 0) {
            sb.append(r.getGenerosMusicalEnComun()).append(" genero(s) musical(es) en comun. ");
        }
        if (r.isCoincideCiudad()) {
            sb.append("Misma ciudad. ");
        }
        return sb.toString().trim();
    }
}
