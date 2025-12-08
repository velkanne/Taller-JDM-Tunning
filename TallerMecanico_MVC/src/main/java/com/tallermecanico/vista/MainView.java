package com.tallermecanico.vista;

import com.tallermecanico.controlador.ControladorTaller;
import com.tallermecanico.modelo.OrdenTrabajo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.Map;

/**
 * Vista principal de la aplicación de Taller Mecánico.
 * Incluye formulario, tabla, búsqueda/filtrado y panel de estadísticas.
 */
public class MainView extends JFrame {

    private ControladorTaller controlador;

    // Componentes del formulario
    private JTextField txtPatente;
    private JTextField txtModelo;
    private JComboBox<String> cmbServicio;
    private JRadioButton rbNormal;
    private JRadioButton rbUrgente;
    private JRadioButton rbCritico;
    private JCheckBox chkClienteEspera;
    private JTextArea txtObservaciones;

    // Componentes de búsqueda/filtrado
    private JTextField txtBusqueda;
    private JComboBox<String> cmbFiltroUrgencia;
    private JComboBox<String> cmbFiltroServicio;

    // Tabla y botones
    private JTable tablaOrdenes;
    private DefaultTableModel modeloTabla;
    private JButton btnAgregar;

    // Панель de estadísticas
    private JLabel lblTotal;
    private JLabel lblNormal;
    private JLabel lblUrgente;
    private JLabel lblCritico;
    private JLabel lblEspera;

    // Estado de edición
    private int ordenEnEdicion = -1; // -1 = modo agregar, >= 0 = modo editar

    public MainView() {
        controlador = new ControladorTaller();

        setTitle("Taller Mecánico - Gestión de Órdenes");
        setSize(1100, 750);
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
        actualizarEstadisticas();
    }

    private void inicializarComponentes() {
        setLayout(new BorderLayout(10, 10));

        add(crearPanelSuperior(), BorderLayout.NORTH);
        add(crearPanelFormulario(), BorderLayout.WEST);
        add(crearPanelTabla(), BorderLayout.CENTER);
        add(crearPanelEstadisticas(), BorderLayout.SOUTH);
    }

