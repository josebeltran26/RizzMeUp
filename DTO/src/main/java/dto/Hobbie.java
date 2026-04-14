package dto;

/**
 * Los hobbies o pasatiempos que puede tener el usuario
 *
 * @author USUARIO
 */
public enum Hobbie {
    LEER("Leer"),
    VIAJAR("Viajar"),
    COCINAR("Cocinar"),
    DEPORTES("Deportes"),
    FOTOGRAFIA("Fotografía"),
    VIDEOJUEGOS("Videojuegos"),
    PINTURA("Pintura"),
    DIBUJO("Dibujo"),
    CINE("Cine"),
    SERIES("Series"),
    ANIME("Anime"),
    MUSICA("Tocar música"),
    BAILAR("Bailar"),
    YOGA("Yoga"),
    GYM("Gym"),
    CORRER("Correr"),
    SENDERISMO("Senderismo"),
    NATACION("Natación"),
    CAMPING("Camping"),
    JARDINERIA("Jardinería"),
    MANUALIDADES("Manualidades"),
    ESCRITURA("Escritura"),
    PROGRAMACION("Programación"),
    IDIOMAS("Aprender idiomas"),
    MASCOTAS("Mascotas"),
    SKATEBOARDING("Skateboarding"),
    CICLISMO("Ciclismo"),
    MEDITACION("Meditación"),
    VOLUNTARIADO("Voluntariado"),
    OTRO("Otro");

    private final String nombre;

    Hobbie(String nombre) {
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
