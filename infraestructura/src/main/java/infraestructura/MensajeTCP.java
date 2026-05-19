package infraestructura;

/**
 * Mensaje intercambiado entre cliente y servidor via TCP.
 * Formato: JSON de una sola linea terminada en \n
 *
 * Tipos cliente → servidor:
 *   REGISTER        - registrar nuevo usuario
 *   UPDATE          - actualizar perfil
 *   ELIMINAR        - desactivar usuario
 *   LIKE            - registrar swipe
 *   GET_CANDIDATOS  - pedir lista de candidatos
 *   GET_USUARIO     - pedir un usuario por ID
 *   GET_PREFERENCIAS
 *   GUARDAR_PREFERENCIAS
 *   GET_MATCHES
 *   GUARDAR_MATCH
 *   GUARDAR_MENSAJE
 *   GET_MENSAJES
 *
 * Tipos servidor → cliente (respuesta):
 *   OK              - operacion exitosa, payload con resultado
 *   ERROR           - operacion fallida, payload con mensaje
 *
 * Tipos servidor → todos los clientes (broadcast):
 *   NUEVO_USUARIO   - alguien se acaba de registrar
 *   USUARIO_ACTUALIZADO
 *   NUEVO_MATCH
 *   NUEVO_MENSAJE
 *
 * @author USUARIO
 */
public class MensajeTCP {

    private String tipo;
    private String payload;

    public MensajeTCP() {
    }

    public MensajeTCP(String tipo, String payload) {
        this.tipo = tipo;
        this.payload = payload;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }
}
