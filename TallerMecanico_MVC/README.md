# ⚡ Velocity JDM Garage - Versión 3.0 (95%)

**Japanese Domestic Market Tuning Shop Management System**

**Descripción:**
Sistema avanzado de gestión para taller especializado en tuning de autos deportivos japoneses. Arquitectura MVC con base de datos JSON extendida, estética inspirada en cultura JDM racing, catálogo pre-cargado de autos icónicos (Skyline GT-R, Supra, RX-7), gestión integral de clientes, inventario de piezas de alta performance, y sistema completo de import/export.

## 🏁 Características JDM

### Gestión Completa
- 🚗 **Autos Deportivos Japoneses**: Catálogo de JDM legends (Nissan, Toyota, Mazda, Honda, Subaru, Mitsubishi)
- ⚙️ **Piezas Tuning Premium**: HKS, Greddy, Tomei, Nismo, Tein, Brembo
- 👤 **Clientes**: Sistema de tipos (Regular, VIP, Corporativo)
- 📦 **Control Stock**: Alertas de bajo stock automáticas
- 📋 **Órdenes Trabajo**: Tracking completo con relaciones

### Base de Datos JSON
- `clientes.json` - Registro de clientes
- `autos.json` - Catálogo de autos JDM
- `piezas.json` - Inventario de piezas tuning
- `stock.json` - Control de inventario
- `ordenes.json` - Órdenes de trabajo
- 💾 **Import/Export** unificado para backup completo

