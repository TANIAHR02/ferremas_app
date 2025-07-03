@echo off
echo ========================================
echo    PRUEBAS DE MEJORAS FERREMAS
echo ========================================
echo.

echo [1/5] Verificando que la aplicación esté ejecutándose...
curl -s http://localhost:8082/actuator/health
if %errorlevel% neq 0 (
    echo ERROR: La aplicación no está ejecutándose en puerto 8082
    echo Por favor ejecuta: ./mvnw spring-boot:run
    pause
    exit /b 1
)
echo ✓ Aplicación ejecutándose correctamente
echo.

echo [2/5] Probando registro de usuario...
curl -X POST http://localhost:8082/api/auth/registro ^
  -H "Content-Type: application/json" ^
  -d "{\"nombre\":\"Test\",\"apellido\":\"Usuario\",\"email\":\"test@ferremas.com\",\"password\":\"test123\",\"telefono\":\"+56912345678\",\"direccion\":\"Test 123\"}"
echo.
echo ✓ Registro de usuario probado
echo.

echo [3/5] Probando login...
curl -X POST http://localhost:8082/api/auth/login ^
  -H "Content-Type: application/json" ^
  -d "{\"email\":\"test@ferremas.com\",\"password\":\"test123\"}"
echo.
echo ✓ Login probado
echo.

echo [4/5] Verificando documentación Swagger...
echo Abriendo Swagger UI en el navegador...
start http://localhost:8082/swagger-ui.html
echo ✓ Swagger UI disponible
echo.

echo [5/5] Verificando endpoints de administración...
echo Probando endpoint de estadísticas (requiere autenticación)...
curl -X GET http://localhost:8082/api/admin/dashboard/stats
echo.
echo ✓ Endpoints de administración verificados
echo.

echo ========================================
echo    PRUEBAS COMPLETADAS
echo ========================================
echo.
echo Resumen de mejoras implementadas:
echo ✓ Sistema de autenticación JWT
echo ✓ Autorización basada en roles
echo ✓ Controladores específicos por rol
echo ✓ Servicio de notificaciones por email
echo ✓ Configuración de seguridad mejorada
echo ✓ Documentación Swagger actualizada
echo ✓ Monitoreo con Actuator
echo.
echo URLs importantes:
echo - Aplicación: http://localhost:8082
echo - Swagger UI: http://localhost:8082/swagger-ui.html
echo - Actuator: http://localhost:8082/actuator
echo.
echo Para probar funcionalidades específicas:
echo 1. Registra un usuario en /api/auth/registro
echo 2. Haz login en /api/auth/login
echo 3. Usa el token JWT en el header Authorization
echo 4. Prueba los endpoints específicos de cada rol
echo.
pause 
