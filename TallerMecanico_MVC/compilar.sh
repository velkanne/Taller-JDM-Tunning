#!/bin/bash

echo "========================================"
echo "Compilando TallerMecanico_MVC..."
echo "========================================"

# Crear directorio de salida
mkdir -p target/classes

# Compilar con dependencias
javac -cp "lib/*" -d target/classes -encoding UTF-8 src/main/java/com/tallermecanico/**/*.java

if [ $? -eq 0 ]; then
    echo ""
    echo "✓ Compilación exitosa!"
    echo "Ejecuta './ejecutar.sh' para iniciar la aplicación."
else
    echo ""
    echo "✗ Error en la compilación."
    echo "Verifica que JDK esté instalado."
fi
