package com.tallermecanico;

import com.formdev.flatlaf.FlatLightLaf;
import com.tallermecanico.vista.MainView;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Punto de entrada de la aplicación Taller Mecánico MVC.
 */
public class Main {
    public static void main(String[] args) {
        // Configurar FlatLaf Look and Feel
        try {
            FlatLightLaf.setup();

            // Configuraciones de UI personalizadas
            UIManager.put("Button.arc", 10);
            UIManager.put("Component.arc", 10);
            UIManager.put("TextComponent.arc", 8);
        } catch (Exception e) {
            System.err.println("Error al configurar FlatLaf: " + e.getMessage());
        }

        // Ejecutar la interfaz de usuario en el Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainView mainView = new MainView();
                mainView.setVisible(true);
            }
        });
    }
}
