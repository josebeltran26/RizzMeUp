package explorarperfiles;

import dto.LikeDTO;
import dto.MatchDTO;
import dto.UsuarioDTO;

/**
 * Contrato del Business Object de Like/Swipe.
 * Define las reglas de negocio del flujo de swipe.
 *
 * @author USUARIO
 */
public interface ILikeBO {

    /**
     * Valida que el like sea correcto (IDs no nulos, no likearse a si mismo).
     * @return null si es valido, mensaje de error si no
     */
    String validar();

    /**
     * Retorna el DTO subyacente.
     */
    LikeDTO getDTO();

    /**
     * Indica si este like es positivo (Rizz Me Up) o negativo (Saltar).
     */
    boolean esPositivo();

    /**
     * Crea un MatchDTO a partir de este like mutuo.
     * Solo llamar cuando se confirme que existe like mutuo.
     */
    MatchDTO crearMatch();
}
