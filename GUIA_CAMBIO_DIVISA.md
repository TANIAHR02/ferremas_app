# Guía para Probar el Cambio de Divisas - Ferremas

## Descripción
Esta guía te ayudará a comprobar que la funcionalidad de cambio de divisas funciona correctamente en tu proyecto Ferremas, incluyendo la conexión al Banco Central de Chile.

## Configuración Inicial

### 1. Obtener API Key del Banco Central
Para conectarte al Banco Central de Chile y obtener tasas de cambio reales:

1. Visita: https://api.sbif.cl/
2. Registra tu email
3. Obtén tu API key gratuita
4. Reemplaza `TU_API_KEY_AQUI` en `application.properties` con tu API key real

### 2. Configuración en application.properties
```properties
# Banco Central Configuration
bcentral.api.url=https://api.sbif.cl/api-sbifv3/recursos_api/dolar?apikey=TU_API_KEY_AQUI&formato=json
```

## Endpoints Disponibles

### Tasas de Cambio
- `GET /api/banco-central/tasa-cambio` - Obtiene la tasa del USD
- `GET /api/banco-central/tasas-cambio` - Obtiene todas las tasas disponibles
- `GET /api/banco-central/tasa-cambio/{codigoMoneda}` - Obtiene tasa específica (USD, EUR, CLP)

### Conversión de Montos
- `GET /api/banco-central/convertir?montoExtranjero=X&tasaCambio=Y` - Conversión manual
- `GET /api/banco-central/convertir/{codigoMoneda}?monto=X` - Conversión automática
- `GET /api/banco-central/convertir-usd?montoUSD=X` - Conversión específica USD
- `GET /api/banco-central/convertir-eur?montoEUR=X` - Conversión específica EUR

## Cómo Probar

### Opción 1: Usando el archivo de pruebas HTTP
1. Abre el archivo `pruebas-cambio-divisa.http` en VS Code
2. Instala la extensión "REST Client" si no la tienes
3. Ejecuta las pruebas una por una haciendo clic en "Send Request"

### Opción 2: Usando cURL desde terminal
```bash
# Obtener tasa de cambio del USD
curl -X GET "http://localhost:8081/api/banco-central/tasa-cambio"

# Obtener todas las tasas
curl -X GET "http://localhost:8081/api/banco-central/tasas-cambio"

# Convertir 100 USD a CLP
curl -X GET "http://localhost:8081/api/banco-central/convertir-usd?montoUSD=100"

# Convertir 50 EUR a CLP
curl -X GET "http://localhost:8081/api/banco-central/convertir-eur?montoEUR=50"
```

### Opción 3: Usando Postman
1. Importa las siguientes URLs en Postman:
   - `http://localhost:8081/api/banco-central/tasa-cambio`
   - `http://localhost:8081/api/banco-central/tasas-cambio`
   - `http://localhost:8081/api/banco-central/convertir-usd?montoUSD=100`

## Casos de Prueba

### Pruebas Básicas
1. **Obtener tasa de cambio**: Debería devolver la tasa actual del USD
2. **Obtener todas las tasas**: Debería devolver un array con USD, EUR y CLP
3. **Convertir montos**: Debería multiplicar el monto por la tasa de cambio

### Pruebas de Error
1. **Moneda inexistente**: Debería devolver error 500
2. **Monto cero**: Debería devolver 0
3. **Monto negativo**: Debería devolver valor negativo

### Pruebas de Conexión al Banco Central
1. **Con API key válida**: Debería obtener tasas reales
2. **Sin API key o API caída**: Debería usar datos de respaldo

## Respuestas Esperadas

### Tasa de Cambio
```json
"1 USD = 950.50 CLP"
```

### Todas las Tasas
```json
[
  {
    "codigo": "USD",
    "tasaCambio": 950.50
  },
  {
    "codigo": "EUR", 
    "tasaCambio": 1050.25
  },
  {
    "codigo": "CLP",
    "tasaCambio": 1.0
  }
]
```

### Conversión
```json
95050.00
```

## Verificación de Funcionamiento

### ✅ Funciona Correctamente Si:
- Las tasas de cambio se obtienen del Banco Central
- Las conversiones son matemáticamente correctas
- Los errores se manejan apropiadamente
- Los datos de respaldo funcionan cuando la API falla

### ❌ Problemas Comunes:
- Error 500: Verificar que la aplicación esté corriendo en puerto 8081
- Tasas de respaldo: La API del Banco Central no está disponible
- Errores de conversión: Verificar que los montos sean números válidos

## Solución de Problemas

### La aplicación no responde
```bash
# Verificar que esté corriendo
curl http://localhost:8081/actuator/health
```

### Error de conexión al Banco Central
- Verificar que la API key sea válida
- Verificar conexión a internet
- Los datos de respaldo deberían funcionar automáticamente

### Errores de conversión
- Verificar que los montos sean números válidos
- Verificar que las tasas de cambio no sean nulas

## Notas Importantes

1. **Datos de Respaldo**: Si la API del Banco Central falla, el sistema usa tasas de respaldo
2. **API Gratuita**: La API del Banco Central tiene límites de uso gratuito
3. **Tasas en Tiempo Real**: Las tasas se obtienen en tiempo real cuando la API está disponible
4. **Monedas Soportadas**: USD, EUR y CLP (peso chileno)

## ejecutar

Esta implementación incluye:
- ✅ Conexión real al Banco Central de Chile
- ✅ Manejo de errores y datos de respaldo
- ✅ Endpoints REST para todas las operaciones
- ✅ Pruebas automatizadas con archivos HTTP
- ✅ Documentación completa
- ✅ Código limpio y bien estructurado 

## Ejecutar Pruebas

Para ejecutar todas las pruebas automáticamente, sigue estos pasos:

1. Descarga el archivo `ejecutar-pruebas.bat`
2. Ejecuta el archivo haciendo doble clic

¡Tu funcionalidad de cambio de divisas está completamente probada y lista para demostrar! 🎯 