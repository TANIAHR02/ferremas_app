@echo off
echo ========================================
echo PRUEBAS DE ACTUALIZACION DE PRECIOS
echo ========================================
echo.

echo Verificando que la aplicacion este ejecutandose...
curl -s http://localhost:8081/api/productos >nul 2>&1
if %errorlevel% neq 0 (
    echo ERROR: La aplicacion no esta ejecutandose en http://localhost:8081
    echo Por favor, inicia la aplicacion primero.
    pause
    exit /b 1
)
echo Aplicacion ejecutandose correctamente.
echo.

echo ========================================
echo PRUEBA 1: Actualizar precio por ID
echo ========================================
curl -X PATCH http://localhost:8081/api/productos/1/precio ^
  -H "Content-Type: application/json" ^
  -d "{\"nuevoPrecio\": 18990.00, \"motivo\": \"Ajuste de precios por inflacion\", \"usuario\": \"admin@ferremas.cl\"}"
echo.
echo.

echo ========================================
echo PRUEBA 2: Actualizar precio por codigo
echo ========================================
curl -X PATCH http://localhost:8081/api/productos/codigo/MART001/precio ^
  -H "Content-Type: application/json" ^
  -d "{\"nuevoPrecio\": 15990.00, \"motivo\": \"Promocion especial\", \"usuario\": \"gerente@ferremas.cl\"}"
echo.
echo.

echo ========================================
echo PRUEBA 3: Precio invalido (debe fallar)
echo ========================================
curl -X PATCH http://localhost:8081/api/productos/1/precio ^
  -H "Content-Type: application/json" ^
  -d "{\"nuevoPrecio\": -100.00, \"motivo\": \"Precio negativo invalido\", \"usuario\": \"test@ferremas.cl\"}"
echo.
echo.

echo ========================================
echo PRUEBA 4: Producto inexistente (debe fallar)
echo ========================================
curl -X PATCH http://localhost:8081/api/productos/999/precio ^
  -H "Content-Type: application/json" ^
  -d "{\"nuevoPrecio\": 25000.00, \"motivo\": \"Producto que no existe\", \"usuario\": \"test@ferremas.cl\"}"
echo.
echo.

echo ========================================
echo PRUEBA 5: Obtener historial de precios
echo ========================================
curl -X GET http://localhost:8081/api/productos/1/historial-precios
echo.
echo.

echo ========================================
echo PRUEBA 6: Actualizar precio con decimales
echo ========================================
curl -X PATCH http://localhost:8081/api/productos/3/precio ^
  -H "Content-Type: application/json" ^
  -d "{\"nuevoPrecio\": 89990.50, \"motivo\": \"Ajuste de precios con decimales\", \"usuario\": \"finanzas@ferremas.cl\"}"
echo.
echo.

echo ========================================
echo PRUEBAS COMPLETADAS
echo ========================================
echo.
echo Revisa los resultados arriba para verificar que:
echo - Las actualizaciones exitosas devuelven status 200
echo - Los errores devuelven status 400 o 404 segun corresponda
echo - Los historiales de precios se muestran correctamente
echo.
pause 