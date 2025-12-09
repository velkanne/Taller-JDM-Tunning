package com.tallermecanico.vista;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

/**
 * Constantes UI para JDM Tuning Shop - Estética inspirada en cultura racing
 * japonesa.
 */
public final class UIConstants {

    private UIConstants() {
    }

    // === PALETA JDM RACING ===
    public static final Color COLOR_ROJO_RACING = new Color(227, 30, 36); // #E31E24
    public static final Color COLOR_NEGRO_CARBONO = new Color(26, 26, 29); // #1A1A1D
    public static final Color COLOR_BLANCO = new Color(255, 255, 255); // #FFFFFF
    public static final Color COLOR_GRIS_METAL = new Color(74, 74, 79); // #4A4A4F
    public static final Color COLOR_NARANJA_TURBO = new Color(255, 107, 53); // #FF6B35
    public static final Color COLOR_AZUL_NITRO = new Color(0, 119, 200); // #0077C8

    // Colores por urgencia (actualizados para tema JDM)
    public static final Color COLOR_NORMAL = new Color(76, 175, 80); // Verde
    public static final Color COLOR_URGENTE = COLOR_NARANJA_TURBO;
    public static final Color COLOR_CRITICO = COLOR_ROJO_RACING;

    // Colores UI
    public static final Color COLOR_FONDO_PANEL = new Color(240, 240, 245);
    public static final Color COLOR_FONDO_OSCURO = COLOR_NEGRO_CARBONO;
    public static final Color COLOR_BORDE = COLOR_GRIS_METAL;
    public static final Color COLOR_HEADER_TABLA = COLOR_ROJO_RACING;
    public static final Color COLOR_TEXTO_HEADER = COLOR_BLANCO;
    public static final Color COLOR_ACENTO = COLOR_AZUL_NITRO;

    // === DIMENSIONES ===
    public static final Dimension DIM_VENTANA = new Dimension(1400, 900);
    public static final Dimension DIM_MINIMA = new Dimension(1200, 700);
    public static final Dimension DIM_CAMPO_TEXTO = new Dimension(200, 32);
    public static final Dimension DIM_AREA_TEXTO = new Dimension(400, 90);
    public static final Dimension DIM_BOTON = new Dimension(140, 36);

    // === FUENTES JDM ===
    public static final Font FONT_TITULO_JDM = new Font("Courier New", Font.BOLD, 24);
    public static final Font FONT_SUBTITULO = new Font("Courier New", Font.BOLD, 18);
    public static final Font FONT_LABEL = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font FONT_BOTON = new Font("Segoe UI", Font.BOLD, 13);
    public static final Font FONT_TABLA = new Font("Consolas", Font.PLAIN, 12);
    public static final Font FONT_MONO = new Font("Consolas", Font.PLAIN, 11);

    // === ICONOS UNICODE JDM ===
    public static final String ICON_AUTO = "🏎️";
    public static final String ICON_PIEZA = "⚙️";
    public static final String ICON_CLIENTE = "👤";
    public static final String ICON_STOCK = "📦";
    public static final String ICON_ORDEN = "📋";
    public static final String ICON_DASHBOARD = "📊";
    public static final String ICON_VELOCIDAD = "💨";
    public static final String ICON_BANDERA = "🏁";
    public static final String ICON_FUEGO = "🔥";
    public static final String ICON_RAYO = "⚡";

    // Botones
    public static final String ICON_AGREGAR = "✚";
    public static final String ICON_MODIFICAR = "✎";
    public static final String ICON_ELIMINAR = "✖";
    public static final String ICON_BUSCAR = "🔍";
    public static final String ICON_EXPORT = "💾";
    public static final String ICON_IMPORT = "📥";

    // === TOOLTIPS JDM ===
    public static final String TOOLTIP_CLIENTE = "Gestionar clientes del taller JDM";
    public static final String TOOLTIP_AUTO = "Catálogo de autos deportivos japoneses";
    public static final String TOOLTIP_PIEZA = "Piezas de tuning y performance";
    public static final String TOOLTIP_STOCK = "Control de inventario con alertas";
    public static final String TOOLTIP_ORDEN = "Órdenes de trabajo activas";
    public static final String TOOLTIP_EXPORT = "Exportar base de datos completa";
    public static final String TOOLTIP_IMPORT = "Importar datos desde archivo JSON";

    // === MENSAJES ===
    public static final String MSG_CONFIRMAR_ELIMINAR = "¿Eliminar este registro?";
    public static final String MSG_TITULO_ELIMINAR = "Confirmar Eliminación";
    public static final String MSG_SELECCIONAR = "Seleccione un elemento de la tabla";
    public static final String MSG_TITULO_ERROR = "Error";
    public static final String MSG_TITULO_INFO = "Información";
    public static final String MSG_TITULO_EXITO = "Éxito";

    // === TEXTOS JDM ===
    public static final String TITULO_APP = "⚡ VELOCITY JDM GARAGE";
    public static final String SUBTITULO_APP = "Japanese Domestic Market Tuning Shop";
    public static final String TAB_DASHBOARD = ICON_DASHBOARD + " Dashboard";
    public static final String TAB_CLIENTES = ICON_CLIENTE + " Clientes";
    public static final String TAB_AUTOS = ICON_AUTO + " JDM Cars";
    public static final String TAB_PIEZAS = ICON_PIEZA + " Parts";
    public static final String TAB_STOCK = ICON_STOCK + " Stock";
    public static final String TAB_ORDENES = ICON_ORDEN + " Work Orders";
}
