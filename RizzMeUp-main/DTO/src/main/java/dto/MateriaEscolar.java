package dto;

/**
 * Las materias de la escuela que le gustan al usuario
 *
 * @author USUARIO
 */
public enum MateriaEscolar {
    MATEMATICAS("Matemáticas"),
    FISICA("Física"),
    QUIMICA("Química"),
    BIOLOGIA("Biología"),
    HISTORIA("Historia"),
    LITERATURA("Literatura"),
    FILOSOFIA("Filosofía"),
    INGLES("Inglés"),
    FRANCES("Francés"),
    PROGRAMACION("Programación"),
    BASE_DE_DATOS("Base de Datos"),
    REDES("Redes"),
    ECONOMIA("Economía"),
    ADMINISTRACION("Administración"),
    CONTABILIDAD("Contabilidad"),
    DERECHO("Derecho"),
    PSICOLOGIA("Psicología"),
    ARTE("Arte"),
    MUSICA("Música"),
    EDUCACION_FISICA("Educación Física"),
    INGENIERIA_SOFTWARE("Ingeniería de Software"),
    ARQUITECTURA("Arquitectura"),
    DISEÑO("Diseño"),
    MEDICINA("Medicina"),
    COMUNICACION("Comunicación"),
    SOCIOLOGIA("Sociología"),
    ELECTRONICA("Electrónica"),
    MECANICA("Mecánica"),
    OTRA("Otra");

    private final String nombre;

    MateriaEscolar(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
