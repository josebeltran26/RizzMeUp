package dto;

import java.io.InputStream;
import java.util.Base64;

/**
 * Utilidad para cargar imagenes desde los recursos
 * y convertirlas a Base64 para guardarlas en los DTOs.
 *
 * @author USUARIO
 */
public class ImagenUtil {

    /**
     * Carga una imagen desde los recursos del classpath y la devuelve como String Base64.
     *
     * @param rutaRecurso la ruta del recurso (ejemplo: "/hombre1.jpg")
     * @return la imagen codificada en Base64, o null si no se encuentra
     */
    public static String cargarImagenBase64(String rutaRecurso) {
        try (InputStream is = ImagenUtil.class.getResourceAsStream(rutaRecurso)) {
            if (is == null) {
                System.out.println("No se encontro la imagen: " + rutaRecurso);
                return null;
            }
            byte[] bytes = is.readAllBytes();
            return Base64.getEncoder().encodeToString(bytes);
        } catch (Exception e) {
            System.out.println("Error al cargar imagen: " + e.getMessage());
            return null;
        }
    }
}
