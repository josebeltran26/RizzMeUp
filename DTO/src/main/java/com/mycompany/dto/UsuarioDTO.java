package com.mycompany.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Aqui se guarda toda la info del usuario
 *
 * @author USUARIO
 */
public class UsuarioDTO {

    private Long id;
    private String correo;
    private String contrasena;
    private String nombre;
    private int edad;
    private String profesion;
    private String descripcionPersonal;
    private String ciudad;
    private String fotoPerfilBase64;
    private String genero;
    private LocalDate fechaNacimiento;
    private LocalDateTime fechaRegistro;
    private boolean activo;

    private List<GeneroMusical> generosMusicalFavoritos;
    private List<Hobbie> hobbies;
    private List<MateriaEscolar> materiasFavoritas;

    private String escuela;
    private String nivelEstudios;
    private String signoZodiacal;
    private String redSocialInstagram;
    private String redSocialTiktok;

    private String estacionFavorita;
    private String videojuegoFavorito;
    private String peliculaFavorita;
    private String serieFavorita;
    private String cancionFavorita;
    private String artistaFavorito;
    private String comidaFavorita;
    private String bebidaFavorita;
    private String animalFavorito;
    private String colorFavorito;
    private String emojiFavorito;
    private String frasePersonal;
    private String planIdealCita;
    private String tipoMascota;
    private String superpoder;
    private String deporteFavorito;
    private String lugarSonado;

    public UsuarioDTO() {
    }

