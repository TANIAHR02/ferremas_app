@echo off
echo ========================================
echo    PRUEBA DEL CARRITO DE COMPRAS
echo ========================================
echo.

echo 1. Verificando que la aplicación esté ejecutándose...
curl -s http://localhost:8082/actuator/health > nul
if %errorlevel% neq 0 (
    echo ERROR: La aplicación no está ejecutándose en http://localhost:8082
    echo Por favor, ejecuta: ./mvnw spring-boot:run
    pause
    exit /b 1
)
echo ✓ Aplicación ejecutándose correctamente
echo.

echo 2. Probando API del carrito...
echo.

echo 2.1. Creando sesión de carrito...
set SESSION_ID=test_session_%random%
echo Sesión creada: %SESSION_ID%
echo.

echo 2.2. Agregando productos al carrito...
echo Agregando Martillo Profesional...
curl -X POST "http://localhost:8082/api/carrito/%SESSION_ID%/agregar" ^
  -H "Content-Type: application/json" ^
  -d "{\"codigo\":\"MART-001\",\"nombre\":\"Martillo Profesional\",\"precio\":15990,\"imagen\":\"/img/martillo.png\"}"
echo.
echo.

echo Agregando Destornillador Phillips...
curl -X POST "http://localhost:8082/api/carrito/%SESSION_ID%/agregar" ^
  -H "Content-Type: application/json" ^
  -d "{\"codigo\":\"DEST-002\",\"nombre\":\"Destornillador Phillips\",\"precio\":8500,\"imagen\":\"/img/desatornillador.png\"}"
echo.
echo.

echo 2.3. Obteniendo carrito...
curl -X GET "http://localhost:8082/api/carrito/%SESSION_ID%"
echo.
echo.

echo 2.4. Actualizando cantidad del martillo...
curl -X PUT "http://localhost:8082/api/carrito/%SESSION_ID%/actualizar-cantidad?codigo=MART-001&cantidad=2"
echo.
echo.

echo 2.5. Obteniendo resumen del carrito...
curl -X GET "http://localhost:8082/api/carrito/%SESSION_ID%/resumen"
echo.
echo.

echo 2.6. Eliminando destornillador del carrito...
curl -X DELETE "http://localhost:8082/api/carrito/%SESSION_ID%/eliminar?codigo=DEST-002"
echo.
echo.

echo 2.7. Carrito final...
curl -X GET "http://localhost:8082/api/carrito/%SESSION_ID%"
echo.
echo.

echo 2.8. Vaciando carrito...
curl -X DELETE "http://localhost:8082/api/carrito/%SESSION_ID%/vaciar"
echo.
echo.

echo 2.9. Verificando carrito vacío...
curl -X GET "http://localhost:8082/api/carrito/%SESSION_ID%"
echo.
echo.

echo ========================================
echo    PRUEBA COMPLETADA
echo ========================================
echo.
echo Para probar la interfaz web:
echo 1. Abre http://localhost:8082/productos
echo 2. Haz clic en "Agregar al carrito" en cualquier producto
echo 3. Ve a http://localhost:8082/carrito
echo 4. Prueba las funciones de eliminar, actualizar cantidad y vaciar carrito
echo.
pause 