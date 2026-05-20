/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio.mapper;

import datos.entidades.Usuario;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 *
 * @author Roger Jr
 */
public class UsuarioMapper implements IUsuarioMapper
{
    private static UsuarioMapper instancia;

    private UsuarioMapper()
    {
    }

    public static UsuarioMapper getInstancia()
    {
        if (instancia == null)
        {
            instancia = new UsuarioMapper();
        }
        return instancia;
    }

    @Override
    public Document toDocument(Usuario u)
    {
        Document doc = new Document();

        if (u.getId() != null)
        {
            doc.append("_id", new ObjectId(u.getId()));
        }

        doc.append("nombre", u.getNombre());
        doc.append("estadoCuenta", u.getEstadoCuenta());
        doc.append("advertencias", u.getAdvertencias());
        doc.append("correo", u.getCorreo());
        doc.append("fotoPerfilUrl", u.getFotoPerfilUrl());

        return doc;
    }

    @Override
    public Usuario toEntity(Document d)
    {
        if (d == null)
        {
            return null;
        }

        Usuario u = new Usuario();

        u.setId(d.getObjectId("_id").toHexString());
        u.setNombre(d.getString("nombre"));
        u.setEstadoCuenta(d.getString("estadoCuenta"));

        // advertencias puede ser null si es usuario nuevo
        Integer adv = d.getInteger("advertencias");
        u.setAdvertencias(adv != null ? adv : 0);

        u.setCorreo(d.getString("correo"));
        u.setFotoPerfilUrl(d.getString("fotoPerfilUrl"));

        return u;
    }

    @Override
    public List<Usuario> toListEntity(List<Document> lista)
    {
        List<Usuario> resultado = new ArrayList<>();
        if (lista == null)
        {
            return resultado;
        }
        for (Document d : lista)
        {
            resultado.add(toEntity(d));
        }
        return resultado;
    }
}