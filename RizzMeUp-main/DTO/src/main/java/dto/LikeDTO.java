package dto;

import java.time.LocalDateTime;

/**
 * Esto es cuando un usuario le da "Rizz Me Up" o "Saltar" a otro perfil,
 * basicamente el swipe de la app.
 *
 * @author USUARIO
 */
public class LikeDTO {

    private Long id;
    private Long usuarioOrigenId;
    private Long usuarioDestinoId;
    private boolean esLike;
    private LocalDateTime fechaLike;

    public LikeDTO() {
    }

    public LikeDTO(Long id, Long usuarioOrigenId, Long usuarioDestinoId,
            boolean esLike, LocalDateTime fechaLike) {
        this.id = id;
        this.usuarioOrigenId = usuarioOrigenId;
        this.usuarioDestinoId = usuarioDestinoId;
        this.esLike = esLike;
        this.fechaLike = fechaLike;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUsuarioOrigenId() {
        return usuarioOrigenId;
    }

    public void setUsuarioOrigenId(Long usuarioOrigenId) {
        this.usuarioOrigenId = usuarioOrigenId;
    }

    public Long getUsuarioDestinoId() {
        return usuarioDestinoId;
    }

    public void setUsuarioDestinoId(Long usuarioDestinoId) {
        this.usuarioDestinoId = usuarioDestinoId;
    }

    public boolean isEsLike() {
        return esLike;
    }

    public void setEsLike(boolean esLike) {
        this.esLike = esLike;
    }

    public LocalDateTime getFechaLike() {
        return fechaLike;
    }

    public void setFechaLike(LocalDateTime fechaLike) {
        this.fechaLike = fechaLike;
    }

    @Override
    public String toString() {
        return "LikeDTO{"
                + "id=" + id
                + ", usuarioOrigenId=" + usuarioOrigenId
                + ", usuarioDestinoId=" + usuarioDestinoId
                + ", esLike=" + esLike
                + ", fechaLike=" + fechaLike
                + '}';
    }
}
