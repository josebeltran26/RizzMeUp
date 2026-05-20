package enviarmsg.persistencia;

import enviarmsg.negocio.dto.MensajeDTO;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementacion del DAO de persistencia local que mapea y delega al
 * modulo de persistencia global de MongoDB.
 */
public class MensajeDAO implements IMensajeDAO {

    private final persistencia.IMensajeDAO daoGlobal;

    public MensajeDAO() {
        this.daoGlobal = new persistencia.MensajeDAO();
    }

    @Override
    public Long guardar(MensajeDTO mensaje) {
        dto.MensajeDTO globalMsg = new dto.MensajeDTO(
                mensaje.getId(),
                mensaje.getMatchId(),
                mensaje.getRemitenteId(),
                mensaje.getDestinatarioId(),
                mensaje.getContenido(),
                mensaje.getFechaEnvio(),
                mensaje.isLeido()
        );
        
        Long id = daoGlobal.insertar(globalMsg);
        mensaje.setId(id);
        return id;
    }

    @Override
    public List<MensajeDTO> obtenerMensajesPorMatch(Long matchId) {
        List<dto.MensajeDTO> listGlobal = daoGlobal.buscarPorMatch(matchId);
        List<MensajeDTO> localList = new ArrayList<>();
        
        for (dto.MensajeDTO m : listGlobal) {
            localList.add(new MensajeDTO(
                    m.getId(),
                    m.getMatchId(),
                    m.getRemitenteId(),
                    m.getDestinatarioId(),
                    m.getContenido(),
                    m.getFechaEnvio(),
                    m.isLeido()
            ));
        }
        return localList;
    }

    @Override
    public void marcarComoLeidos(Long matchId, Long destinatarioId) {
        daoGlobal.marcarComoLeidos(matchId, destinatarioId);
    }
}
