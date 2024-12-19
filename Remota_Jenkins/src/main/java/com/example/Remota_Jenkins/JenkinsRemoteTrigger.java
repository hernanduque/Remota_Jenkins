import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

public class JenkinsRemoteTrigger {
    public static void main(String[] args) {
        // Configuración global de estilo para cuadros de diálogo
        UIManager.put("OptionPane.background", new Color(200, 230, 201)); // Verde claro
        UIManager.put("Panel.background", new Color(200, 230, 201)); // Fondo del panel
        UIManager.put("OptionPane.messageForeground", new Color(27, 94, 32)); // Texto verde oscuro
        UIManager.put("OptionPane.messageFont", new Font("Arial", Font.BOLD, 14)); // Fuente del mensaje
        UIManager.put("Button.background", new Color(34, 139, 34)); // Botón verde oscuro
        UIManager.put("Button.foreground", Color.WHITE); // Texto blanco en botones
        UIManager.put("Button.font", new Font("Arial", Font.BOLD, 12)); // Fuente de botones

        // Crear la ventana principal
        JFrame frame = new JFrame("Ejecutar Tarea Remota en Jenkins");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 500);

        // Establecer color de fondo para todo el contenido
        Color verdeClaro = new Color(200, 230, 201); // Verde claro
        frame.getContentPane().setBackground(verdeClaro);

        // Crear borde verde oscuro alrededor del marco
        JPanel borderPanel = new JPanel();
        borderPanel.setBackground(new Color(34, 139, 34)); // Verde oscuro
        borderPanel.setLayout(new BorderLayout());
        frame.setContentPane(borderPanel);

        // Panel interior con diseño y componentes
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(verdeClaro); // Aplicar color verde claro
        borderPanel.add(contentPanel, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Margen entre componentes
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        // Crear un logo
        JLabel logoLabel = new JLabel();
        try {
            ImageIcon logoIcon = new ImageIcon("F:/Desktop/2024-S2/PRUEBAS/JENKINS/Remota_Jenkins/Remota_Jenkins/LOGO.png"); // Cambia esto por la ruta de tu imagen
            Image logoImage = logoIcon.getImage().getScaledInstance(350, 150, Image.SCALE_SMOOTH);
            logoLabel.setIcon(new ImageIcon(logoImage));
        } catch (Exception e) {
            logoLabel.setText("Logo no encontrado");
            logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        }

        // Agregar etiqueta con el texto "GRUPO 4"
        JLabel groupLabel = new JLabel("GRUPO 4");
        groupLabel.setFont(new Font("Arial", Font.BOLD, 20));
        groupLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Crear el botón para ejecutar la tarea
        JButton button = new JButton("Ejecutar Hola Mundo");
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(new Color(34, 139, 34)); // Verde oscuro
        button.setForeground(Color.WHITE); // Texto blanco

        // Acción del botón
        button.addActionListener((ActionEvent e) -> {
            try {
                // Configuración de Jenkins
                String jenkinsUrl = "http://localhost:8090/job/5_LlamadoRemoto/build"; // URL del job de Jenkins
                String user = "dukeherrera73"; // Reemplaza con tu nombre de usuario
                String apiToken = "116e3bd7f2ca367502aa89533eb1c92eea"; // Reemplaza con tu API Token generado en Jenkins

                // Autenticación básica (usuario:token codificado en Base64)
                String auth = user + ":" + apiToken;
                String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());

                // Crear conexión HTTP
                URL url = new URL(jenkinsUrl + "?token=" + apiToken); // Se incluye el token en la URL
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setRequestProperty("Authorization", "Basic " + encodedAuth);

                // Enviar solicitud y procesar respuesta
                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED) {
                    JOptionPane.showMessageDialog(frame, "Tarea ejecutada con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } else if (responseCode == HttpURLConnection.HTTP_FORBIDDEN) {
                    JOptionPane.showMessageDialog(frame, "Error 403: Acceso prohibido. Verifica el usuario y token.", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (responseCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
                    JOptionPane.showMessageDialog(frame, "Error 401: No autorizado. Verifica las credenciales.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(frame, "Error al ejecutar la tarea. Código: " + responseCode, "Error", JOptionPane.ERROR_MESSAGE);
                }
                connection.disconnect();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace(); // Para depuración en la consola
            }
        });

        // Configuración de la posición de los componentes
        gbc.gridx = 0;
        gbc.gridy = 0;
        contentPanel.add(logoLabel, gbc); // Agregar logo

        gbc.gridy = 1;
        contentPanel.add(groupLabel, gbc); // Agregar texto "GRUPO 4"

        gbc.gridy = 2;
        contentPanel.add(button, gbc); // Agregar botón

        // Mostrar ventana
        frame.setVisible(true);
    }
}
