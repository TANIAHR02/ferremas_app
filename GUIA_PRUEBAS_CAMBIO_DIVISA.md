# Guía de Pruebas - Cambio de Divisas Ferremas

## 📋 Índice
1. [Pruebas con Postman](#pruebas-con-postman)
2. [Pruebas con JMeter](#pruebas-con-jmeter)
3. [Casos de Prueba](#casos-de-prueba)
4. [Resultados Esperados](#resultados-esperados)

---

## 🚀 Pruebas con Postman

### Configuración Inicial

1. **Crear una nueva Collection**
   - Abre Postman
   - Crea una nueva Collection llamada "Ferremas - Cambio de Divisas"
   - Configura la variable de entorno `baseUrl` = `http://localhost:8081/api/banco-central`

### Endpoints a Probar

#### 1. Obtener Tasa de Cambio General
```
GET {{baseUrl}}/tasa-cambio
```
**Headers:**
```
Accept: application/json
```

#### 2. Obtener Todas las Tasas de Cambio
```
GET {{baseUrl}}/tasas-cambio
```
**Headers:**
```
Accept: application/json
```

#### 3. Obtener Tasa Específica por Moneda
```
GET {{baseUrl}}/tasa-cambio/USD
GET {{baseUrl}}/tasa-cambio/EUR
GET {{baseUrl}}/tasa-cambio/CLP
```

#### 4. Conversión de Montos
```
GET {{baseUrl}}/convertir-usd?montoUSD=100
GET {{baseUrl}}/convertir-eur?montoEUR=50
GET {{baseUrl}}/convertir/USD?monto=25
GET {{baseUrl}}/convertir/EUR?monto=75
```

#### 5. Conversión Manual
```
GET {{baseUrl}}/convertir?montoExtranjero=100&tasaCambio=950.50
```

### Script de Prueba Automatizada en Postman

#### Pre-request Script (para cada request):
```javascript
// Verificar que la aplicación esté corriendo
pm.test("Aplicación disponible", function () {
    pm.response.to.have.status(200);
});
```

#### Tests Script (para cada request):
```javascript
// Verificar respuesta exitosa
pm.test("Status code is 200", function () {
    pm.response.to.have.status(200);
});

// Verificar tiempo de respuesta
pm.test("Response time is less than 2000ms", function () {
    pm.expect(pm.response.responseTime).to.be.below(2000);
});

// Verificar que la respuesta no esté vacía
pm.test("Response is not empty", function () {
    pm.expect(pm.response.text()).to.not.be.empty;
});
```

---

## 🔧 Pruebas con JMeter

### ⚠️ IMPORTANTE: Usar el archivo simplificado

**El archivo `ferremas-cambio-divisa.jmx` puede tener problemas de compatibilidad. Usa `ferremas-cambio-divisa-simple.jmx` en su lugar.**

### Configuración del Plan de Pruebas

#### 1. Configurar Variables de Usuario
- **Thread Group** → **User Defined Variables**
  - `baseUrl`: `http://localhost:8081`
  - `apiPath`: `/api/banco-central`

#### 2. Configurar HTTP Request Defaults
- **Config Element** → **HTTP Request Defaults**
  - **Server Name or IP**: `localhost`
  - **Port Number**: `8081`
  - **Protocol**: `http`

#### 3. Configurar HTTP Header Manager
- **Config Element** → **HTTP Header Manager**
  - **Name**: `Accept`
  - **Value**: `application/json`

### Casos de Prueba en JMeter

#### Caso 1: Obtener Tasa de Cambio
```
Name: Obtener Tasa de Cambio
Method: GET
Path: ${apiPath}/tasa-cambio
```

#### Caso 2: Obtener Todas las Tasas
```
Name: Obtener Todas las Tasas
Method: GET
Path: ${apiPath}/tasas-cambio
```

#### Caso 3: Obtener Tasa USD
```
Name: Obtener Tasa USD
Method: GET
Path: ${apiPath}/tasa-cambio/USD
```

#### Caso 4: Obtener Tasa EUR
```
Name: Obtener Tasa EUR
Method: GET
Path: ${apiPath}/tasa-cambio/EUR
```

#### Caso 5: Convertir USD
```
Name: Convertir USD
Method: GET
Path: ${apiPath}/convertir-usd?montoUSD=100
```

#### Caso 6: Convertir EUR
```
Name: Convertir EUR
Method: GET
Path: ${apiPath}/convertir-eur?montoEUR=50
```

#### Caso 7: Conversión Manual
```
Name: Conversión Manual
Method: GET
Path: ${apiPath}/convertir?montoExtranjero=100&tasaCambio=950.50
```

### Configuración de Assertions

#### Response Assertion
- **Apply to**: `Main sample only`
- **Pattern to test**: 
  - Para tasas: `.*tasaCambio.*`
  - Para conversiones: `.*[0-9]+.*`

#### JSON Assertion
- **JSON Path**: `$`
- **Expected Value**: `true`

#### Duration Assertion
- **Duration in milliseconds**: `2000`

### Configuración de Listeners

#### View Results Tree
- **Filename**: `results-tree.jtl`

#### Aggregate Report
- **Filename**: `aggregate-report.jtl`

#### Response Time Graph
- **Filename**: `response-time-graph.jtl`

---

## 📊 Casos de Prueba

### Pruebas Funcionales

#### 1. Pruebas Básicas
| Caso | Descripción | Entrada | Resultado Esperado |
|------|-------------|---------|-------------------|
| TC001 | Obtener tasa USD | GET /tasa-cambio | "1 USD = X CLP" |
| TC002 | Obtener todas las tasas | GET /tasas-cambio | Array con USD, EUR, CLP |
| TC003 | Convertir 100 USD | GET /convertir-usd?montoUSD=100 | 95050.0 |
| TC004 | Convertir 50 EUR | GET /convertir-eur?montoEUR=50 | 52512.50 |

#### 2. Pruebas de Error
| Caso | Descripción | Entrada | Resultado Esperado |
|------|-------------|---------|-------------------|
| TC005 | Moneda inexistente | GET /tasa-cambio/JPY | Error 500 |
| TC006 | Monto cero | GET /convertir-usd?montoUSD=0 | 0 |
| TC007 | Monto negativo | GET /convertir-usd?montoUSD=-10 | -9505.0 |

#### 3. Pruebas de Rendimiento
| Caso | Descripción | Carga | Resultado Esperado |
|------|-------------|-------|-------------------|
| TC008 | 10 usuarios simultáneos | 10 threads | Respuesta < 2s |
| TC009 | 50 usuarios simultáneos | 50 threads | Respuesta < 3s |
| TC010 | 100 usuarios simultáneos | 100 threads | Sin errores |

### Pruebas de Integración

#### 1. Conexión al Banco Central
- Verificar que se obtienen tasas reales
- Verificar fallback a datos de respaldo
- Verificar manejo de errores de red

#### 2. Precisión de Conversiones
- Verificar cálculos matemáticos
- Verificar redondeo correcto
- Verificar manejo de decimales

---

## 📈 Resultados Esperados

### Respuestas JSON

#### Tasa de Cambio
```json
"1 USD = 950.5 CLP"
```

#### Todas las Tasas
```json
[
  {
    "codigo": "USD",
    "tasaCambio": 950.5
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

#### Conversión
```json
95050.0
```

### Métricas de Rendimiento

| Métrica | Valor Esperado |
|---------|----------------|
| Tiempo de respuesta | < 2000ms |
| Throughput | > 100 req/s |
| Error Rate | < 1% |
| CPU Usage | < 80% |
| Memory Usage | < 512MB |

---

## 🛠️ Scripts de Automatización

### Script JMeter (JMX) - VERSIÓN SIMPLIFICADA
```xml
<?xml version="1.0" encoding="UTF-8"?>
<jmeterTestPlan version="1.2" properties="5.0" jmeter="5.6.3">
  <hashTree>
    <TestPlan guiclass="TestPlanGui" testclass="TestPlan" testname="Ferremas Cambio Divisas">
      <!-- Configuración básica sin assertions complejas -->
    </TestPlan>
    <hashTree>
      <ThreadGroup guiclass="ThreadGroupGui" testclass="ThreadGroup" testname="Pruebas Cambio Divisas">
        <!-- Thread Group básico -->
      </ThreadGroup>
      <hashTree>
        <!-- HTTP Requests simples -->
      </hashTree>
    </hashTree>
  </hashTree>
</jmeterTestPlan>
```

### Collection Postman
```json
{
  "info": {
    "name": "Ferremas - Cambio de Divisas",
    "schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json"
  },
  "item": [
    {
      "name": "Obtener Tasa de Cambio",
      "request": {
        "method": "GET",
        "header": [
          {
            "key": "Accept",
            "value": "application/json"
          }
        ],
        "url": {
          "raw": "{{baseUrl}}/tasa-cambio",
          "host": ["{{baseUrl}}"],
          "path": ["tasa-cambio"]
        }
      }
    }
  ]
}
```

---

## 📝 Checklist de Pruebas

### ✅ Pre-Pruebas
- [ ] Aplicación corriendo en puerto 8081
- [ ] API key del Banco Central configurada
- [ ] Base de datos conectada
- [ ] Herramientas de prueba instaladas

### ✅ Pruebas Funcionales
- [ ] Obtener tasa de cambio
- [ ] Obtener todas las tasas
- [ ] Conversión USD a CLP
- [ ] Conversión EUR a CLP
- [ ] Manejo de errores

### ✅ Pruebas de Rendimiento
- [ ] Tiempo de respuesta < 2s
- [ ] Throughput > 100 req/s
- [ ] Sin errores bajo carga
- [ ] Uso de recursos aceptable

### ✅ Pruebas de Integración
- [ ] Conexión al Banco Central
- [ ] Fallback a datos de respaldo
- [ ] Precisión de conversiones
- [ ] Manejo de errores de red

---

## 🚨 Solución de Problemas

### Error 500 - Aplicación no responde
```bash
# Verificar que esté corriendo
curl http://localhost:8081/actuator/health
```

### Error de conexión al Banco Central
- Verificar API key en application.properties
- Verificar conexión a internet
- Los datos de respaldo deberían funcionar

### Errores en JMeter
- **PROBLEMA**: Error de ClassCastException en archivo JMX
- **SOLUCIÓN**: Usar `ferremas-cambio-divisa-simple.jmx` en lugar del archivo original
- Verificar configuración de puerto
- Verificar headers correctos

### Errores en Postman
- Verificar variables de entorno
- Verificar headers
- Verificar scripts de prueba

---

## 📊 Reportes

### JMeter
- **HTML Report**: Generar reporte HTML completo
- **Dashboard**: Usar Grafana para visualización
- **Logs**: Revisar logs de errores

### Postman
- **Collection Runner**: Ejecutar todas las pruebas
- **Newman**: Automatización desde línea de comandos
- **Monitors**: Configurar monitoreo continuo

---

## 🎯 Conclusión

Esta guía te permite probar completamente la funcionalidad de cambio de divisas usando tanto Postman como JMeter. Las pruebas cubren:

- ✅ **Funcionalidad básica**
- ✅ **Manejo de errores**
- ✅ **Rendimiento**
- ✅ **Integración con Banco Central**
- ✅ **Automatización**

### 📁 Archivos Disponibles:

1. **`Ferremas-Cambio-Divisas.postman_collection.json`** - Collection de Postman
2. **`ferremas-cambio-divisa-simple.jmx`** - Archivo JMeter simplificado (RECOMENDADO)
3. **`ejecutar-pruebas.bat`** - Script de automatización
4. **`GUIA_PRUEBAS_CAMBIO_DIVISA.md`** - Esta guía completa

¡Tu implementación está lista para ser probada exhaustivamente! 🚀 