    /**
     * Panel superior con menú y búsqueda
     */
    private JPanel crearPanelSuperior() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(crearMenuBar(), BorderLayout.NORTH);
        panel.add(crearPanelBusqueda(), BorderLayout.CENTER);
        return panel;
    }

    private JMenuBar crearMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu menuArchivo = new JMenu("Archivo");
        JMenuItem itemGuardar = new JMenuItem("💾 Guardar");
        JMenuItem itemSalir = new JMenuItem("🚪 Salir");

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
        JMenuItem itemAcerca = new JMenuItem("ℹ️ Acerca de");

        itemAcerca.addActionListener(e -> {
            JOptionPane.showMessageDialog(this,
                    "Taller Mecánico MVC\nSistema de Gestión de Órdenes\nVersión 2.0 (75%)",
                    "Acerca de", JOptionPane.INFORMATION_MESSAGE);
        });

        menuAyuda.add(itemAcerca);

        menuBar.add(menuArchivo);
        menuBar.add(menuAyuda);

        return menuBar;
    }

    /**
     * Panel de búsqueda y filtrado
     */
    private JPanel crearPanelBusqueda() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("🔍 Búsqueda y Filtros"));

        JLabel lblBuscar = new JLabel("Patente:");
        txtBusqueda = new JTextField(12);
        txtBusqueda.setToolTipText("Buscar por patente (búsqueda parcial)");

        JLabel lblUrgencia = new JLabel("Urgencia:");
        String[] opcionesUrgencia = { "Todas", "Normal", "Urgente", "Crítico" };
        cmbFiltroUrgencia = new JComboBox<>(opcionesUrgencia);
        cmbFiltroUrgencia.setToolTipText("Filtrar por nivel de urgencia");

        JLabel lblServicio = new JLabel("Servicio:");
        String[] opcionesServicio = { "Todos", "Mantención", "Frenos", "Aceite", "Motor" };
        cmbFiltroServicio = new JComboBox<>(opcionesServicio);
        cmbFiltroServicio.setToolTipText("Filtrar por tipo de servicio");

        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setToolTipText("Aplicar filtros de búsqueda");
        btnBuscar.addActionListener(e -> aplicarFiltros());

        JButton btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setToolTipText("Quitar todos los filtros");
        btnLimpiar.addActionListener(e -> limpiarFiltros());

        panel.add(lblBuscar);
        panel.add(txtBusqueda);
        panel.add(lblUrgencia);
        panel.add(cmbFiltroUrgencia);
        panel.add(lblServicio);
        panel.add(cmbFiltroServicio);
        panel.add(btnBuscar);
        panel.add(btnLimpiar);

        return panel;
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

        // Título
        gbc.gridx = 0;
        gbc.gridy = fila;
        gbc.gridwidth = 2;
        JLabel lblTitulo = new JLabel("📝 Nueva Orden de Trabajo");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        panelCampos.add(lblTitulo, gbc);

        // Patente
        fila++;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = fila;
        panelCampos.add(new JLabel("Patente:"), gbc);

        gbc.gridx = 1;
        txtPatente = new JTextField(15);
        txtPatente.setToolTipText("Formato: AA-BB-CC o LLLL-NN");
        panelCampos.add(txtPatente, gbc);

        // Modelo
        fila++;
        gbc.gridx = 0;
        gbc.gridy = fila;
        panelCampos.add(new JLabel("Modelo Auto:"), gbc);

        gbc.gridx = 1;
        txtModelo = new JTextField(15);
        panelCampos.add(txtModelo, gbc);

        // Servicio
        fila++;
        gbc.gridx = 0;
        gbc.gridy = fila;
        panelCampos.add(new JLabel("Servicio:"), gbc);

        gbc.gridx = 1;
        String[] servicios = { "Mantención", "Frenos", "Aceite", "Motor" };
        cmbServicio = new JComboBox<>(servicios);
        panelCampos.add(cmbServicio, gbc);

        // Urgencia
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

        // Cliente Espera
        fila++;
        gbc.gridy = fila;
        chkClienteEspera = new JCheckBox("Cliente Espera");
        chkClienteEspera.setToolTipText("Marcar si el cliente está esperando en el taller");
        panelCampos.add(chkClienteEspera, gbc);

        // Observaciones
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

        // Botones del formulario
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        btnAgregar = new JButton("➕ Agregar Orden");
        btnAgregar.setToolTipText("Guardar nueva orden de trabajo");
        JButton btnLimpiar = new JButton("🧹 Limpiar");
        btnLimpiar.setToolTipText("Limpiar formulario");

        btnAgregar.addActionListener(e -> agregarOModificarOrden());
        btnLimpiar.addActionListener(e -> limpiarFormulario());

        panelBotones.add(btnAgregar);
        panelBotones.add(btnLimpiar);

        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        return panelPrincipal;
    }

    private JPanel crearPanelTabla() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblTitulo = new JLabel("📋 Órdenes Registradas");
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

        // Renderer personalizado para colorear por urgencia
        tablaOrdenes.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if (!isSelected) {
                    String urgencia = (String) table.getValueAt(row, 3); // Columna de urgencia
                    switch (urgencia) {
                        case "Crítico":
                            c.setBackground(new Color(255, 200, 200)); // Rojo claro
                            break;
                        case "Urgente":
                            c.setBackground(new Color(255, 235, 180)); // Amarillo claro
                            break;
                        default:
                            c.setBackground(Color.WHITE);
                    }
                }
                return c;
            }
        });

        JScrollPane scrollTabla = new JScrollPane(tablaOrdenes);
        panel.add(scrollTabla, BorderLayout.CENTER);

        // Botones de acción sobre la tabla
        JPanel panelBotonesTabla = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnModificar = new JButton("✏️ Modificar");
        btnModificar.setToolTipText("Modificar orden seleccionada");
        JButton btnEliminar = new JButton("🗑️ Eliminar");
        btnEliminar.setToolTipText("Eliminar orden seleccionada");

        btnModificar.addActionListener(e -> prepararModificacion());
        btnEliminar.addActionListener(e -> eliminarOrden());

        panelBotonesTabla.add(btnModificar);
        panelBotonesTabla.add(btnEliminar);
        panel.add(panelBotonesTabla, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Panel de estadísticas en la parte inferior
     */
    private JPanel crearPanelEstadisticas() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panel.setBorder(BorderFactory.createTitledBorder("📊 Estadísticas"));

        lblTotal = new JLabel("Total: 0");
        lblNormal = new JLabel("Normal: 0");
        lblUrgente = new JLabel("Urgente: 0");
        lblCritico = new JLabel("Crítico: 0");
        lblEspera = new JLabel("En Espera: 0");

        Font fuenteStats = new Font("Arial", Font.BOLD, 12);
        lblTotal.setFont(fuenteStats);
        lblNormal.setFont(fuenteStats);
        lblUrgente.setFont(fuenteStats);
        lblCritico.setFont(fuenteStats);
        lblEspera.setFont(fuenteStats);

        lblCritico.setForeground(new Color(200, 0, 0));
        lblUrgente.setForeground(new Color(200, 150, 0));
        lblNormal.setForeground(new Color(0, 120, 0));

        panel.add(lblTotal);
        panel.add(new JLabel("|"));
        panel.add(lblNormal);
        panel.add(lblUrgente);
        panel.add(lblCritico);
        panel.add(new JLabel("|"));
        panel.add(lblEspera);

        return panel;
    }

    /**
     * Agrega o modifica una orden según el modo actual
     */
    private void agregarOModificarOrden() {
        String patente = txtPatente.getText();
        String modelo = txtModelo.getText();
        String servicio = (String) cmbServicio.getSelectedItem();
        String urgencia = rbNormal.isSelected() ? "Normal" : rbUrgente.isSelected() ? "Urgente" : "Crítico";
        boolean clienteEspera = chkClienteEspera.isSelected();
        String observaciones = txtObservaciones.getText();

        boolean exito;
        if (ordenEnEdicion == -1) {
            // Modo agregar
            exito = controlador.agregarOrden(patente, modelo, servicio, urgencia, clienteEspera, observaciones);
            if (exito) {
                JOptionPane.showMessageDialog(this, "Orden agregada correctamente");
            }
        } else {
            // Modo modificar
            exito = controlador.modificarOrden(ordenEnEdicion, patente, modelo, servicio, urgencia, clienteEspera,
                    observaciones);
            if (exito) {
                JOptionPane.showMessageDialog(this, "Orden modificada correctamente");
                ordenEnEdicion = -1;
                btnAgregar.setText("➕ Agregar Orden");
            }
        }

        if (exito) {
            limpiarFormulario();
            aplicarFiltros(); // Mantener filtros activos
            actualizarEstadisticas();
        }
    }

    /**
     * Prepara el formulario para modificar una orden existente
     */
    private void prepararModificacion() {
        int filaSeleccionada = tablaOrdenes.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una orden para modificar",
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Buscar índice real en la lista completa
        String patenteSeleccionada = (String) tablaOrdenes.getValueAt(filaSeleccionada, 0);
        List<OrdenTrabajo> todasLasOrdenes = controlador.obtenerTodasLasOrdenes();

        for (int i = 0; i < todasLasOrdenes.size(); i++) {
            if (todasLasOrdenes.get(i).getPatente().equals(patenteSeleccionada)) {
                ordenEnEdicion = i;
                OrdenTrabajo orden = todasLasOrdenes.get(i);

                // Cargar datos en formulario
                txtPatente.setText(orden.getPatente());
                txtModelo.setText(orden.getModeloAuto());
                cmbServicio.setSelectedItem(orden.getServicio());

                switch (orden.getUrgencia()) {
                    case "Normal":
                        rbNormal.setSelected(true);
                        break;
                    case "Urgente":
                        rbUrgente.setSelected(true);
                        break;
                    case "Crítico":
                        rbCritico.setSelected(true);
                        break;
                }

                chkClienteEspera.setSelected(orden.isClienteEspera());
                txtObservaciones.setText(orden.getObservaciones());

                btnAgregar.setText("💾 Actualizar Orden");
                txtPatente.requestFocus();
                break;
            }
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
            // Buscar índice real
            String patenteSeleccionada = (String) tablaOrdenes.getValueAt(filaSeleccionada, 0);
            List<OrdenTrabajo> todasLasOrdenes = controlador.obtenerTodasLasOrdenes();

            for (int i = 0; i < todasLasOrdenes.size(); i++) {
                if (todasLasOrdenes.get(i).getPatente().equals(patenteSeleccionada)) {
                    controlador.eliminarOrden(i);
                    break;
                }
            }

            aplicarFiltros();
            actualizarEstadisticas();
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
        ordenEnEdicion = -1;
        btnAgregar.setText("➕ Agregar Orden");
        txtPatente.requestFocus();
    }

    /**
     * Aplica filtros de búsqueda a la tabla
     */
    private void aplicarFiltros() {
        List<OrdenTrabajo> ordenesFiltradas;

        String patenteBusqueda = txtBusqueda.getText().trim();
        String urgenciaFiltro = (String) cmbFiltroUrgencia.getSelectedItem();
        String servicioFiltro = (String) cmbFiltroServicio.getSelectedItem();

        // Búsqueda por patente
        if (!patenteBusqueda.isEmpty()) {
            ordenesFiltradas = controlador.buscarPorPatente(patenteBusqueda);
        } else {
            ordenesFiltradas = controlador.obtenerTodasLasOrdenes();
        }

        // Filtro por urgencia
        if (!urgenciaFiltro.equals("Todas")) {
            ordenesFiltradas = ordenesFiltradas.stream()
                    .filter(o -> o.getUrgencia().equals(urgenciaFiltro))
                    .collect(java.util.stream.Collectors.toList());
        }

        // Filtro por servicio
        if (!servicioFiltro.equals("Todos")) {
            ordenesFiltradas = ordenesFiltradas.stream()
                    .filter(o -> o.getServicio().equals(servicioFiltro))
                    .collect(java.util.stream.Collectors.toList());
        }

        actualizarTablaConLista(ordenesFiltradas);
    }

    private void limpiarFiltros() {
        txtBusqueda.setText("");
        cmbFiltroUrgencia.setSelectedIndex(0);
        cmbFiltroServicio.setSelectedIndex(0);
        actualizarTabla();
    }

    private void actualizarTabla() {
        actualizarTablaConLista(controlador.obtenerTodasLasOrdenes());
    }

    private void actualizarTablaConLista(List<OrdenTrabajo> ordenes) {
        modeloTabla.setRowCount(0);

        for (OrdenTrabajo orden : ordenes) {
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

    private void actualizarEstadisticas() {
        Map<String, Integer> stats = controlador.obtenerEstadisticas();

        lblTotal.setText("Total: " + stats.get("total"));
        lblNormal.setText("Normal: " + stats.get("normal"));
        lblUrgente.setText("Urgente: " + stats.get("urgente"));
        lblCritico.setText("Crítico: " + stats.get("critico"));
        lblEspera.setText("En Espera: " + stats.get("espera"));
    }
}
