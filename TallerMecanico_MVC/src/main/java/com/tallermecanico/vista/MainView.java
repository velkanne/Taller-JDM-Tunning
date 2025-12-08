package com.tallermecanico.vista;

import com.tallermecanico.controlador.ControladorTaller;
import com.tallermecanico.modelo.OrdenTrabajo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainView extends JFrame {

    private ControladorTaller controlador;

    private JTextField txtPatente;
    private JTextField txtModelo;
    private JComboBox<String> cmbServicio;
    private JRadioButton rbNormal;
    private JRadioButton rbUrgente;
    private JRadioButton rbCritico;
    private JCheckBox chkClienteEspera;
    private JTextArea txtObservaciones;
    private JTable tablaOrdenes;
    private DefaultTableModel modeloTabla;

    public MainView() {
        controlador = new ControladorTaller();

        setTitle("Taller Mecánico - Gestión de Órdenes");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                controlador.guardarDatos();
                System.exit(0);
            }
        });

        inicializarComponentes();
        actualizarTabla();
    }

    private void inicializarComponentes() {
        setLayout(new BorderLayout(10, 10));

        add(crearMenuBar(), BorderLayout.NORTH);
        add(crearPanelFormulario(), BorderLayout.WEST);
        add(crearPanelTabla(), BorderLayout.CENTER);
    }

    private JMenuBar crearMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu menuArchivo = new JMenu("Archivo");
        JMenuItem itemGuardar = new JMenuItem("Guardar");
        JMenuItem itemSalir = new JMenuItem("Salir");

        itemGuardar.addActionListener(e -> {
            controlador.guardarDatos();
            JOptionPane.showMessageDialog(this, "Datos guardados correctamente");
        });

        itemSalir.addActionListener(e -> {
            controlador.guardarDatos();
            System.exit(0);
        });

        menuArchivo.add(itemGuardar);
        menuArchivo.addSeparator();
        menuArchivo.add(itemSalir);

        JMenu menuAyuda = new JMenu("Ayuda");
        JMenuItem itemAcerca = new JMenuItem("Acerca de");

        itemAcerca.addActionListener(e -> {
            JOptionPane.showMessageDialog(this,
                    "Taller Mecánico MVC\nSistema de Gestión de Órdenes\nVersión 1.0",
                    "Acerca de", JOptionPane.INFORMATION_MESSAGE);
        });

        menuAyuda.add(itemAcerca);

        menuBar.add(menuArchivo);
        menuBar.add(menuAyuda);

        return menuBar;
    }

    private JPanel crearPanelFormulario() {
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelPrincipal.setPreferredSize(new Dimension(350, 0));

        JPanel panelCampos = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        int fila = 0;

        gbc.gridx = 0;
        gbc.gridy = fila;
        gbc.gridwidth = 2;
        JLabel lblTitulo = new JLabel("Nueva Orden de Trabajo");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        panelCampos.add(lblTitulo, gbc);

        fila++;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = fila;
        panelCampos.add(new JLabel("Patente:"), gbc);

        gbc.gridx = 1;
        txtPatente = new JTextField(15);
        panelCampos.add(txtPatente, gbc);

        fila++;
        gbc.gridx = 0;
        gbc.gridy = fila;
        panelCampos.add(new JLabel("Modelo Auto:"), gbc);

        gbc.gridx = 1;
        txtModelo = new JTextField(15);
        panelCampos.add(txtModelo, gbc);

        fila++;
        gbc.gridx = 0;
        gbc.gridy = fila;
        panelCampos.add(new JLabel("Servicio:"), gbc);

        gbc.gridx = 1;
        String[] servicios = { "Mantención", "Frenos", "Aceite", "Motor" };
        cmbServicio = new JComboBox<>(servicios);
        panelCampos.add(cmbServicio, gbc);

        fila++;
        gbc.gridx = 0;
        gbc.gridy = fila;
        gbc.gridwidth = 2;
        panelCampos.add(new JLabel("Urgencia:"), gbc);

        fila++;
        JPanel panelUrgencia = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ButtonGroup grupoUrgencia = new ButtonGroup();

        rbNormal = new JRadioButton("Normal", true);
        rbUrgente = new JRadioButton("Urgente");
        rbCritico = new JRadioButton("Crítico");

        grupoUrgencia.add(rbNormal);
        grupoUrgencia.add(rbUrgente);
        grupoUrgencia.add(rbCritico);

        panelUrgencia.add(rbNormal);
        panelUrgencia.add(rbUrgente);
        panelUrgencia.add(rbCritico);

        gbc.gridy = fila;
        panelCampos.add(panelUrgencia, gbc);

        fila++;
        gbc.gridy = fila;
        chkClienteEspera = new JCheckBox("Cliente Espera");
        panelCampos.add(chkClienteEspera, gbc);

        fila++;
        gbc.gridx = 0;
        gbc.gridy = fila;
        gbc.gridwidth = 2;
        panelCampos.add(new JLabel("Observaciones:"), gbc);

        fila++;
        gbc.gridy = fila;
        txtObservaciones = new JTextArea(5, 20);
        txtObservaciones.setLineWrap(true);
        txtObservaciones.setWrapStyleWord(true);
        JScrollPane scrollObservaciones = new JScrollPane(txtObservaciones);
        panelCampos.add(scrollObservaciones, gbc);

        panelPrincipal.add(panelCampos, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton btnAgregar = new JButton("Agregar Orden");
        JButton btnLimpiar = new JButton("Limpiar");

        btnAgregar.addActionListener(e -> agregarOrden());
        btnLimpiar.addActionListener(e -> limpiarFormulario());

        panelBotones.add(btnAgregar);
        panelBotones.add(btnLimpiar);

        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        return panelPrincipal;
    }

    private JPanel crearPanelTabla() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblTitulo = new JLabel("Órdenes Registradas");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(lblTitulo, BorderLayout.NORTH);

        String[] columnas = { "Patente", "Modelo", "Servicio", "Urgencia", "Espera", "Observaciones" };
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaOrdenes = new JTable(modeloTabla);
        tablaOrdenes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaOrdenes.getTableHeader().setReorderingAllowed(false);

        JScrollPane scrollTabla = new JScrollPane(tablaOrdenes);
        panel.add(scrollTabla, BorderLayout.CENTER);

        JPanel panelBotonesTabla = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnEliminar = new JButton("Eliminar Orden");

        btnEliminar.addActionListener(e -> eliminarOrden());

        panelBotonesTabla.add(btnEliminar);
        panel.add(panelBotonesTabla, BorderLayout.SOUTH);

        return panel;
    }

    private void agregarOrden() {
        String patente = txtPatente.getText();
        String modelo = txtModelo.getText();
        String servicio = (String) cmbServicio.getSelectedItem();
        String urgencia = rbNormal.isSelected() ? "Normal" : rbUrgente.isSelected() ? "Urgente" : "Crítico";
        boolean clienteEspera = chkClienteEspera.isSelected();
        String observaciones = txtObservaciones.getText();

        if (controlador.agregarOrden(patente, modelo, servicio, urgencia, clienteEspera, observaciones)) {
            JOptionPane.showMessageDialog(this, "Orden agregada correctamente");
            limpiarFormulario();
            actualizarTabla();
        }
    }

    private void eliminarOrden() {
        int filaSeleccionada = tablaOrdenes.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una orden para eliminar",
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de eliminar esta orden?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            controlador.eliminarOrden(filaSeleccionada);
            actualizarTabla();
            JOptionPane.showMessageDialog(this, "Orden eliminada correctamente");
        }
    }

    private void limpiarFormulario() {
        txtPatente.setText("");
        txtModelo.setText("");
        cmbServicio.setSelectedIndex(0);
        rbNormal.setSelected(true);
        chkClienteEspera.setSelected(false);
        txtObservaciones.setText("");
        txtPatente.requestFocus();
    }

    private void actualizarTabla() {
        modeloTabla.setRowCount(0);

        for (OrdenTrabajo orden : controlador.obtenerTodasLasOrdenes()) {
            Object[] fila = {
                    orden.getPatente(),
                    orden.getModeloAuto(),
                    orden.getServicio(),
                    orden.getUrgencia(),
                    orden.isClienteEspera() ? "Sí" : "No",
                    orden.getObservaciones()
            };
            modeloTabla.addRow(fila);
        }
    }
}
