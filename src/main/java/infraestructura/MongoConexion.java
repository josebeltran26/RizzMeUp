/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package infraestructura;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

/**
 *
 * @author Roger Jr
 */
public class MongoConexion 
{
    private static MongoConexion instancia;
    private MongoClient cliente;
    private MongoDatabase baseDatos;

    private static final String CADENA_CONEXION = "mongodb://localhost:27017";
    private static final String NOMBRE_BD = "rizzmeup";

    private MongoConexion() 
    {
        cliente = MongoClients.create(CADENA_CONEXION);
        baseDatos = cliente.getDatabase(NOMBRE_BD);
    }

    public static MongoConexion getInstancia() 
    {
        if (instancia == null) 
        {
            instancia = new MongoConexion();
        }
        return instancia;
    }

    public MongoDatabase getBaseDatos() 
    {
        return baseDatos;
    }

    public void cerrarConexion() 
    {
        if (cliente != null) 
        {
            cliente.close();
        }
    }
}
