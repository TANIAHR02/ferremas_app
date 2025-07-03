# Guía de Simulación WebPay - 4 Escenarios Básicos

## Descripción General

Esta guía describe los 4 escenarios básicos de simulación de WebPay disponibles en la aplicación Ferremas. La simulación permite probar diferentes situaciones de pago sin conectarse a la API real de Transbank.

## Escenarios Disponibles

### 1. **SUCCESS** - Pago Exitoso
- **Descripción**: Simula una transacción aprobada por el banco
- **Estado**: AUTHORIZED
- **Código de Respuesta**: 00
- **Mensaje**: Sin errores
- **Detalles**: Incluye código de autorización, últimos dígitos de tarjeta
- **Uso**: Probar flujo normal de pago

### 2. **FAIL** - Pago Fallido
- **Descripción**: Simula una transacción rechazada por el banco
- **Estado**: FAILED
- **Código de Respuesta**: 05
- **Mensaje**: "Transacción rechazada por el banco"
- **Detalles**: Tarjeta VISA de crédito
- **Uso**: Probar manejo de pagos rechazados

### 3. **TIMEOUT** - Tiempo de Espera Agotado
- **Descripción**: Simula un tiempo de espera agotado
- **Estado**: TIMEOUT
- **Código de Respuesta**: 68
- **Mensaje**: "Tiempo de espera agotado"
- **Detalles**: Sin información de tarjeta
- **Uso**: Probar manejo de timeouts

### 4. **INSUFFICIENT_FUNDS** - Fondos Insuficientes
- **Descripción**: Simula una tarjeta sin fondos suficientes
- **Estado**: FAILED
- **Código de Respuesta**: 51
- **Mensaje**: "Fondos insuficientes"
- **Detalles**: Tarjeta VISA de débito
- **Uso**: Probar validación de fondos

## Códigos de Respuesta

| Código | Descripción |
|--------|-------------|
| 00 | Transacción aprobada |
| 05 | Transacción rechazada |
| 51 | Fondos insuficientes |
| 68 | Error de comunicación/Timeout |

## Estados de Transacción

- **PENDING**: Transacción en proceso
- **AUTHORIZED**: Transacción autorizada
- **FAILED**: Transacción fallida
- **TIMEOUT**: Transacción con timeout

## Cómo Usar los Escenarios

### 1. Configurar Escenario
```bash
curl -X POST http://localhost:8082/api/webpay-simulation/simulate/scenario \
  -H "Content-Type: application/json" \
  -d '{"scenario":"SUCCESS"}'
```

### 2. Iniciar Transacción
```bash
curl -X POST http://localhost:8082/api/webpay-simulation/simulate/init \
  -H "Content-Type: application/json" \
  -d '{"pedidoId":1001,"monto":25000}'
```

### 3. Consultar Estado
```bash
curl http://localhost:8082/api/webpay-simulation/simulate/status/{TOKEN}
```

### 4. Ver Resultado Web
```
http://localhost:8082/api/webpay-simulation/simulate/{TOKEN}
```

## Comandos para Cada Escenario

### PAGO EXITOSO
```bash
# Configurar escenario
curl -X POST http://localhost:8082/api/webpay-simulation/simulate/scenario -H "Content-Type: application/json" -d "{\"scenario\":\"SUCCESS\"}"

# Generar token
curl -X POST http://localhost:8082/api/webpay-simulation/simulate/init -H "Content-Type: application/json" -d "{\"pedidoId\":1001,\"monto\":25000}"
```

### PAGO FALLIDO
```bash
# Configurar escenario
curl -X POST http://localhost:8082/api/webpay-simulation/simulate/scenario -H "Content-Type: application/json" -d "{\"scenario\":\"FAIL\"}"

# Generar token
curl -X POST http://localhost:8082/api/webpay-simulation/simulate/init -H "Content-Type: application/json" -d "{\"pedidoId\":1002,\"monto\":15000}"
```

### TIMEOUT
```bash
# Configurar escenario
curl -X POST http://localhost:8082/api/webpay-simulation/simulate/scenario -H "Content-Type: application/json" -d "{\"scenario\":\"TIMEOUT\"}"

# Generar token
curl -X POST http://localhost:8082/api/webpay-simulation/simulate/init -H "Content-Type: application/json" -d "{\"pedidoId\":1003,\"monto\":30000}"
```

