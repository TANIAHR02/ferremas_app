@echo off
echo ========================================
echo PRUEBA AUTOMATICA SIMULACION WEBPAY
echo ========================================
echo.

echo 1. Verificando estado de simulacion...
curl -X GET http://localhost:8081/api/webpay-simulation/simulate/scenario
echo.
echo.

echo 2. Iniciando pago simulado (esto genera un token automaticamente)...
for /f "tokens=*" %%i in ('curl -s -X POST http://localhost:8081/api/webpay-simulation/simulate/init -H "Content-Type: application/json" -d "{\"pedidoId\": 99999, \"monto\": 15000.0, \"metodoPago\": \"WEBPAY\"}"') do set RESPONSE=%%i
echo Respuesta: %RESPONSE%
echo.
echo.

echo 3. Extrayendo token de la respuesta...
for /f "tokens=2 delims=:," %%a in ('echo %RESPONSE% ^| findstr "token"') do set TOKEN=%%a
set TOKEN=%TOKEN:"=%
set TOKEN=%TOKEN: =%
echo Token extraido: %TOKEN%
echo.
echo.

echo 4. Consultando estado con el token generado...
curl -X GET http://localhost:8081/api/webpay-simulation/simulate/status/%TOKEN%
echo.
echo.

echo 5. Verificando todas las transacciones...
curl -X GET http://localhost:8081/api/webpay-simulation/simulate/transactions
echo.
echo.

echo ========================================
echo PRUEBA COMPLETADA
echo ========================================
echo.
echo Para ver la simulacion web, abre en tu navegador:
echo http://localhost:8081/api/webpay-simulation/simulate/%TOKEN%
echo.
echo Para ver el panel de control:
echo http://localhost:8081/api/webpay-simulation/simulate/panel
echo.
pause 