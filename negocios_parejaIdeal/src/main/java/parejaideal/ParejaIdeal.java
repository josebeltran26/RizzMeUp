package parejaideal;

import dto.GeneroMusical;
import dto.Hobbie;
import dto.MateriaEscolar;
import dto.PreferenciasUsuarioDTO;
import dto.ResultadoCompatibilidadDTO;
import dto.UsuarioDTO;
import explorarperfiles.ExplorarPerfiles;
import explorarperfiles.IExplorarPerfiles;
import preferencias.GestionPreferencias;
import preferencias.IPreferencias;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Implementacion del modulo de Pareja Ideal.
 * Usa un algoritmo de puntaje ponderado para evaluar la compatibilidad
 * entre el usuario y cada candidato basado en sus preferencias.
 *
 * Pesos del algoritmo:
 * - Genero buscado: 25 puntos (obligatorio)
 * - Rango de edad: 20 puntos
 * - Ciudad: 15 puntos
 * - Hobbies en comun: 20 puntos (proporcional)
 * - Generos musicales en comun: 10 puntos (proporcional)
 * - Materias en comun: 5 puntos (proporcional)
 * - Signo zodiacal: 5 puntos
 *
 * @author USUARIO
 */
public class ParejaIdeal implements IParejaIdeal {

    private final IPreferencias negocioPreferencias;
    private final IExplorarPerfiles negocioExplorar;

    // Pesos del algoritmo de compatibilidad
    private static final double PESO_GENERO = 25.0;
    private static final double PESO_EDAD = 20.0;
    private static final double PESO_CIUDAD = 15.0;
    private static final double PESO_HOBBIES = 20.0;
    private static final double PESO_MUSICA = 10.0;
    private static final double PESO_MATERIAS = 5.0;
    private static final double PESO_SIGNO = 5.0;

    public ParejaIdeal() {
        this.negocioPreferencias = new GestionPreferencias();
        this.negocioExplorar = new ExplorarPerfiles();
    }

    public ParejaIdeal(IPreferencias negocioPreferencias, IExplorarPerfiles negocioExplorar) {
        this.negocioPreferencias = negocioPreferencias;
        this.negocioExplorar = negocioExplorar;
    }

    @Override
    public List<ResultadoCompatibilidadDTO> obtenerMejoresOpciones(Long usuarioId) {
        // 1. Obtener preferencias del usuario
        PreferenciasUsuarioDTO preferencias = negocioPreferencias.obtenerPreferencias(usuarioId);

        // 2. Obtener todos los candidatos disponibles
        List<UsuarioDTO> candidatos = negocioExplorar.obtenerCandidatos(usuarioId);

        // 3. Calcular compatibilidad para cada candidato y filtrar
        List<ResultadoCompatibilidadDTO> resultados = new ArrayList<>();
        for (UsuarioDTO candidato : candidatos) {
            ResultadoCompatibilidadDTO resultado = evaluarCandidato(preferencias, candidato);
            // Solo considerar como "Pareja Ideal" si el puntaje es mayor a 40
            if (resultado.getPuntajeTotal() >= 40.0) {
                resultados.add(resultado);
            }
        }

        // 4. Ordenar de mayor a menor compatibilidad
        resultados.sort(Comparator.comparingDouble(ResultadoCompatibilidadDTO::getPuntajeTotal).reversed());

        return resultados;
    }

    @Override
    public ResultadoCompatibilidadDTO calcularCompatibilidad(Long usuarioId, Long candidatoId) {
        PreferenciasUsuarioDTO preferencias = negocioPreferencias.obtenerPreferencias(usuarioId);
        List<UsuarioDTO> candidatos = negocioExplorar.obtenerCandidatos(usuarioId);

        for (UsuarioDTO candidato : candidatos) {
            if (candidato.getId() != null && candidato.getId().equals(candidatoId)) {
                return evaluarCandidato(preferencias, candidato);
            }
        }

        return null;
    }

