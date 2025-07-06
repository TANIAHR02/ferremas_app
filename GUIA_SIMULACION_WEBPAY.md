# 🧪 Guía de Verificación - Simulación WebPay

## 📋 Descripción General

Esta guía te ayudará a verificar que la simulación de WebPay esté funcionando correctamente en el proyecto Ferremas. La simulación permite probar diferentes escenarios de pago sin conectarse a la API real de Transbank.

## 🚀 Inicio Rápido

### 1. Verificar que la aplicación esté ejecutándose
```bash
# Asegúrate de que la aplicación esté corriendo en el puerto 8082
http://localhost:8082
```

### 2. Ejecutar pruebas automáticas
```bash
# Ejecuta el script de pruebas
test-webpay-simulacion.bat
```

### 3. Acceder al panel de control
```
http://localhost:8082/api/webpay-simulation/simulate/panel
```

## 🔧 Endpoints Disponibles

### Configuración de Escenarios
- `POST /api/webpay-simulation/simulate/scenario` - Configurar escenario
- `GET /api/webpay-simulation/simulate/scenario` - Obtener escenario actual
- `POST /api/webpay-simulation/simulate/mode` - Activar/desactivar modo simulación

### Transacciones
- `POST /api/webpay-simulation/simulate/init` - Iniciar pago simulado
- `GET /api/webpay-simulation/simulate/status/{token}` - Consultar estado
- `GET /api/webpay-simulation/simulate/transactions` - Obtener todas las transacciones
- `DELETE /api/webpay-simulation/simulate/transactions` - Limpiar transacciones

### Interfaz Web
- `GET /api/webpay-simulation/simulate/panel` - Panel de control
- `GET /api/webpay-simulation/simulate/{token}` - Simular proceso de pago

## 🎯 Escenarios de Prueba

### 1. Pago Exitoso (SUCCESS)
```json
{
    "scenario": "SUCCESS"
}
```
**Resultado esperado:**
- Status: AUTHORIZED
- Código de autorización generado
- Últimos 4 dígitos de tarjeta simulados

### 2. Pago Fallido (FAIL)
```json
{
    "scenario": "FAIL"
}
```
**Resultado esperado:**
- Status: FAILED
- Mensaje: "Tarjeta rechazada por el banco"

### 3. Timeout (TIMEOUT)
```json
{
    "scenario": "TIMEOUT"
}
```
**Resultado esperado:**
- Status: TIMEOUT
- Mensaje: "Tiempo de espera agotado"

### 4. Fondos Insuficientes (INSUFFICIENT_FUNDS)
```json
{
    "scenario": "INSUFFICIENT_FUNDS"
}
```
**Resultado esperado:**
- Status: FAILED
- Mensaje: "Fondos insuficientes"

## 📝 Pruebas Manuales

### Usando curl

#### 1. Configurar escenario de éxito
```bash
curl -X POST http://localhost:8082/api/webpay-simulation/simulate/scenario \
  -H "Content-Type: application/json" \
  -d '{"scenario": "SUCCESS"}'
```

#### 2. Iniciar pago simulado
```bash
curl -X POST http://localhost:8082/api/webpay-simulation/simulate/init \
  -H "Content-Type: application/json" \
  -d '{"pedidoId": 12345, "monto": 25000.0, "metodoPago": "WEBPAY"}'
```

#### 3. Consultar estado (reemplazar TOKEN con el token real)
```bash
curl -X GET http://localhost:8082/api/webpay-simulation/simulate/status/TOKEN_AQUI
```

#### 4. Obtener todas las transacciones
```bash
curl -X GET http://localhost:8082/api/webpay-simulation/simulate/transactions
```

### Usando el archivo HTTP
1. Abre `pruebas-webpay-simulacion.http` en tu IDE
2. Ejecuta las pruebas una por una
3. Verifica las respuestas

## 🖥️ Panel de Control Web

### Acceso
```
http://localhost:8082/api/webpay-simulation/simulate/panel
```

### Funcionalidades
- **Selector de Escenarios**: Cambiar entre diferentes escenarios de prueba
- **Formulario de Prueba**: Iniciar transacciones de prueba
- **Tabla de Transacciones**: Ver todas las transacciones simuladas
- **Limpieza de Datos**: Eliminar todas las transacciones

## ✅ Checklist de Verificación

### Funcionalidad Básica
- [ ] La aplicación se ejecuta sin errores
- [ ] Los endpoints responden correctamente
- [ ] El panel de control es accesible
- [ ] Se pueden configurar diferentes escenarios

### Escenarios de Pago
- [ ] Escenario SUCCESS funciona correctamente
- [ ] Escenario FAIL funciona correctamente
- [ ] Escenario TIMEOUT funciona correctamente
- [ ] Escenario INSUFFICIENT_FUNDS funciona correctamente

### Gestión de Transacciones
- [ ] Se pueden iniciar transacciones simuladas
- [ ] Se puede consultar el estado de transacciones
- [ ] Se pueden listar todas las transacciones
- [ ] Se pueden limpiar las transacciones

### Interfaz de Usuario
- [ ] El panel de control muestra información correcta
- [ ] La página de simulación muestra resultados apropiados
- [ ] Los estilos CSS se aplican correctamente
- [ ] La navegación funciona sin problemas

## 🐛 Solución de Problemas

### Error: "Connection refused"
- Verifica que la aplicación esté ejecutándose
- Confirma que el puerto 8082 esté disponible

### Error: "404 Not Found"
- Verifica que los endpoints estén correctamente mapeados
- Confirma que el controlador esté registrado

### Error: "500 Internal Server Error"
- Revisa los logs de la aplicación
- Verifica que todas las dependencias estén configuradas

### Las transacciones no se guardan
- Verifica que el modo simulación esté activado
- Confirma que el servicio esté funcionando correctamente

## 📊 Monitoreo y Logs

### Logs de la Aplicación
Los logs muestran información sobre:
- Transacciones iniciadas
- Escenarios configurados
- Errores de procesamiento

### Métricas de Rendimiento
- Tiempo de respuesta de endpoints
- Número de transacciones procesadas
- Uso de memoria

## 🔄 Flujo de Pruebas Recomendado

1. **Configuración inicial**
   - Verificar que la aplicación esté ejecutándose
   - Acceder al panel de control

2. **Pruebas de escenarios**
   - Probar cada escenario individualmente
   - Verificar las respuestas esperadas

3. **Pruebas de integración**
   - Probar múltiples transacciones
   - Verificar la gestión de estados

4. **Pruebas de interfaz**
   - Navegar por el panel de control
   - Probar la simulación web

5. **Limpieza**
   - Limpiar transacciones de prueba
   - Verificar que no queden datos residuales
