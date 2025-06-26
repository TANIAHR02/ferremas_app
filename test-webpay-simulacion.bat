@echo off
echo ========================================
echo PRUEBAS SIMULACION WEBPAY - FERREMAS
echo ========================================
echo.

echo 1. Verificando estado actual de simulacion...
curl -X GET http://localhost:8081/api/webpay-simulation/simulate/scenario
echo.
echo.

echo 2. Configurando escenario de EXITO...
curl -X POST http://localhost:8081/api/webpay-simulation/simulate/scenario ^
  -H "Content-Type: application/json" ^
  -d "{\"scenario\": \"SUCCESS\"}"
echo.
echo.

echo 3. Iniciando pago simulado exitoso...
curl -X POST http://localhost:8081/api/webpay-simulation/simulate/init ^
  -H "Content-Type: application/json" ^
  -d "{\"pedidoId\": 12345, \"monto\": 25000.0, \"metodoPago\": \"WEBPAY\"}"
echo.
echo.

echo 4. Configurando escenario de FALLO...
curl -X POST http://localhost:8081/api/webpay-simulation/simulate/scenario ^
  -H "Content-Type: application/json" ^
  -d "{\"scenario\": \"FAIL\"}"
echo.
echo.

echo 5. Iniciando pago simulado fallido...
curl -X POST http://localhost:8081/api/webpay-simulation/simulate/init ^
  -H "Content-Type: application/json" ^
  -d "{\"pedidoId\": 12346, \"monto\": 15000.0, \"metodoPago\": \"WEBPAY\"}"
echo.
echo.

echo 6. Configurando escenario de TIMEOUT...
curl -X POST http://localhost:8081/api/webpay-simulation/simulate/scenario ^
  -H "Content-Type: application/json" ^
  -d "{\"scenario\": \"TIMEOUT\"}"
echo.
echo.

echo 7. Iniciando pago simulado con timeout...
curl -X POST http://localhost:8081/api/webpay-simulation/simulate/init ^
  -H "Content-Type: application/json" ^
  -d "{\"pedidoId\": 12347, \"monto\": 50000.0, \"metodoPago\": \"WEBPAY\"}"
echo.
echo.

echo 8. Configurando escenario de FONDOS INSUFICIENTES...
curl -X POST http://localhost:8081/api/webpay-simulation/simulate/scenario ^
  -H "Content-Type: application/json" ^
  -d "{\"scenario\": \"INSUFFICIENT_FUNDS\"}"
echo.
echo.

echo 9. Iniciando pago simulado con fondos insuficientes...
curl -X POST http://localhost:8081/api/webpay-simulation/simulate/init ^
  -H "Content-Type: application/json" ^
  -d "{\"pedidoId\": 12348, \"monto\": 100000.0, \"metodoPago\": \"WEBPAY\"}"
echo.
echo.

echo 10. Obteniendo todas las transacciones simuladas...
curl -X GET http://localhost:8081/api/webpay-simulation/simulate/transactions
echo.
echo.

echo ========================================
echo PRUEBAS COMPLETADAS
echo ========================================
echo.
echo Para ver el panel de control, abre en tu navegador:
echo http://localhost:8081/api/webpay-simulation/simulate/panel
echo.
echo Para limpiar las transacciones:
echo curl -X DELETE http://localhost:8081/api/webpay-simulation/simulate/transactions
echo.
pause 