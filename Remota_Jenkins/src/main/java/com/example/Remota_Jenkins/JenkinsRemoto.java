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
        frame.setSize(400, 400);
        frame.setLayout(new BorderLayout());

        // Crear un panel para organizar los componentes
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(232, 245, 233)); // Fondo verde claro

        // Agregar un logo
        JLabel logoLabel = new JLabel();
        logoLabel.setHorizontalAlignment(JLabel.CENTER);
        try {
            ImageIcon logoIcon = new ImageIcon("ruta/a/tu/logo.png"); // Cambia esto por la ruta de tu imagen
            Image logoImage = logoIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
            logoLabel.setIcon(new ImageIcon(logoImage));
        } catch (Exception e) {
            logoLabel.setText("Logo no encontrado");
        }

        // Agregar etiqueta con el texto "GRUPO 4"
        JLabel groupLabel = new JLabel("GRUPO 4");
        groupLabel.setFont(new Font("Arial", Font.BOLD, 20));
        groupLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Crear el botón para ejecutar la tarea
        JButton button = new JButton("Ejecutar Hola Mundo");
        button.setFont(new Font("Arial", Font.BOLD, 16));

        // Acción del botón
        button.addActionListener((ActionEvent e) -> {
            try {
                // Configuración de Jenkins
                String jenkinsUrl = "http://localhost:8090/job/LlamadoRemoto/build"; // URL del job de Jenkins
                String user = "dukeherrera73"; // Reemplaza con tu nombre de usuario
                String apiToken = "1xxxxa"; // Reemplaza con tu API Token generado en Jenkins

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

        // Agregar componentes al panel
        panel.add(Box.createRigidArea(new Dimension(0, 20))); // Espaciado
        panel.add(logoLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 10))); // Espaciado
        panel.add(groupLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 20))); // Espaciado
        panel.add(button);

        // Agregar el panel a la ventana principal
        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }
}
