package persistencia;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import java.util.concurrent.TimeUnit;

/**
 * Singleton de conexion a MongoDB.
 * Una sola instancia de MongoClient para toda la aplicacion.
 * Timeout de 4 segundos para que el servidor devuelva error rapido
 * en lugar de colgar el cliente TCP.
 *
 * @author USUARIO
 */
public class MongoConexion {

    private static final String URI = "mongodb://localhost:27017";
    private static final String NOMBRE_BD = "rizzmeup";

    private static MongoConexion instancia;
    private final MongoClient cliente;
    private final MongoDatabase base;

    private MongoConexion() {
        cliente = MongoClients.create(URI);
        base = cliente.getDatabase(NOMBRE_BD);
        System.out.println("[MongoDB] Conectado a " + NOMBRE_BD);
    }

    /**
     * Obtiene la instancia unica de la conexion.
     */
    public static synchronized MongoConexion getInstance() {
        if (instancia == null) {
            instancia = new MongoConexion();
        }
        return instancia;
    }

    /**
     * Devuelve la base de datos activa.
     */
    public MongoDatabase getBase() {
        return base;
    }

    /**
     * Cierra la conexion con MongoDB.
     */
    public void cerrar() {
        if (cliente != null) {
            cliente.close();
            System.out.println("[MongoDB] Conexion cerrada.");
        }
    }
}
