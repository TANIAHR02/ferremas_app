@echo off
echo ===============================================
echo PRUEBAS CAMBIO DE DIVISA - FERRERMAS
echo ===============================================
echo.

echo Verificando que la aplicacion este corriendo...
curl -s http://localhost:8082/api/banco-central/tasa-cambio > nul 2>&1
if %errorlevel% neq 0 (
    echo ERROR: La aplicacion no esta corriendo en puerto 8082
    echo Por favor ejecuta: mvn spring-boot:run
    pause
    exit /b 1
)

echo ✅ Aplicacion corriendo correctamente
echo.

echo ===============================================
echo PRUEBA 1: Obtener tasa de cambio del USD
echo ===============================================
curl -s http://localhost:8082/api/banco-central/tasa-cambio
echo.
echo.

echo ===============================================
echo PRUEBA 2: Obtener todas las tasas de cambio
echo ===============================================
curl -s http://localhost:8082/api/banco-central/tasas-cambio
echo.
echo.

echo ===============================================
echo PRUEBA 3: Convertir 100 USD a CLP
echo ===============================================
curl -s http://localhost:8082/api/banco-central/convertir-usd?montoUSD=100
echo.
echo.

echo ===============================================
echo PRUEBA 4: Convertir 50 EUR a CLP
echo ===============================================
curl -s http://localhost:8082/api/banco-central/convertir-eur?montoEUR=50
echo.
echo.

echo ===============================================
echo PRUEBA 5: Obtener tasa especifica del USD
echo ===============================================
curl -s http://localhost:8082/api/banco-central/tasa-cambio/USD
echo.
echo.

echo ===============================================
echo PRUEBAS COMPLETADAS
echo ===============================================
echo.
echo Si todas las pruebas devolvieron datos, el cambio de divisa funciona correctamente.
echo.
echo Para mas pruebas detalladas, usa el archivo: pruebas-cambio-divisa.http
echo.
pause 
