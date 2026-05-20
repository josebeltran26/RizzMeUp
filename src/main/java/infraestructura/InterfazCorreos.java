package infraestructura;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;

public class InterfazCorreos
{
    private static InterfazCorreos instancia;

    private static final String SMTP_HOST = "smtp.gmail.com";
    private static final int SMTP_PORT = 587;
    private static final String SMTP_USER = "rabarcelo11@gmail.com";
    private static final String SMTP_PASSWORD = "fcty zsgw phpa hvra";

    private InterfazCorreos()
    {
    }

    public static InterfazCorreos getInstancia()
    {
        if (instancia == null)
        {
            instancia = new InterfazCorreos();
        }
        return instancia;
    }

    public void enviarNotificacionReporte(String correoDestino, String msg)
            throws MessagingException
    {
        Session sesion = crearSesion();
        MimeMessage mensaje = construirMensaje(sesion, correoDestino,
                "Notificacion de reporte - Rizz Me Up", msg);
        Transport.send(mensaje);
    }

    public void enviarNotificacionSancion(String correoDestino, String msg)
            throws MessagingException
    {
        validarCorreoDestino(correoDestino);
        Session sesion = crearSesion();
        MimeMessage mensaje = construirMensaje(sesion, correoDestino,
                "Notificacion de sancion - Rizz Me Up", msg);
        Transport.send(mensaje);
    }

    // configura la sesion smtp con autenticacion tls
    private Session crearSesion()
    {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", SMTP_HOST);
        props.put("mail.smtp.port", String.valueOf(SMTP_PORT));

        return Session.getInstance(props, new Authenticator()
        {
            @Override
            protected PasswordAuthentication getPasswordAuthentication()
            {
                return new PasswordAuthentication(SMTP_USER, SMTP_PASSWORD);
            }
        });
    }

    // construye el mensaje mime con asunto y cuerpo
    private MimeMessage construirMensaje(
            Session sesion,
            String correoDestino,
            String asunto,
            String cuerpo) throws MessagingException
    {
        MimeMessage mensaje = new MimeMessage(sesion);
        mensaje.setFrom(new InternetAddress(SMTP_USER));
        mensaje.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(correoDestino));
        mensaje.setSubject(asunto);
        mensaje.setText(cuerpo);
        return mensaje;
    }

    // valida que el correo destino no sea nulo ni vacio antes de enviar
    private void validarCorreoDestino(String correo) throws MessagingException
    {
        if (correo == null || correo.trim().isEmpty())
        {
            throw new MessagingException(
                    "el correo destino no puede ser nulo o vacio");
        }
    }
}