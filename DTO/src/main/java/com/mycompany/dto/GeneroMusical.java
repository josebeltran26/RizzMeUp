package com.mycompany.dto;

/**
 * Todos los generos musicales
 *
 * @author USUARIO
 */
public enum GeneroMusical {
    POP("Pop"),
    ROCK("Rock"),
    REGGAETON("Reggaetón"),
    HIP_HOP("Hip Hop"),
    RAP("Rap"),
    ELECTRONICA("Electrónica"),
    JAZZ("Jazz"),
    BLUES("Blues"),
    METAL("Metal"),
    INDIE("Indie"),
    R_AND_B("R&B"),
    CUMBIA("Cumbia"),
    SALSA("Salsa"),
    BACHATA("Bachata"),
    CORRIDOS("Corridos"),
    BANDA("Banda"),
    CLASICA("Clásica"),
    KPOP("K-Pop"),
    LATIN("Latín"),
    TRAP("Trap"),
    COUNTRY("Country"),
    OTRO("Otro");

    private final String nombre;

    GeneroMusical(String nombre) {
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
