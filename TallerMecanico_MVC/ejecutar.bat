@echo off
echo ========================================
echo Iniciando TallerMecanico_MVC...
echo ========================================
echo.

java -cp "target\classes;lib\*" com.tallermecanico.Main

if %ERRORLEVEL% NEQ 0 (
    echo.
    echo ✗ Error al ejecutar.
    echo Asegúrate de haber compilado primero con 'compilar.bat'
    pause
)
