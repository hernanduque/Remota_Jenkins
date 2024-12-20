import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.io.File;
import java.nio.file.Files;
import java.net.InetSocketAddress;

public class Hello {
    public static void main(String[] args) throws Exception {
        // Crear el servidor HTTP en el puerto 8000
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

        // Definir un manejador para la ruta "/"
        server.createContext("/", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                // Respuesta HTML con fondo verde, imagen y textos
                String response = "<html>"
                        + "<head>"
                        + "<style>"
                        + "body { background-color: #90EE90; font-family: Arial, sans-serif; text-align: center; padding: 20px; }"
                        + "h1 { color: darkgreen; }"
                        + "h2 { color: darkblue; }"
                        + "img { width: 300px; height: auto; margin: 20px 0; }"
                        + "</style>"
                        + "</head>"
                        + "<body>"
                        // Imagen cargada desde la ruta "/static/LOGO.png"
                        + "<h1>!HOLA MUNDO! :) </h1>"
                        + "<h1>PRUEBAS Y GESTION DE LA CONFIGURACION</h1>"
                        + "<h2>GRUPO 4</h2>"
                        + "</body>"
                        + "</html>";

                // Enviar los encabezados de la respuesta con el código HTTP 200 OK
                exchange.sendResponseHeaders(200, response.getBytes().length);

                // Enviar la respuesta
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        });

        // Crear un manejador para la ruta "/static/" que sirve archivos estáticos como imágenes
        server.createContext("/static", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                // Resolviendo la ruta completa al archivo dentro de la carpeta "resources"
                String path = exchange.getRequestURI().getPath();
                // Usamos la carpeta "resources" dentro del proyecto
                File file = new File("F:/Desktop/2024-S2/PRUEBAS/JENKINS/Remota_Jenkins/Remota_Jenkins/src/main/resources" + path);

                if (file.exists() && !file.isDirectory()) {
                    // Obtener el tipo MIME de la imagen
                    String mimeType = Files.probeContentType(file.toPath());
                    exchange.getResponseHeaders().add("Content-Type", mimeType);

                    // Leer y enviar la imagen
                    byte[] imageBytes = Files.readAllBytes(file.toPath());
                    exchange.sendResponseHeaders(200, imageBytes.length);
                    OutputStream os = exchange.getResponseBody();
                    os.write(imageBytes);
                    os.close();
                } else {
                    // Si el archivo no se encuentra, enviar un error 404
                    String response = "Archivo no encontrado.";
                    exchange.sendResponseHeaders(404, response.getBytes().length);
                    OutputStream os = exchange.getResponseBody();
                    os.write(response.getBytes());
                    os.close();
                }
            }
        });

        // Iniciar el servidor
        server.setExecutor(null); // usa el ejecutor por defecto
        System.out.println("Servidor HTTP corriendo en http://localhost:8000/");
        server.start();
    }
}