    /**
     * Evalua un candidato contra las preferencias del usuario
     * y genera un resultado de compatibilidad detallado.
     */
    private ResultadoCompatibilidadDTO evaluarCandidato(PreferenciasUsuarioDTO pref, UsuarioDTO candidato) {
        ResultadoCompatibilidadDTO resultado = new ResultadoCompatibilidadDTO();
        resultado.setCandidato(candidato);

        double puntaje = 0.0;
        StringBuilder resumen = new StringBuilder();

        // 1. Verificar genero buscado
        boolean generoCoincide = verificarGenero(pref, candidato);
        resultado.setCoincideGenero(generoCoincide);
        if (generoCoincide) {
            puntaje += PESO_GENERO;
            resumen.append("Genero compatible\n");
        } else {
            resumen.append("Genero no compatible\n");
        }

        // 2. Verificar rango de edad
        boolean edadCoincide = verificarEdad(pref, candidato);
        resultado.setCoincideEdad(edadCoincide);
        if (edadCoincide) {
            puntaje += PESO_EDAD;
            resumen.append("Edad dentro del rango (").append(candidato.getEdad()).append(" años)\n");
        } else {
            resumen.append("Edad fuera del rango (").append(candidato.getEdad()).append(" años)\n");
        }

        // 3. Verificar ciudad
        boolean ciudadCoincide = verificarCiudad(pref, candidato);
        resultado.setCoincideCiudad(ciudadCoincide);
        if (ciudadCoincide) {
            puntaje += PESO_CIUDAD;
            resumen.append("Misma ciudad: ").append(candidato.getCiudad()).append("\n");
        } else {
            resumen.append("Ciudad diferente\n");
        }

        // 4. Calcular hobbies en comun
        int hobbiesComun = contarHobbiesEnComun(pref, candidato);
        resultado.setHobbiesEnComun(hobbiesComun);
        if (pref.getHobbiesPreferidos() != null && !pref.getHobbiesPreferidos().isEmpty()) {
            double propHobbies = (double) hobbiesComun / pref.getHobbiesPreferidos().size();
            puntaje += PESO_HOBBIES * Math.min(propHobbies, 1.0);
            resumen.append("Si").append(hobbiesComun).append(" hobbies en comun\n");
        }

        // 5. Calcular generos musicales en comun
        int generosComun = contarGenerosMusicalEnComun(pref, candidato);
        resultado.setGenerosMusicalEnComun(generosComun);
        if (pref.getGenerosMusicalPreferidos() != null && !pref.getGenerosMusicalPreferidos().isEmpty()) {
            double propMusica = (double) generosComun / pref.getGenerosMusicalPreferidos().size();
            puntaje += PESO_MUSICA * Math.min(propMusica, 1.0);
            resumen.append("si").append(generosComun).append(" generos musicales en comun\n");
        }

        // 6. Calcular materias en comun
        int materiasComun = contarMateriasEnComun(pref, candidato);
        resultado.setMateriasEnComun(materiasComun);
        if (pref.getMateriasPreferidas() != null && !pref.getMateriasPreferidas().isEmpty()) {
            double propMaterias = (double) materiasComun / pref.getMateriasPreferidas().size();
            puntaje += PESO_MATERIAS * Math.min(propMaterias, 1.0);
            resumen.append("si").append(materiasComun).append(" materias en comun\n");
        }

        // 7. Verificar signo zodiacal
        boolean signoCoincide = verificarSigno(pref, candidato);
        resultado.setCoincideSigno(signoCoincide);
        if (signoCoincide) {
            puntaje += PESO_SIGNO;
            resumen.append("Signo zodiacal compatible: ").append(candidato.getSignoZodiacal()).append("\n");
        }

        resultado.setPuntajeTotal(puntaje);
        resultado.setResumenCompatibilidad(resumen.toString());

        return resultado;
    }

    private boolean verificarGenero(PreferenciasUsuarioDTO pref, UsuarioDTO candidato) {
        if (pref.getGeneroBuscado() == null || candidato.getGenero() == null) {
            return true; // Si no hay preferencia, no filtrar
        }
        return pref.getGeneroBuscado().equalsIgnoreCase(candidato.getGenero());
    }

    private boolean verificarEdad(PreferenciasUsuarioDTO pref, UsuarioDTO candidato) {
        int edad = candidato.getEdad();
        return edad >= pref.getEdadMinima() && edad <= pref.getEdadMaxima();
    }

    private boolean verificarCiudad(PreferenciasUsuarioDTO pref, UsuarioDTO candidato) {
        if (pref.getCiudadPreferida() == null || candidato.getCiudad() == null) {
            return false;
        }
        return pref.getCiudadPreferida().equalsIgnoreCase(candidato.getCiudad());
    }

    private int contarHobbiesEnComun(PreferenciasUsuarioDTO pref, UsuarioDTO candidato) {
        if (pref.getHobbiesPreferidos() == null || candidato.getHobbies() == null) {
            return 0;
        }
        int count = 0;
        for (Hobbie hobbie : pref.getHobbiesPreferidos()) {
            if (candidato.getHobbies().contains(hobbie)) {
                count++;
            }
        }
        return count;
    }

    private int contarGenerosMusicalEnComun(PreferenciasUsuarioDTO pref, UsuarioDTO candidato) {
        if (pref.getGenerosMusicalPreferidos() == null || candidato.getGenerosMusicalFavoritos() == null) {
            return 0;
        }
        int count = 0;
        for (GeneroMusical genero : pref.getGenerosMusicalPreferidos()) {
            if (candidato.getGenerosMusicalFavoritos().contains(genero)) {
                count++;
            }
        }
        return count;
    }

    private int contarMateriasEnComun(PreferenciasUsuarioDTO pref, UsuarioDTO candidato) {
        if (pref.getMateriasPreferidas() == null || candidato.getMateriasFavoritas() == null) {
            return 0;
        }
        int count = 0;
        for (MateriaEscolar materia : pref.getMateriasPreferidas()) {
            if (candidato.getMateriasFavoritas().contains(materia)) {
                count++;
            }
        }
        return count;
    }

    private boolean verificarSigno(PreferenciasUsuarioDTO pref, UsuarioDTO candidato) {
        if (pref.getSignosPreferidos() == null || pref.getSignosPreferidos().isEmpty()
                || candidato.getSignoZodiacal() == null) {
            return false;
        }
        for (String signo : pref.getSignosPreferidos()) {
            if (signo.equalsIgnoreCase(candidato.getSignoZodiacal())) {
                return true;
            }
        }
        return false;
    }
}
