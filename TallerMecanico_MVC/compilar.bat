@echo off
echo ========================================
echo Compilando TallerMecanico_MVC...
echo ========================================

REM Crear directorio de salida
if not exist "target\classes" mkdir "target\classes"

REM Compilar con dependencias (listando archivos explícitamente)
javac -cp "lib\*" -d target\classes -encoding UTF-8 ^
    src\main\java\com\tallermecanico\Main.java ^
    src\main\java\com\tallermecanico\modelo\OrdenTrabajo.java ^
    src\main\java\com\tallermecanico\modelo\GestorOrdenes.java ^
    src\main\java\com\tallermecanico\controlador\ControladorTaller.java ^
    src\main\java\com\tallermecanico\vista\MainView.java ^
    src\main\java\com\tallermecanico\util\ValidadorPatente.java

if %ERRORLEVEL% == 0 (
    echo.
    echo + Compilacion exitosa!
    echo Ejecuta 'ejecutar.bat' para iniciar la aplicacion.
) else (
    echo.
    echo X Error en la compilacion.
    echo Verifica que JDK este instalado y en PATH.
)

pause

