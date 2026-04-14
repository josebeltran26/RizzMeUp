package dto;

/**
 * Resultado de compatibilidad entre un usuario y un candidato.
 * Incluye el puntaje calculado y un desglose de por que son compatibles.
 *
 * @author USUARIO
 */
public class ResultadoCompatibilidadDTO {

    private UsuarioDTO candidato;
    private double puntajeTotal;
    private int hobbiesEnComun;
    private int generosMusicalEnComun;
    private int materiasEnComun;
    private boolean coincideCiudad;
    private boolean coincideEdad;
    private boolean coincideGenero;
    private boolean coincideSigno;
    private String resumenCompatibilidad;

    public ResultadoCompatibilidadDTO() {
    }

    public UsuarioDTO getCandidato() {
        return candidato;
    }

    public void setCandidato(UsuarioDTO candidato) {
        this.candidato = candidato;
    }

    public double getPuntajeTotal() {
        return puntajeTotal;
    }

    public void setPuntajeTotal(double puntajeTotal) {
        this.puntajeTotal = puntajeTotal;
    }

    public int getHobbiesEnComun() {
        return hobbiesEnComun;
    }

    public void setHobbiesEnComun(int hobbiesEnComun) {
        this.hobbiesEnComun = hobbiesEnComun;
    }

    public int getGenerosMusicalEnComun() {
        return generosMusicalEnComun;
    }

    public void setGenerosMusicalEnComun(int generosMusicalEnComun) {
        this.generosMusicalEnComun = generosMusicalEnComun;
    }

    public int getMateriasEnComun() {
        return materiasEnComun;
    }

    public void setMateriasEnComun(int materiasEnComun) {
        this.materiasEnComun = materiasEnComun;
    }

    public boolean isCoincideCiudad() {
        return coincideCiudad;
    }

    public void setCoincideCiudad(boolean coincideCiudad) {
        this.coincideCiudad = coincideCiudad;
    }

    public boolean isCoincideEdad() {
        return coincideEdad;
    }

    public void setCoincideEdad(boolean coincideEdad) {
        this.coincideEdad = coincideEdad;
    }

    public boolean isCoincideGenero() {
        return coincideGenero;
    }

    public void setCoincideGenero(boolean coincideGenero) {
        this.coincideGenero = coincideGenero;
    }

    public boolean isCoincideSigno() {
        return coincideSigno;
    }

    public void setCoincideSigno(boolean coincideSigno) {
        this.coincideSigno = coincideSigno;
    }

    public String getResumenCompatibilidad() {
        return resumenCompatibilidad;
    }

    public void setResumenCompatibilidad(String resumenCompatibilidad) {
        this.resumenCompatibilidad = resumenCompatibilidad;
    }

    @Override
    public String toString() {
        return "ResultadoCompatibilidadDTO{"
                + "candidato=" + (candidato != null ? candidato.getNombre() : "null")
                + ", puntaje=" + String.format("%.1f", puntajeTotal) + "%"
                + ", hobbiesEnComun=" + hobbiesEnComun
                + ", generosEnComun=" + generosMusicalEnComun
                + ", materiasEnComun=" + materiasEnComun
                + ", coincideCiudad=" + coincideCiudad
                + ", coincideEdad=" + coincideEdad
                + '}';
    }
}