### Estética JDM Racing
- 🎨 Paleta: Rojo Racing (#E31E24), Negro Carbono, Naranja Turbo, Azul Nitro
- ✨ Fuentes monoespaciadas estilo técnico
- 🏎️ Iconos temáticos Unicode (autos, piezas, velocidad)
- 🔥 Tema FlatLaf customizado

## 📦 Dependencias

- **Gson 2.10.1** - Persistencia JSON
- **FlatLaf 3.2.5** - Look and Feel
- **SLF4J 2.0.9** - Logging API
- **Logback 1.4.11** - Logging implementation
- **JUnit 5.10.1** - Testing framework
- **JDK 17+** - Requisito mínimo

## 🚀 Instrucciones de Ejecución

### Con Maven (Recomendado)

```bash
# Compilar proyecto
mvn clean compile

# Ejecutar tests
mvn test

# Ejecutar aplicación
mvn exec:java
```

### Scripts Alternativos

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

## 📂 Estructura del Proyecto

```
TallerMecanico_MVC/
├── src/
│   ├── main/java/com/tallermecanico/
│   │   ├── Main.java
│   │   ├── modelo/
│   │   │   ├── OrdenTrabajo.java
│   │   │   ├── Cliente.java ⭐
│   │   │   ├── AutoDeportivo.java ⭐
│   │   │   ├── Pieza.java ⭐
│   │   │   ├── StockPieza.java ⭐
│   │   │   ├── GestorOrdenes.java
│   │   │   ├── GestorClientes.java ⭐
│   │   │   ├── GestorAutos.java ⭐
│   │   │   ├── GestorPiezas.java ⭐
│   │   │   ├── GestorStock.java ⭐
│   │   │   └── LocalDateAdapter.java ⭐
│   │   ├── vista/
│   │   │   ├── MainView.java
│   │   │   └── UIConstants.java (JDM Theme) ⭐
│   │   ├── controlador/
│   │   │   └── ControladorTaller.java
│   │   ├── util/
│   │   │   ├── ValidadorPatente.java
│   │   │   └── ImportExportManager.java ⭐
│   │   └── exception/
│   │       └── TallerException.java
│   ├── main/resources/
│   │   └── logback.xml
│   └── test/java/... (41 tests unitarios)
├── data/ (generado automáticamente)
│   ├── clientes.json
│   ├── autos.json
│   ├── piezas.json
│   ├── stock.json
│   └── ordenes.json
├── logs/
│   └── taller.log
└── pom.xml

⭐ = Nuevo en v3.0 JDM Edition
```

## 🎯 Comparativa de Versiones

| Característica | v2.5 (85%) | v3.0 (95%) |
|---|---|---|
| **Base de Datos** | 1 modelo (OrdenTrabajo) | 5 modelos ✅ |
| **Clientes** | ❌ No | Cliente (VIP/Regular/Corp) ✅ |
| **Autos** | Solo patente | AutoDeportivo JDM ✅ |
| **Piezas** | ❌ No | Catálogo 6 categorías ✅ |
| **Stock** | ❌ No | Control + Alertas ✅ |
| **Import/Export** | ❌ No | Backup unificado ✅ |
| **Catálogo** | ❌ No | 6 JDM cars + piezas ✅ |
| **Estética** | Genérica | JDM Racing Theme ✅ |
| **Tests** | 41 tests | 41 tests ✅ |
| **Logging** | SLF4J | SLF4J ✅ |

## 🚗 Catálogo Pre-cargado

### Autos JDM Icónicos
- **Nissan Skyline GT-R R34** (RB26DETT, 280HP)
- **Toyota Supra A80** (2JZ-GTE, 320HP)
- **Mazda RX-7 FD** (13B-REW, 255HP)
- **Honda NSX NA1** (C30A, 270HP)
- **Subaru WRX STI** (EJ257, 300HP)
- **Mitsubishi Lancer Evolution IX** (4G63T, 286HP)

### Piezas Tuning Premium
- **Turbos**: HKS GT3037
- **Escapes**: Greddy Titanium
- **Suspensión**: Tein Flex Z
- **Frenos**: Brembo 6 Pistones
- **Motor**: CP Pistons Forjados
- **Estética**: Nismo GT-Wing

## 💾 Import/Export

```java
// Exportar base de datos completa
ImportExportManager manager = new ImportExportManager();
manager.exportarTodo("backup_jdm.json", 
    gestorClientes, gestorAutos, gestorPiezas, gestorStock, gestorOrdenes);

// Importar (leer y validar)
Map<String, Object> datos = manager.leerExportacion("backup_jdm.json");
if (manager.validarExportacion(datos)) {
    // Procesar importación
}
```

## 🧪 Testing

```bash
# Ejecutar todos los tests
mvn test

# Tests específicos
mvn test -Dtest=GestorClientesTest
mvn test -Dtest=AutoDeportivoTest
```

**Cobertura:** 41 tests unitarios + nuevos  tests modelos JDM

## 🔧 Notas Técnicas

- **Catálogos**: Se cargan automáticamente al primer inicio si no existen datos
- **UUIDs**: Todos los modelos usan IDs únicos generados automáticamente
-**Relaciones**: Cliente-Auto, Auto-Orden, Pieza-Stock mantenidas por IDs
- **Stock**: Alertas cuando cantidad <= mínimo configurado
- **Logs**: Todas las operaciones CRUD registradas con SLF4J

## 🎨 Paleta de Colores JDM

```java
Rojo Racing:     #E31E24 (Principal)
Negro Carbono:   #1A1A1D (Fondos)
Naranja Turbo:   #FF6B35 (Urgente/Acento)
Azul Nitro:      #0077C8 (Detalles)
Gris Metal:      #4A4A4F (Bordes)
```

## 📝 Licencia

Proyecto educativo - Libre uso académico.

---

**v3.0 - JDM Edition** | *Born from the streets of Tokyo* 🏁⚡

**Autor:** Tu Nombre

**Descripción:**
Aplicación de escritorio avanzada para la gestión de órdenes de trabajo de un taller mecánico, implementada siguiendo la arquitectura Modelo-Vista-Controlador (MVC). Permite registrar, visualizar, modificar, eliminar y buscar órdenes de trabajo para vehículos. Incluye persistencia de datos en formato JSON, validación avanzada de patentes chilenas, interfaz moderna con FlatLaf, panel de estadísticas en tiempo real, **sistema de logging profesional con SLF4J/Logback** y **suite completa de tests unitarios con JUnit 5**.

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
- 🎯 Constantes UI centralizadas

### Persistencia
- 💾 Guardado automático en formato JSON
- 📄 Archivo legible: `ordenes.json`
- 🔄 Carga automática al iniciar

### **Logging Profesional (NUEVO)**
- 📝 Logging estructurado con SLF4J + Logback
- 🎨 Logs colorizados en consola
- 📁 Rotación diaria de archivos de log
- 🔍 Niveles: DEBUG, INFO, WARN, ERROR

### **Testing Completo (NUEVO)**
- ✅ Suite de tests unitarios con JUnit 5
- ✅ Cobertura: ValidadorPatente, OrdenTrabajo, GestorOrdenes, ControladorTaller
- ✅ Tests parametrizados y casos edge
- ✅ >60% cobertura de código

## 📦 Dependencias

- **Gson 2.10.1** - Persistencia JSON
- **FlatLaf 3.2.5** - Look and Feel moderno
- **SLF4J 2.0.9** - API de logging
- **Logback 1.4.11** - Implementación logging
- **JUnit 5.10.1** - Framework de testing
- **JDK 17+** - Requisito mínimo

## 🚀 Instrucciones de Ejecución

### Opción 1: Usando Maven (Recomendado)

```bash
# Compilar y ejecutar tests
mvn clean test

# Compilar proyecto
mvn clean compile

# Ejecutar aplicación
mvn exec:java
```

### Opción 2: Usando scripts

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

### Opción 3: Manual con línea de comandos

**Compilar:**
```bash
javac -cp "lib/*" -d target/classes src/main/java/com/tallermecanico/**/*.java
```

**Ejecutar:**
```bash
java -cp "target/classes;lib/*" com.tallermecanico.Main
```

*En Linux/Mac, reemplazar `;` por `:`*

### Opción 4: Usando IDE

1. Abrir proyecto en IntelliJ IDEA, Eclipse o VS Code
2. Maven descargará automáticamente las dependencias
3. Ejecutar `Main.java`

## 🧪 Ejecutar Tests

```bash
# Con Maven
mvn test

# Con Maven (verbose)
mvn test -X

# Test específico
mvn test -Dtest=ValidadorPatenteTest
```

## 📂 Estructura del Proyecto

```
TallerMecanico_MVC/
├── lib/                          # Dependencias externas (legacy)
│   ├── gson-2.10.1.jar
│   └── flatlaf-3.2.5.jar
├── src/
│   ├── main/java/com/tallermecanico/
│   │   ├── Main.java                 # Punto de entrada
│   │   ├── modelo/
│   │   │   ├── OrdenTrabajo.java     # POJO modelo de datos
│   │   │   └── GestorOrdenes.java    # Lógica de negocio + persistencia JSON
│   │   ├── vista/
│   │   │   ├── MainView.java         # Interfaz Swing con FlatLaf
│   │   │   └── UIConstants.java      # Constantes UI centralizadas
│   │   ├── controlador/
│   │   │   └── ControladorTaller.java # Puente entre vista y modelo
│   │   ├── util/
│   │   │   └── ValidadorPatente.java # Validación con regex
│   │   └── exception/
│   │       └── TallerException.java  # Excepción personalizada
│   ├── main/resources/
│   │   └── logback.xml               # Configuración logging
│   └── test/java/com/tallermecanico/
│       ├── util/
│       │   └── ValidadorPatenteTest.java
│       ├── modelo/
│       │   ├── OrdenTrabajoTest.java
│       │   └── GestorOrdenesTest.java
│       └── controlador/
│           └── ControladorTallerTest.java
├── logs/                         # Logs de aplicación (generado)
│   └── taller.log
├── ordenes.json                  # Datos persistentes (generado)
├── README.md
├── pom.xml                       # Configuración Maven
├── compilar.bat / compilar.sh    # Scripts de compilación
└── ejecutar.bat / ejecutar.sh    # Scripts de ejecución
```

## 🎯 Mejoras Implementadas (Versión 2.5)

### vs Versión 2.0 (75%)

| Característica | v2.0 | v2.5 |\n|---------------|------|------|\n| CRUD | ✅ Completo | ✅ Completo |\n| Persistencia | ✅ JSON | ✅ JSON |\n| Búsqueda/Filtros | ✅ Sí | ✅ Sí |\n| Validación Patente | ✅ Regex completo | ✅ Regex completo |\n| UI Theme | ✅ FlatLaf | ✅ FlatLaf + Constantes |\n| Estadísticas | ✅ Panel completo | ✅ Panel completo |\n| **Logging** | ❌ System.out/err | ✅ SLF4J + Logback |\n| **Tests Unitarios** | ❌ No | ✅ JUnit 5 (4 clases) |\n| **Manejo Excepciones** | ❌ Básico | ✅ TallerException |\n| **Constantes UI** | ❌ Hardcoded | ✅ Centralizadas |\n| **Cobertura Tests** | 0% | >60% |\n\n## 🔧 Notas Técnicas

- **Formato de datos**: Si tienes datos en `ordenes.dat` de la versión anterior, no se migrarán automáticamente. Realizar backup si es necesario.
- **Logs**: Los archivos de log se guardan en carpeta `logs/` con rotación diaria.
- **Tests**: Ejecutar `mvn test` antes de cada commit para asegurar calidad.
- **Compilación**: Maven descargará automáticamente las dependencias de SLF4J y Logback.
- **Compatibilidad**: Probado en Windows 10/11, también funciona en Linux y MacOS.

## 📊 Cobertura de Tests

| Componente | Tests | Cobertura Estimada |
|------------|-------|-------------------|
| ValidadorPatente | 9 tests | ~90% |
| OrdenTrabajo | 7 tests | ~85% |
| GestorOrdenes | 14 tests | ~70% |
| ControladorTaller | 11 tests | ~65% |

## 📝 Licencia

Proyecto educativo - Libre uso académico.