### FONDOS INSUFICIENTES
```bash
# Configurar escenario
curl -X POST http://localhost:8082/api/webpay-simulation/simulate/scenario -H "Content-Type: application/json" -d "{\"scenario\":\"INSUFFICIENT_FUNDS\"}"

# Generar token
curl -X POST http://localhost:8082/api/webpay-simulation/simulate/init -H "Content-Type: application/json" -d "{\"pedidoId\":1004,\"monto\":50000}"
```

## Comandos Útiles

### Obtener Escenarios Disponibles
```bash
curl http://localhost:8082/api/webpay-simulation/simulate/scenarios
```

### Obtener Escenario Actual
```bash
curl http://localhost:8082/api/webpay-simulation/simulate/scenario
```

### Obtener Transacciones
```bash
curl http://localhost:8082/api/webpay-simulation/simulate/transactions
```

### Limpiar Transacciones
```bash
curl -X DELETE http://localhost:8082/api/webpay-simulation/simulate/transactions
```

## Archivos de Prueba

### Archivo HTTP
- `pruebas-webpay-4-escenarios.http`: Contiene todas las pruebas para los 4 escenarios

### Script Batch Automático
- `test-4-escenarios-webpay.bat`: Ejecuta automáticamente los 4 escenarios

### Panel de Control Web
- `http://localhost:8082/api/webpay-simulation/simulate/panel`: Interfaz web para controlar la simulación

## Ejemplo de Uso Completo

```bash
# 1. Verificar que la aplicación esté ejecutándose
curl http://localhost:8082/api/test/health

# 2. Obtener escenarios disponibles
curl http://localhost:8082/api/webpay-simulation/simulate/scenarios

# 3. Configurar escenario de éxito
curl -X POST http://localhost:8082/api/webpay-simulation/simulate/scenario -H "Content-Type: application/json" -d "{\"scenario\":\"SUCCESS\"}"

# 4. Generar token
curl -X POST http://localhost:8082/api/webpay-simulation/simulate/init -H "Content-Type: application/json" -d "{\"pedidoId\":1001,\"monto\":25000}"

# Respuesta esperada:
# {"token":"uuid-generado","monto":25000,"ordenCompra":"1001","url":"/api/webpay-simulation/simulate/uuid-generado"}

# 5. Ver resultado en navegador
# http://localhost:8082/api/webpay-simulation/simulate/uuid-generado
```

## Casos de Uso Recomendados

### Desarrollo y Testing
1. **SUCCESS**: Verificar flujo normal de pago
2. **FAIL**: Probar manejo de errores de pago
3. **TIMEOUT**: Probar manejo de timeouts
4. **INSUFFICIENT_FUNDS**: Probar validación de fondos

### Validación de Formularios
- Usar **SUCCESS** para verificar que el formulario funciona correctamente
- Usar **FAIL** para probar mensajes de error
- Usar **TIMEOUT** para probar manejo de conexiones lentas
- Usar **INSUFFICIENT_FUNDS** para probar validaciones de monto

## Notas Importantes

1. **Tokens Únicos**: Cada transacción genera un token único
2. **Almacenamiento en Memoria**: Las transacciones se almacenan en memoria (se pierden al reiniciar)
3. **Sin Conexión Real**: No se conecta a Transbank real
4. **Configuración Dinámica**: Los escenarios se pueden cambiar en tiempo real
5. **Logs Detallados**: Todas las transacciones se registran en logs

## Solución de Problemas

### Error: Puerto 8082 en uso
```bash
# Verificar procesos en el puerto
netstat -ano | findstr :8082

# Terminar proceso si es necesario
taskkill /PID {PID} /F
```

### Error: Aplicación no responde
```bash
# Verificar salud de la aplicación
curl http://localhost:8082/api/test/health
```

### Error: Token no encontrado
- Verificar que el token existe en las transacciones
- Usar el endpoint de transacciones para listar tokens válidos

## Próximos Pasos

1. Ejecutar el script `test-4-escenarios-webpay.bat`
2. Revisar resultados en el panel web
3. Probar escenarios específicos según necesidades
4. Integrar con pruebas automatizadas 
