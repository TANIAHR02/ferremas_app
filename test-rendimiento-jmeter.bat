@echo off
echo ========================================
echo   PRUEBAS DE RENDIMIENTO FERREMASAPP
echo ========================================
echo.

echo [1/4] Verificando que la aplicacion este ejecutandose...
curl -s http://localhost:8082/actuator/health > nul
if %errorlevel% neq 0 (
    echo ERROR: La aplicacion no esta ejecutandose en http://localhost:8082
    echo Por favor, ejecuta la aplicacion primero con: mvnw.cmd spring-boot:run
    pause
    exit /b 1
)
echo ✓ Aplicacion ejecutandose correctamente
echo.

echo [2/4] Verificando JMeter...
where jmeter.bat > nul 2>&1
if %errorlevel% neq 0 (
    echo ADVERTENCIA: JMeter no encontrado en el PATH
    echo Por favor, asegurate de que JMeter este instalado y configurado
    echo.
    echo Opciones:
    echo 1. Instalar JMeter desde: https://jmeter.apache.org/download_jmeter.cgi
    echo 2. Agregar JMeter al PATH del sistema
    echo 3. Ejecutar manualmente: jmeter.bat -t ferremasapp-performance-test.jmx
    echo.
    pause
    exit /b 1
)
echo ✓ JMeter encontrado
echo.

echo [3/4] Verificando archivo de prueba...
if not exist "ferremasapp-performance-test.jmx" (
    echo ERROR: Archivo ferremasapp-performance-test.jmx no encontrado
    echo Asegurate de que el archivo este en el directorio actual
    pause
    exit /b 1
)
echo ✓ Archivo de prueba encontrado
echo.

echo [4/4] Iniciando pruebas de rendimiento...
echo.
echo ========================================
echo   CONFIGURACION DE PRUEBAS
echo ========================================
echo • Escenario 1: 10 usuarios navegando productos (5 iteraciones)
echo • Escenario 2: 5 usuarios realizando pagos (3 iteraciones)  
echo • Escenario 3: 15 usuarios consultando APIs (10 iteraciones)
echo • Tiempo total estimado: ~5-10 minutos
echo.
echo ========================================
echo   EJECUTANDO PRUEBAS...
echo ========================================
echo.

REM Crear directorio para resultados si no existe
if not exist "resultados-jmeter" mkdir resultados-jmeter

REM Ejecutar JMeter con configuracion no-GUI para mejor rendimiento
jmeter.bat -n -t ferremasapp-performance-test.jmx -l resultados-jmeter/resultados_%date:~-4,4%%date:~-10,2%%date:~-7,2%_%time:~0,2%%time:~3,2%%time:~6,2%.jtl -e -o resultados-jmeter/reporte_%date:~-4,4%%date:~-10,2%%date:~-7,2%_%time:~0,2%%time:~3,2%%time:~6,2%

if %errorlevel% equ 0 (
    echo.
    echo ========================================
    echo   PRUEBAS COMPLETADAS EXITOSAMENTE
    echo ========================================
    echo.
    echo Resultados guardados en:
    echo • Archivo JTL: resultados-jmeter/
    echo • Reporte HTML: resultados-jmeter/
    echo.
    echo Para ver el reporte HTML, abre el archivo index.html en:
    echo resultados-jmeter/reporte_[fecha]_[hora]/
    echo.
) else (
    echo.
    echo ========================================
    echo   ERROR EN LA EJECUCION
    echo ========================================
    echo.
    echo Revisa los mensajes de error anteriores
    echo Verifica que:
    echo • La aplicacion este ejecutandose
    echo • El archivo JMX sea valido
    echo • JMeter este correctamente instalado
    echo.
)
