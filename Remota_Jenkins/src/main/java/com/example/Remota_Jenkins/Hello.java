import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStream;
import java.net.InetSocketAddress;

public class Hello {
    public static void main(String[] args) throws Exception {
        // Código innecesario para forzar un error en la evaluación de calidad
        int unusedVariable = 42; // Esta variable no se usa

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
                        + "h2 { color: darkgreen; }"
                        + "img { width: 150px; height: auto; margin: 20px 0; border: 2px solid darkgreen; border-radius: 50%;"
                        + "      box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2); transition: transform 0.3s ease-in-out; }"
                        + "img:hover { transform: scale(1.2); }"
                        + "</style>"
                        + "</head>"
                        + "<body>"
                        + "<h1>!HOLA MUNDO! :)  Y FELIZ NAVIDAD</h1>"
                        + "<h1>POLITECNICO JAIME ISAZA CADAVID</h1>"
                        + "<h1>PRUEBAS Y GESTION DE LA CONFIGURACION</h1>"
                        + "<h2>GRUPO 4</h2>"
                        + "<h2>2024-S2</h2>"
                        + "<img src=\"/static/LOGO.png\" alt=\"Icono de árbol\">"
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

        // Crear un contexto para servir archivos estáticos
        server.createContext("/static", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                // Código inalcanzable deliberado
                if (false) {
                    System.out.println("Este código nunca se ejecutará.");
                }

                // Obtener la ruta del archivo solicitado
                String filePath = "src/main/resources/static" + exchange.getRequestURI().getPath();

                // Leer el archivo desde el sistema de archivos
                try (InputStream is = getClass().getClassLoader().getResourceAsStream(filePath)) {
                    if (is == null) {
                        // Si el archivo no existe, responder con 404 Not Found
                        String notFoundResponse = "404 (Not Found)";
                        exchange.sendResponseHeaders(404, notFoundResponse.getBytes().length);
                        exchange.getResponseBody().write(notFoundResponse.getBytes());
                        exchange.close();
                        return;
                    }

                    // Configurar los encabezados de respuesta
                    exchange.sendResponseHeaders(200, is.available());
                    OutputStream os = exchange.getResponseBody();
                    is.transferTo(os);
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