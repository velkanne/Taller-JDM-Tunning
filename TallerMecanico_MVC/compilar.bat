@echo off
echo ========================================
echo Compilando TallerMecanico_MVC (Version 2.5)...
echo ========================================

REM Verificar si existe Maven
where mvn >nul 2>nul
if %ERRORLEVEL% == 0 (
    echo Usando Maven para compilar...
    mvn clean compile
    if %ERRORLEVEL% == 0 (
        echo.
        echo + Compilacion exitosa con Maven
        goto :end
    )
)

REM Fallback: Compilacion manual sin dependencias Maven
echo Maven no detectado. Compilando manualmente...
echo ADVERTENCIA: Se omitiran caracteristicas de logging SLF4J

REM Crear directorios
if not exist "target\classes" mkdir target\classes

REM Compilar codigo fuente (solo lib JARs incluidos)
javac -encoding UTF-8 -cp "lib\*" -d target\classes src\main\java\com\tallermecanico\**\*.java

if %ERRORLEVEL% == 0 (
    echo.
    echo + Compilacion exitosa
    echo.
    echo NOTA: Para funcionalidad completa (logging), instalar Maven y ejecutar:
    echo   mvn clean compile
) else (
    echo.
    echo X Error en la compilacion.
    echo Verifica que JDK este instalado y en PATH.
    pause
    exit /b 1
)

:end
pause
