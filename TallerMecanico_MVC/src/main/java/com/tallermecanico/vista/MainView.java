package com.tallermecanico.vista;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {

    public MainView() {
        setTitle("Taller Mecánico - Gestión de Órdenes");
        setSize(800, 600); // Tamaño inicial de la ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrar la ventana en la pantalla
        
        // Aquí se añadirán los componentes de la interfaz
        // Por ahora, solo un JLabel simple para verificar que funciona
        add(new JLabel("Bienvenido al Taller Mecánico", SwingConstants.CENTER));
    }
}
