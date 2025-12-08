#!/bin/bash

echo "========================================"
echo "Iniciando TallerMecanico_MVC..."
echo "========================================"
echo ""

java -cp "target/classes:lib/*" com.tallermecanico.Main

if [ $? -ne 0 ]; then
    echo ""
    echo "✗ Error al ejecutar."
    echo "Asegúrate de haber compilado primero con './compilar.sh'"
fi
