# 🔧 Solución de Problemas - Simulación WebPay

## 🚨 Problema: No se pueden simular pagos / El historial no muestra nada

### 🔍 Diagnóstico Paso a Paso

#### 1. Verificar que la aplicación esté ejecutándose
```bash
# Verificar si hay un proceso en el puerto 8081
netstat -ano | findstr :8081
```

**Resultado esperado:** Debe mostrar un proceso escuchando en el puerto 8081.

#### 2. Verificar que la aplicación responda
```bash
# Probar endpoint básico
curl -X GET http://localhost:8081/api/test/health
```

**Resultado esperado:**
```json
{
  "status": "OK",
  "message": "Aplicación funcionando correctamente",
  "timestamp": 1234567890
}
```

#### 3. Verificar endpoints de simulación
```bash
# Probar endpoint de estado de simulación
curl -X GET http://localhost:8081/api/webpay-simulation/simulate/scenario
```

**Resultado esperado:**
```json
{
  "scenario": "SUCCESS",
  "simulationMode": true
}
```

#### 4. Probar iniciar transacción
```bash
# Iniciar pago simulado
curl -X POST http://localhost:8081/api/webpay-simulation/simulate/init \
  -H "Content-Type: application/json" \
  -d "{\"pedidoId\": 12345, \"monto\": 25000.0, \"metodoPago\": \"WEBPAY\"}"
```

**Resultado esperado:**
```json
{
  "token": "uuid-generado",
  "monto": 25000.0,
  "ordenCompra": "12345",
  "url": "/api/webpay-simulation/simulate/uuid-generado"
}
```

#### 5. Verificar transacciones almacenadas
```bash
# Obtener todas las transacciones
curl -X GET http://localhost:8081/api/webpay-simulation/simulate/transactions
```

**Resultado esperado:**
```json
{
  "transactions": {
    "uuid-generado": {
      "token": "uuid-generado",
      "buyOrder": "12345",
      "amount": 25000.0,
      "status": "PENDING",
      "createdAt": 1234567890
    }
  },
  "count": 1
}
```

### 🛠️ Soluciones Comunes

#### Problema 1: La aplicación no responde
**Síntomas:** Error "Connection refused" o timeout
**Solución:**
1. Verificar que la aplicación esté ejecutándose:
   ```bash
   mvnw.cmd spring-boot:run
   ```
2. Esperar hasta que aparezca "Started FerremasApplication"
3. Verificar que no haya errores en los logs

#### Problema 2: Endpoints retornan 404
**Síntomas:** Error "404 Not Found"
**Solución:**
1. Verificar que el controlador esté registrado
2. Reiniciar la aplicación
3. Verificar que no haya conflictos de rutas

#### Problema 3: Endpoints retornan 500
**Síntomas:** Error "500 Internal Server Error"
**Solución:**
1. Revisar los logs de la aplicación
2. Verificar que todas las dependencias estén configuradas
3. Verificar que la base de datos esté accesible

#### Problema 4: Las transacciones no se guardan
**Síntomas:** El endpoint `/transactions` retorna lista vacía
**Solución:**
1. Verificar que el modo simulación esté activado
2. Verificar que el servicio esté funcionando
3. Verificar que no haya errores en el método `iniciarPagoSimulado`

### 🔧 Scripts de Diagnóstico

#### Script Automático
Ejecuta el script de diagnóstico:
```bash
diagnostico-webpay.bat
```

#### Verificación Manual
1. **Verificar aplicación:**
   ```bash
   curl -X GET http://localhost:8081/api/test/health
   ```

2. **Verificar simulación:**
   ```bash
   curl -X GET http://localhost:8081/api/webpay-simulation/simulate/scenario
   ```

3. **Probar transacción:**
   ```bash
   curl -X POST http://localhost:8081/api/webpay-simulation/simulate/init \
     -H "Content-Type: application/json" \
     -d "{\"pedidoId\": 99999, \"monto\": 10000.0, \"metodoPago\": \"WEBPAY\"}"
   ```

4. **Verificar historial:**
   ```bash
   curl -X GET http://localhost:8081/api/webpay-simulation/simulate/transactions
   ```

### 🌐 Verificación Web

#### Panel de Control
Abre en tu navegador:
```
http://localhost:8081/api/webpay-simulation/simulate/panel
```

**Deberías ver:**
- Selector de escenarios
- Formulario de prueba
- Tabla de transacciones (inicialmente vacía)

#### Simulación de Pago
Después de iniciar una transacción, abre:
```
http://localhost:8081/api/webpay-simulation/simulate/{token}
```

**Deberías ver:**
- Resultado del pago simulado
- Detalles de la transacción
- Estado del pago

### 📋 Checklist de Verificación

- [ ] La aplicación está ejecutándose en el puerto 8081
- [ ] El endpoint `/api/test/health` responde correctamente
- [ ] El endpoint `/api/webpay-simulation/simulate/scenario` responde
- [ ] Se puede configurar un escenario
- [ ] Se puede iniciar una transacción simulada
- [ ] Las transacciones se almacenan correctamente
- [ ] El panel de control es accesible
- [ ] La simulación web funciona

### 🚨 Si Nada Funciona

1. **Reiniciar la aplicación:**
   ```bash
   # Detener la aplicación (Ctrl+C)
   # Luego reiniciar
   mvnw.cmd spring-boot:run
   ```

2. **Verificar logs:**
   - Buscar errores en la consola
   - Verificar que no haya excepciones

3. **Verificar configuración:**
   - Revisar `application.properties`
   - Verificar que el puerto 8081 esté libre

4. **Verificar dependencias:**
   - Asegurar que MySQL esté ejecutándose
   - Verificar conexión a la base de datos

### 📞 Soporte Adicional

Si los problemas persisten:
1. Ejecuta el script de diagnóstico completo
2. Revisa los logs de la aplicación
3. Verifica que todos los archivos estén en su lugar
4. Confirma que la base de datos esté funcionando

---

**Nota:** La simulación de WebPay es completamente independiente de la API real de Transbank, por lo que no requiere credenciales reales ni conexión a internet. 