    public UsuarioDTO(Long id, String correo, String contrasena, String nombre,
            int edad, String profesion, String descripcionPersonal,
            String ciudad, String fotoPerfilBase64, String genero,
            LocalDate fechaNacimiento, LocalDateTime fechaRegistro, boolean activo,
            List<GeneroMusical> generosMusicalFavoritos, List<Hobbie> hobbies,
            List<MateriaEscolar> materiasFavoritas, String escuela,
            String nivelEstudios, String signoZodiacal,
            String redSocialInstagram, String redSocialTiktok,
            String estacionFavorita, String videojuegoFavorito,
            String peliculaFavorita, String serieFavorita,
            String cancionFavorita, String artistaFavorito,
            String comidaFavorita, String bebidaFavorita,
            String animalFavorito, String colorFavorito,
            String emojiFavorito, String frasePersonal,
            String planIdealCita, String tipoMascota,
            String superpoder, String deporteFavorito, String lugarSonado) {
        this.id = id;
        this.correo = correo;
        this.contrasena = contrasena;
        this.nombre = nombre;
        this.edad = edad;
        this.profesion = profesion;
        this.descripcionPersonal = descripcionPersonal;
        this.ciudad = ciudad;
        this.fotoPerfilBase64 = fotoPerfilBase64;
        this.genero = genero;
        this.fechaNacimiento = fechaNacimiento;
        this.fechaRegistro = fechaRegistro;
        this.activo = activo;
        this.generosMusicalFavoritos = generosMusicalFavoritos;
        this.hobbies = hobbies;
        this.materiasFavoritas = materiasFavoritas;
        this.escuela = escuela;
        this.nivelEstudios = nivelEstudios;
        this.signoZodiacal = signoZodiacal;
        this.redSocialInstagram = redSocialInstagram;
        this.redSocialTiktok = redSocialTiktok;
        this.estacionFavorita = estacionFavorita;
        this.videojuegoFavorito = videojuegoFavorito;
        this.peliculaFavorita = peliculaFavorita;
        this.serieFavorita = serieFavorita;
        this.cancionFavorita = cancionFavorita;
        this.artistaFavorito = artistaFavorito;
        this.comidaFavorita = comidaFavorita;
        this.bebidaFavorita = bebidaFavorita;
        this.animalFavorito = animalFavorito;
        this.colorFavorito = colorFavorito;
        this.emojiFavorito = emojiFavorito;
        this.frasePersonal = frasePersonal;
        this.planIdealCita = planIdealCita;
        this.tipoMascota = tipoMascota;
        this.superpoder = superpoder;
        this.deporteFavorito = deporteFavorito;
        this.lugarSonado = lugarSonado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getProfesion() {
        return profesion;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }

    public String getDescripcionPersonal() {
        return descripcionPersonal;
    }

    public void setDescripcionPersonal(String descripcionPersonal) {
        this.descripcionPersonal = descripcionPersonal;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getFotoPerfilBase64() {
        return fotoPerfilBase64;
    }

    public void setFotoPerfilBase64(String fotoPerfilBase64) {
        this.fotoPerfilBase64 = fotoPerfilBase64;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public List<GeneroMusical> getGenerosMusicalFavoritos() {
        return generosMusicalFavoritos;
    }

    public void setGenerosMusicalFavoritos(List<GeneroMusical> generosMusicalFavoritos) {
        this.generosMusicalFavoritos = generosMusicalFavoritos;
    }

    public List<Hobbie> getHobbies() {
        return hobbies;
    }

    public void setHobbies(List<Hobbie> hobbies) {
        this.hobbies = hobbies;
    }

    public List<MateriaEscolar> getMateriasFavoritas() {
        return materiasFavoritas;
    }

    public void setMateriasFavoritas(List<MateriaEscolar> materiasFavoritas) {
        this.materiasFavoritas = materiasFavoritas;
    }

    public String getEscuela() {
        return escuela;
    }

    public void setEscuela(String escuela) {
        this.escuela = escuela;
    }

    public String getNivelEstudios() {
        return nivelEstudios;
    }

    public void setNivelEstudios(String nivelEstudios) {
        this.nivelEstudios = nivelEstudios;
    }

    public String getSignoZodiacal() {
        return signoZodiacal;
    }

    public void setSignoZodiacal(String signoZodiacal) {
        this.signoZodiacal = signoZodiacal;
    }

    public String getRedSocialInstagram() {
        return redSocialInstagram;
    }

    public void setRedSocialInstagram(String redSocialInstagram) {
        this.redSocialInstagram = redSocialInstagram;
    }

    public String getRedSocialTiktok() {
        return redSocialTiktok;
    }

    public void setRedSocialTiktok(String redSocialTiktok) {
        this.redSocialTiktok = redSocialTiktok;
    }

    public String getEstacionFavorita() {
        return estacionFavorita;
    }

    public void setEstacionFavorita(String estacionFavorita) {
        this.estacionFavorita = estacionFavorita;
    }

    public String getVideojuegoFavorito() {
        return videojuegoFavorito;
    }

    public void setVideojuegoFavorito(String videojuegoFavorito) {
        this.videojuegoFavorito = videojuegoFavorito;
    }

    public String getPeliculaFavorita() {
        return peliculaFavorita;
    }

    public void setPeliculaFavorita(String peliculaFavorita) {
        this.peliculaFavorita = peliculaFavorita;
    }

    public String getSerieFavorita() {
        return serieFavorita;
    }

    public void setSerieFavorita(String serieFavorita) {
        this.serieFavorita = serieFavorita;
    }

    public String getCancionFavorita() {
        return cancionFavorita;
    }

    public void setCancionFavorita(String cancionFavorita) {
        this.cancionFavorita = cancionFavorita;
    }

    public String getArtistaFavorito() {
        return artistaFavorito;
    }

    public void setArtistaFavorito(String artistaFavorito) {
        this.artistaFavorito = artistaFavorito;
    }

    public String getComidaFavorita() {
        return comidaFavorita;
    }

    public void setComidaFavorita(String comidaFavorita) {
        this.comidaFavorita = comidaFavorita;
    }

    public String getBebidaFavorita() {
        return bebidaFavorita;
    }

    public void setBebidaFavorita(String bebidaFavorita) {
        this.bebidaFavorita = bebidaFavorita;
    }

    public String getAnimalFavorito() {
        return animalFavorito;
    }

    public void setAnimalFavorito(String animalFavorito) {
        this.animalFavorito = animalFavorito;
    }

    public String getColorFavorito() {
        return colorFavorito;
    }

    public void setColorFavorito(String colorFavorito) {
        this.colorFavorito = colorFavorito;
    }

    public String getEmojiFavorito() {
        return emojiFavorito;
    }

    public void setEmojiFavorito(String emojiFavorito) {
        this.emojiFavorito = emojiFavorito;
    }

    public String getFrasePersonal() {
        return frasePersonal;
    }

    public void setFrasePersonal(String frasePersonal) {
        this.frasePersonal = frasePersonal;
    }

    public String getPlanIdealCita() {
        return planIdealCita;
    }

    public void setPlanIdealCita(String planIdealCita) {
        this.planIdealCita = planIdealCita;
    }

    public String getTipoMascota() {
        return tipoMascota;
    }

    public void setTipoMascota(String tipoMascota) {
        this.tipoMascota = tipoMascota;
    }

    public String getSuperpoder() {
        return superpoder;
    }

    public void setSuperpoder(String superpoder) {
        this.superpoder = superpoder;
    }

    public String getDeporteFavorito() {
        return deporteFavorito;
    }

    public void setDeporteFavorito(String deporteFavorito) {
        this.deporteFavorito = deporteFavorito;
    }

    public String getLugarSonado() {
        return lugarSonado;
    }

    public void setLugarSonado(String lugarSonado) {
        this.lugarSonado = lugarSonado;
    }

    @Override
    public String toString() {
        return "UsuarioDTO{"
                + "id=" + id
                + ", correo='" + correo + '\''
                + ", nombre='" + nombre + '\''
                + ", edad=" + edad
                + ", profesion='" + profesion + '\''
                + ", ciudad='" + ciudad + '\''
                + ", genero='" + genero + '\''
                + ", escuela='" + escuela + '\''
                + ", nivelEstudios='" + nivelEstudios + '\''
                + ", signoZodiacal='" + signoZodiacal + '\''
                + ", generosMusicalFavoritos=" + generosMusicalFavoritos
                + ", hobbies=" + hobbies
                + ", materiasFavoritas=" + materiasFavoritas
                + ", estacionFavorita='" + estacionFavorita + '\''
                + ", videojuegoFavorito='" + videojuegoFavorito + '\''
                + ", peliculaFavorita='" + peliculaFavorita + '\''
                + ", serieFavorita='" + serieFavorita + '\''
                + ", comidaFavorita='" + comidaFavorita + '\''
                + ", deporteFavorito='" + deporteFavorito + '\''
                + ", lugarSonado='" + lugarSonado + '\''
                + ", activo=" + activo
                + '}';
    }
}
