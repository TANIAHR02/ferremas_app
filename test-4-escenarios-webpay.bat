@echo off
echo ========================================
echo PRUEBAS DE SIMULACION WEBPAY - 4 ESCENARIOS
echo ========================================
echo.

echo Verificando que la aplicacion este ejecutandose...
curl -s http://localhost:8081/api/test/health > nul
if %errorlevel% neq 0 (
    echo ERROR: La aplicacion no esta ejecutandose en el puerto 8081
    echo Por favor, ejecuta: mvnw.cmd spring-boot:run
    pause
    exit /b 1
)
echo Aplicacion ejecutandose correctamente.
echo.

echo ========================================
echo 1. OBTENIENDO ESCENARIOS DISPONIBLES
echo ========================================
curl -s http://localhost:8081/api/webpay-simulation/simulate/scenarios
echo.
echo.

echo ========================================
echo 2. PROBANDO LOS 4 ESCENARIOS
echo ========================================

echo [1/4] Probando escenario: PAGO EXITOSO
curl -s -X POST http://localhost:8081/api/webpay-simulation/simulate/scenario -H "Content-Type: application/json" -d "{\"scenario\":\"SUCCESS\"}"
echo.
curl -s -X POST http://localhost:8081/api/webpay-simulation/simulate/init -H "Content-Type: application/json" -d "{\"pedidoId\":1001,\"monto\":25000}"
echo.
echo.

echo [2/4] Probando escenario: PAGO FALLIDO
curl -s -X POST http://localhost:8081/api/webpay-simulation/simulate/scenario -H "Content-Type: application/json" -d "{\"scenario\":\"FAIL\"}"
echo.
curl -s -X POST http://localhost:8081/api/webpay-simulation/simulate/init -H "Content-Type: application/json" -d "{\"pedidoId\":1002,\"monto\":15000}"
echo.
echo.

echo [3/4] Probando escenario: TIMEOUT
curl -s -X POST http://localhost:8081/api/webpay-simulation/simulate/scenario -H "Content-Type: application/json" -d "{\"scenario\":\"TIMEOUT\"}"
echo.
curl -s -X POST http://localhost:8081/api/webpay-simulation/simulate/init -H "Content-Type: application/json" -d "{\"pedidoId\":1003,\"monto\":30000}"
echo.
echo.

echo [4/4] Probando escenario: FONDOS INSUFICIENTES
curl -s -X POST http://localhost:8081/api/webpay-simulation/simulate/scenario -H "Content-Type: application/json" -d "{\"scenario\":\"INSUFFICIENT_FUNDS\"}"
echo.
curl -s -X POST http://localhost:8081/api/webpay-simulation/simulate/init -H "Content-Type: application/json" -d "{\"pedidoId\":1004,\"monto\":50000}"
echo.
echo.

echo ========================================
echo 3. OBTENIENDO RESUMEN DE TRANSACCIONES
echo ========================================
curl -s http://localhost:8081/api/webpay-simulation/simulate/transactions
echo.
echo.

echo ========================================
echo 4. ACCESO A PANELES WEB
echo ========================================
echo Panel de Control: http://localhost:8081/api/webpay-simulation/simulate/panel
echo.
echo Para ver los resultados de cada transaccion, usa los tokens generados arriba
echo en la URL: http://localhost:8081/api/webpay-simulation/simulate/{TOKEN}
echo.

echo ========================================
echo PRUEBAS COMPLETADAS
echo ========================================
echo Se han probado los 4 escenarios de WebPay:
echo - Pago Exitoso
echo - Pago Fallido  
echo - Timeout
echo - Fondos Insuficientes
echo.
echo Revisa los resultados en el panel de control web
echo.
pause 