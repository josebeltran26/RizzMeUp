package explorarperfiles;

import dto.LikeDTO;
import dto.MatchDTO;
import java.time.LocalDateTime;

/**
 * Business Object del Like/Swipe.
 * Encapsula las reglas de negocio del flujo de swipe.
 *
 * @author USUARIO
 */
public class LikeBO implements ILikeBO {

    private final LikeDTO dto;

    public LikeBO(LikeDTO dto) {
        this.dto = dto;
    }

    /**
     * Factory: crea un like positivo (Rizz Me Up).
     */
    public static LikeBO rizzMeUp(Long origenId, Long destinoId) {
        LikeDTO like = new LikeDTO();
        like.setUsuarioOrigenId(origenId);
        like.setUsuarioDestinoId(destinoId);
        like.setEsLike(true);
        like.setFechaLike(LocalDateTime.now());
        return new LikeBO(like);
    }

    /**
     * Factory: crea un saltar (dislike).
     */
    public static LikeBO saltar(Long origenId, Long destinoId) {
        LikeDTO like = new LikeDTO();
        like.setUsuarioOrigenId(origenId);
        like.setUsuarioDestinoId(destinoId);
        like.setEsLike(false);
        like.setFechaLike(LocalDateTime.now());
        return new LikeBO(like);
    }

    // =========================================================================
    // ILikeBO
    // =========================================================================

    @Override
    public String validar() {
        if (dto.getUsuarioOrigenId() == null)
            return "El usuario origen no puede ser nulo.";
        if (dto.getUsuarioDestinoId() == null)
            return "El usuario destino no puede ser nulo.";
        if (dto.getUsuarioOrigenId().equals(dto.getUsuarioDestinoId()))
            return "Un usuario no puede darse like a si mismo.";
        return null;
    }

    @Override
    public LikeDTO getDTO() {
        return dto;
    }

    @Override
    public boolean esPositivo() {
        return dto.isEsLike();
    }

    @Override
    public MatchDTO crearMatch() {
        MatchDTO match = new MatchDTO();
        match.setUsuario1Id(dto.getUsuarioOrigenId());
        match.setUsuario2Id(dto.getUsuarioDestinoId());
        match.setFechaMatch(LocalDateTime.now());
        match.setActivo(true);
        return match;
    }
}
