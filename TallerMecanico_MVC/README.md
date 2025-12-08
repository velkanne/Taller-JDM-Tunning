# Taller Mecánico MVC - Versión 2.0 (75%)

**Autor:** Tu Nombre

**Descripción:**
Aplicación de escritorio avanzada para la gestión de órdenes de trabajo de un taller mecánico, implementada siguiendo la arquitectura Modelo-Vista-Controlador (MVC). Permite registrar, visualizar, modificar, eliminar y buscar órdenes de trabajo para vehículos. Incluye persistencia de datos en formato JSON, validación avanzada de patentes chilenas, interfaz moderna con FlatLaf y panel de estadísticas en tiempo real.

## ✨ Funcionalidades

### CRUD Completo
- ✅ **Crear** nuevas órdenes de trabajo
- ✅ **Leer** y visualizar todas las órdenes
- ✅ **Modificar** órdenes existentes
- ✅ **Eliminar** órdenes con confirmación

### Búsqueda y Filtrado
- 🔍 Búsqueda parcial por patente
- 🎯 Filtro por nivel de urgencia (Normal/Urgente/Crítico)
- 🔧 Filtro por tipo de servicio (Mantención/Frenos/Aceite/Motor)
- 🔄 Filtros combinables

### Validación Avanzada
- ✓ Patentes en formato chileno: `AA-BB-CC` (antiguo) o `LLLL-NN` (nuevo)
- ✓ Mensajes descriptivos de error
- ✓ Normalización automática a mayúsculas

### UI/UX Moderna
- 🎨 Tema FlatLaf (Look and Feel moderno)
- 🌈 Codificación por colores según urgencia en tabla
- 📊 Panel de estadísticas (contador por urgencia y clientes en espera)
- 💡 Tooltips informativos  
- 🔖 Iconos Unicode intuitivos

### Persistencia
- 💾 Guardado automático en formato JSON
- 📄 Archivo legible: `ordenes.json`
- 🔄 Carga automática al iniciar

## 📦 Dependencias

- **Gson 2.10.1** - Persistencia JSON
- **FlatLaf 3.2.5** - Look and Feel moderno
- **JDK 17+** - Requisito mínimo

Las bibliotecas ya están incluidas en la carpeta `lib/`.

## 🚀 Instrucciones de Ejecución

### Opción 1: Usando scripts (Recomendado)

**Windows:**
```bash
compilar.bat
ejecutar.bat
```

**Linux/Mac:**
```bash
chmod +x compilar.sh ejecutar.sh
./compilar.sh
./ejecutar.sh
```

### Opción 2: Manual con línea de comandos

**Compilar:**
```bash
javac -cp "lib/*" -d target/classes src/main/java/com/tallermecanico/**/*.java
```

**Ejecutar:**
```bash
java -cp "target/classes;lib/*" com.tallermecanico.Main
```

*En Linux/Mac, reemplazar `;` por `:`*

### Opción 3: Usando IDE

1. Abrir proyecto en IntelliJ IDEA, Eclipse o VS Code
2. Agregar JARs de `lib/` al classpath del proyecto
3. Ejecutar `Main.java`

## 📂 Estructura del Proyecto

```
TallerMecanico_MVC/
├── lib/                          # Dependencias externas
│   ├── gson-2.10.1.jar
│   └── flatlaf-3.2.5.jar
├── src/main/java/com/tallermecanico/
│   ├── Main.java                 # Punto de entrada
│   ├── modelo/
│   │   ├── OrdenTrabajo.java     # POJO modelo de datos
│   │   └── GestorOrdenes.java    # Lógica de negocio + persistencia JSON
│   ├── vista/
│   │   └── MainView.java         # Interfaz Swing con FlatLaf
│   ├── controlador/
│   │   └── ControladorTaller.java # Puente entre vista y modelo
│   └── util/
│       └── ValidadorPatente.java # Validación con regex
├── ordenes.json                  # Datos persistentes (generado automáticamente)
├── README.md
├── pom.xml                       # Configuración Maven (opcional)
├── compilar.bat / compilar.sh    # Scripts de compilación
└── ejecutar.bat / ejecutar.sh    # Scripts de ejecución
```

## 🎯 Mejoras Implementadas (Versión 2.0)

### vs Versión 1.0 (~50%)

| Característica | v1.0 | v2.0 |
|---------------|------|------|
| CRUD | Parcial (C, R, D) | ✅ Completo (C, R, U, D) |
| Persistencia | Binaria (.dat) | ✅ JSON (.json) |
| Búsqueda | ❌ No | ✅ Patente + Filtros |
| Validación Patente | Básica (longitud) | ✅ Regex formatos chilenos |
| UI Theme | Swing default | ✅ FlatLaf moderno |
| Estadísticas | ❌ No | ✅ Panel completo |
| Colores Tabla | ❌ No | ✅ Por urgencia |
| Tooltips | ❌ No | ✅ Sí |

## 🔧 Notas Técnicas

- **Formato de datos**: Si tienes datos en `ordenes.dat` de la versión anterior, no se migrarán automáticamente. Realizar backup si es necesario.
- **Compilación**: Asegurarse de que el JDK esté en la variable PATH del sistema.
- **Compatibilidad**: Probado en Windows 10/11, también funciona en Linux y MacOS.

## 📝 Licencia

Proyecto educativo - Libre uso académico.

