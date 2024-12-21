package com.example.Remota_Jenkins;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

public class JenkinsRemoto {
    public static void main(String[] args) {
        // Crear la ventana principal
        JFrame frame = new JFrame("Ejecutar Tarea Remota en Jenkins");        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 500);
        frame.setLayout(new GridBagLayout()); // Usar GridBagLayout para centrar los componentes

        // Establecer color de fondo
        frame.getContentPane().setBackground(new Color(200, 230, 201)); // Verde claro

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
        JButton button = new JButton("Ejecutar proyecto Maven");
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(new Color(34, 139, 34)); // Verde oscuro
        button.setForeground(Color.WHITE); // Texto blanco

        // Acción del botón
        button.addActionListener((ActionEvent e) -> {
            try {
                // Configuración de Jenkins
                String jenkinsUrl = "http://localhost:8090/job/Inspeccion_y_entrega_continua/build"; // URL del job de Jenkins
                String user = "dukeherrera73"; // Reemplaza con tu nombre de usuario
                String apiToken = "110567806227b477a85ff871eed17c40ff"; // Reemplaza con tu API Token generado en Jenkins

                // Autenticación básica (usuario:token codificado en Base64)
                String auth = user + ":" + apiToken;
                String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());

                // Crear conexión HTTP
                URL url = new URL(jenkinsUrl + "?token=" + apiToken); // Se incluye el token en la URL
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setRequestProperty("Authorization", "Basic " + encodedAuth);
                UIManager UI=new UIManager();

                // Enviar solicitud y procesar respuesta
                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED) {

                    UI.put("Panel.background", new Color(27, 94, 32));
                    UI.put("OptionPane.messageForeground", Color.decode("#b9f6ca"));
                    JOptionPane.showMessageDialog(frame, "Tarea ejecutada con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);

                } else if (responseCode == HttpURLConnection.HTTP_FORBIDDEN) {
                    UI.put("Panel.background", new Color(255, 195, 0));
                    UI.put("OptionPane.messageForeground", Color.decode("#388e3c"));
                    JOptionPane.showMessageDialog(frame, "Error 403: Acceso prohibido. Verifica el usuario y token.", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (responseCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
                    UI.put("Panel.background", new Color(255, 195, 0));
                    UI.put("OptionPane.messageForeground", Color.decode("#388e3c"));
                    JOptionPane.showMessageDialog(frame, "Error 401: No autorizado. Verifica las credenciales.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    UI.put("Panel.background", new Color(255, 195, 0));
                    UI.put("OptionPane.messageForeground", Color.decode("#388e3c"));
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
        frame.add(logoLabel, gbc); // Agregar logo

        gbc.gridy = 1;
        frame.add(groupLabel, gbc); // Agregar texto "GRUPO 4"

        gbc.gridy = 2;
        frame.add(button, gbc); // Agregar botón

        // Mostrar ventana
        frame.setVisible(true);
    }
}

