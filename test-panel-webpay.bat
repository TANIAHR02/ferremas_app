@echo off
echo ========================================
echo PRUEBA PANEL DE CONTROL WEBPAY
echo ========================================
echo.

echo Esperando que la aplicacion inicie...
timeout /t 30 /nobreak > nul

echo.
echo ========================================
echo VERIFICANDO ESTADO DE LA APLICACION
echo ========================================

echo 1. Verificando si la aplicacion responde...
curl -s http://localhost:8082/actuator/health
echo.
echo.

echo 2. Verificando estado de simulacion WebPay...
curl -s http://localhost:8082/api/webpay-simulation/simulate/scenario
echo.
echo.

echo 3. Configurando escenario de EXITO...
curl -s -X POST http://localhost:8082/api/webpay-simulation/simulate/scenario -H "Content-Type: application/json" -d "{\"scenario\": \"SUCCESS\"}"
echo.
echo.

echo 4. Generando token de prueba...
curl -s -X POST http://localhost:8082/api/webpay-simulation/simulate/init -H "Content-Type: application/json" -d "{\"pedidoId\": 99999, \"monto\": 15000.0, \"metodoPago\": \"WEBPAY\"}"
echo.
echo.

echo ========================================
echo URLS PARA ACCEDER
echo ========================================
echo.
echo Panel de Control WebPay:
echo http://localhost:8082/api/webpay-simulation/simulate/panel
echo.
echo Documentacion API:
echo http://localhost:8082/swagger-ui.html
echo.
echo Aplicacion Principal:
echo http://localhost:8082
echo.

echo ========================================
echo INSTRUCCIONES
echo ========================================
echo.
echo 1. Abre el panel de control en tu navegador
echo 2. Selecciona un escenario (SUCCESS, FAIL, TIMEOUT, INSUFFICIENT_FUNDS)
echo 3. Haz clic en "Aplicar Escenario"
echo 4. Ingresa un monto y ID de pedido
echo 5. Haz clic en "Iniciar Prueba de Pago"
echo 6. Verifica que se genere el token y aparezca en la tabla
echo.

pause 