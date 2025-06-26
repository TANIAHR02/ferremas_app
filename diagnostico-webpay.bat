@echo off
echo ========================================
echo DIAGNOSTICO SIMULACION WEBPAY
echo ========================================
echo.

echo 1. Verificando si la aplicacion esta ejecutandose...
curl -X GET http://localhost:8081/api/test/health
echo.
echo.

echo 2. Verificando endpoints de simulacion...
curl -X GET http://localhost:8081/api/test/webpay-simulation-status
echo.
echo.

echo 3. Verificando estado actual de simulacion...
curl -X GET http://localhost:8081/api/webpay-simulation/simulate/scenario
echo.
echo.

echo 4. Configurando escenario de EXITO...
curl -X POST http://localhost:8081/api/webpay-simulation/simulate/scenario -H "Content-Type: application/json" -d "{\"scenario\": \"SUCCESS\"}"
echo.
echo.

echo 5. Iniciando pago simulado de prueba...
curl -X POST http://localhost:8081/api/webpay-simulation/simulate/init -H "Content-Type: application/json" -d "{\"pedidoId\": 99999, \"monto\": 10000.0, \"metodoPago\": \"WEBPAY\"}"
echo.
echo.

echo 6. Verificando transacciones almacenadas...
curl -X GET http://localhost:8081/api/webpay-simulation/simulate/transactions
echo.
echo.

echo 7. Verificando panel de control (deberia mostrar HTML)...
curl -X GET http://localhost:8081/api/webpay-simulation/simulate/panel
echo.
echo.

echo ========================================
echo DIAGNOSTICO COMPLETADO
echo ========================================
echo.
echo Si ves respuestas JSON, la simulacion esta funcionando.
echo Si ves errores, revisa los logs de la aplicacion.
echo.
echo Para acceder al panel de control web:
echo http://localhost:8081/api/webpay-simulation/simulate/panel
echo.
pause 