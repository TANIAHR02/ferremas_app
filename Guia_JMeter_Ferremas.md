# Guía Paso a Paso - Pruebas de Rendimiento con JMeter
## Sistema Ferremas - ASY5131 Integración de Plataformas

---

## 📋 Tabla de Contenidos

1. [Instalación y Configuración](#1-instalación-y-configuración)
2. [Configuración del Plan de Pruebas](#2-configuración-del-plan-de-pruebas)
3. [Creación de Thread Groups](#3-creación-de-thread-groups)
4. [Configuración de HTTP Requests](#4-configuración-de-http-requests)
5. [Configuración de Headers y Variables](#5-configuración-de-headers-y-variables)
6. [Configuración de Listeners](#6-configuración-de-listeners)
7. [Ejecución de Pruebas](#7-ejecución-de-pruebas)
8. [Análisis de Resultados](#8-análisis-de-resultados)
9. [Solución de Problemas Comunes](#9-solución-de-problemas-comunes)
10. [Mejores Prácticas](#10-mejores-prácticas)

---

## 1. Instalación y Configuración

### 1.1 Descarga e Instalación de JMeter

**Paso 1: Descargar JMeter**
1. Ve a [https://jmeter.apache.org/download_jmeter.cgi](https://jmeter.apache.org/download_jmeter.cgi)
2. Descarga la versión más reciente (5.6.3 o superior)
3. Selecciona el archivo `.zip` para Windows

**Paso 2: Instalar JMeter**
1. Extrae el archivo ZIP en una carpeta (ej: `C:\apache-jmeter-5.6.3`)
2. Navega a la carpeta `bin`
3. Ejecuta `jmeter.bat` (Windows) o `jmeter.sh` (Linux/Mac)

**Paso 3: Verificar la Instalación**
- Se abrirá la interfaz gráfica de JMeter
- Verifica que aparezca "Apache JMeter" en el título

### 1.2 Configuración Inicial

**Paso 1: Configurar Variables de Entorno**
```bash
# Agregar JMeter al PATH (Windows)
set JMETER_HOME=C:\apache-jmeter-5.6.3
set PATH=%PATH%;%JMETER_HOME%\bin
```

**Paso 2: Verificar Java**
```bash
java -version
# Debe mostrar Java 8 o superior
```

---

## 2. Configuración del Plan de Pruebas

### 2.1 Crear Nuevo Plan de Pruebas

**Paso 1: Abrir JMeter**
1. Ejecuta `jmeter.bat`
2. Se abrirá la interfaz gráfica

**Paso 2: Crear Plan de Pruebas**
1. Click derecho en "Test Plan"
2. Selecciona "Add" → "Threads (Users)" → "Thread Group"
3. Renombra el Thread Group como "Login Performance Test"

### 2.2 Configurar Variables Globales

**Paso 1: Agregar Variables de Usuario**
1. Click derecho en "Test Plan"
2. Selecciona "Add" → "Config Element" → "User Defined Variables"
3. Configura las siguientes variables:

| Variable Name | Variable Value |
|---------------|----------------|
| `base_url` | `http://localhost:8080` |
| `content_type` | `application/json` |
| `test_user_email` | `admin@ferremas.com` |
| `test_user_password` | `admin123` |

**Paso 2: Verificar Configuración**
- Las variables estarán disponibles en todo el plan de pruebas
- Se pueden usar como `${base_url}` en las configuraciones

---

## 3. Creación de Thread Groups

### 3.1 Thread Group 1: 10 Usuarios Concurrentes

**Paso 1: Crear Thread Group**
1. Click derecho en "Test Plan"
2. "Add" → "Threads (Users)" → "Thread Group"
3. Renombrar como "10 Users - Login Test"

**Paso 2: Configurar Parámetros**
```
Number of Threads (users): 10
Ramp-up period (seconds): 30
Loop Count: 10
Scheduler: ☐ (desmarcado)
```

**Paso 3: Configurar Comportamiento**
```
Same user on each iteration: ☑ (marcado)
Delay Thread creation until needed: ☐ (desmarcado)
```

### 3.2 Thread Group 2: 50 Usuarios Concurrentes

**Paso 1: Crear Segundo Thread Group**
1. Click derecho en "Test Plan"
2. "Add" → "Threads (Users)" → "Thread Group"
3. Renombrar como "50 Users - Products Test"

**Paso 2: Configurar Parámetros**
```
Number of Threads (users): 50
Ramp-up period (seconds): 60
Loop Count: 20
Scheduler: ☐ (desmarcado)
```

### 3.3 Thread Group 3: 100 Usuarios Concurrentes

**Paso 1: Crear Tercer Thread Group**
1. Click derecho en "Test Plan"
2. "Add" → "Threads (Users)" → "Thread Group"
3. Renombrar como "100 Users - Orders Test"

**Paso 2: Configurar Parámetros**
```
Number of Threads (users): 100
Ramp-up period (seconds): 120
Loop Count: 5
Scheduler: ☐ (desmarcado)
```

---

## 4. Configuración de HTTP Requests

### 4.1 Configurar HTTP Request Defaults

**Paso 1: Agregar HTTP Request Defaults**
1. Click derecho en cada Thread Group
2. "Add" → "Config Element" → "HTTP Request Defaults"
3. Configurar:

```
Protocol: http
Server Name or IP: ${base_url}
Port Number: 8080
Content encoding: UTF-8
```

### 4.2 Configurar HTTP Header Manager

**Paso 1: Agregar HTTP Header Manager**
1. Click derecho en cada Thread Group
2. "Add" → "Config Element" → "HTTP Header Manager"
3. Agregar headers:

| Header Name | Header Value |
|-------------|--------------|
| `Content-Type` | `${content_type}` |
| `Accept` | `application/json` |
| `User-Agent` | `JMeter Test` |

### 4.3 Crear HTTP Requests Específicos

#### 4.3.1 HTTP Request para Login

**Paso 1: Crear HTTP Request**
1. Click derecho en "10 Users - Login Test"
2. "Add" → "Sampler" → "HTTP Request"
3. Renombrar como "POST /api/auth/login"

**Paso 2: Configurar Request**
```
Method: POST
Path: /api/auth/login
Parameters: (vacío)
Body Data:
{
  "email": "${test_user_email}",
  "password": "${test_user_password}"
}
```

#### 4.3.2 HTTP Request para Productos

**Paso 1: Crear HTTP Request**
1. Click derecho en "50 Users - Products Test"
2. "Add" → "Sampler" → "HTTP Request"
3. Renombrar como "GET /api/productos"

**Paso 2: Configurar Request**
```
Method: GET
Path: /api/productos
Parameters: (vacío)
Body Data: (vacío)
```

#### 4.3.3 HTTP Request para Pedidos

**Paso 1: Crear HTTP Request**
1. Click derecho en "100 Users - Orders Test"
2. "Add" → "Sampler" → "HTTP Request"
3. Renombrar como "POST /api/pedidos"

**Paso 2: Configurar Request**
```
Method: POST
Path: /api/pedidos
Parameters: (vacío)
Body Data:
{
  "usuarioId": 1,
  "productos": [
    {
      "productoId": 1,
      "cantidad": 1
    }
  ]
}
```

---

## 5. Configuración de Headers y Variables

### 5.1 Configurar Content-Type para POST Requests

**Paso 1: Agregar Header Manager Específico**
1. Click derecho en cada HTTP Request POST
2. "Add" → "Config Element" → "HTTP Header Manager"
3. Configurar:

```
Content-Type: application/json
```

### 5.2 Configurar Variables Dinámicas

**Paso 1: Agregar CSV Data Set Config**
1. Click derecho en Thread Group
2. "Add" → "Config Element" → "CSV Data Set Config"
3. Configurar:

```
Filename: test_data.csv
Variable Names: email,password,productoId
Delimiter: ,
Recycle on EOF: true
Stop thread on EOF: false
```

**Paso 2: Crear Archivo test_data.csv**
```csv
email,password,productoId
admin@ferremas.com,admin123,1
cliente1@test.com,cliente123,2
cliente2@test.com,cliente123,3
```

---

## 6. Configuración de Listeners

### 6.1 View Results Tree

**Paso 1: Agregar View Results Tree**
1. Click derecho en cada Thread Group
2. "Add" → "Listener" → "View Results Tree"
3. Configurar:

```
Filename: (dejar vacío para ver en pantalla)
Configure: ☑ Save Response Data
☑ Save Response Headers
☑ Save Request Headers
```

### 6.2 Aggregate Report

**Paso 1: Agregar Aggregate Report**
1. Click derecho en cada Thread Group
2. "Add" → "Listener" → "Aggregate Report"
3. Configurar:

```
Filename: aggregate_report.jtl
☑ Save Response Data
☑ Save Response Headers
```

### 6.3 Graph Results

**Paso 1: Agregar Graph Results**
1. Click derecho en cada Thread Group
2. "Add" → "Listener" → "Graph Results"
3. Configurar:

```
Filename: graph_results.jtl
☑ Save Response Data
```

---

## 7. Ejecución de Pruebas

### 7.1 Preparación Previa

**Paso 1: Verificar Sistema**
1. Asegúrate de que tu aplicación Ferremas esté ejecutándose
2. Verifica que esté accesible en `http://localhost:8080`
3. Confirma que la base de datos tenga datos de prueba

**Paso 2: Verificar Configuración JMeter**
1. Revisa que todas las variables estén configuradas
2. Verifica que los endpoints sean correctos
3. Confirma que los datos de prueba estén disponibles

### 7.2 Ejecutar Pruebas

**Paso 1: Ejecutar Thread Group Individual**
1. Selecciona el Thread Group "10 Users - Login Test"
2. Click en el botón verde "Start" (▶️)
3. Observa los resultados en tiempo real

**Paso 2: Ejecutar Todo el Plan**
1. Selecciona "Test Plan" en el árbol
2. Click en "Start" (▶️)
3. Todas las pruebas se ejecutarán secuencialmente

**Paso 3: Monitorear Ejecución**
- Observa el contador de usuarios activos
- Revisa los listeners en tiempo real
- Monitorea el uso de recursos del sistema

### 7.3 Detener Pruebas

**Paso 1: Detener Ejecución**
1. Click en el botón rojo "Stop" (⏹️)
2. Espera a que todas las threads terminen
3. Revisa los resultados finales

---

## 8. Análisis de Resultados

### 8.1 Interpretar View Results Tree

**Paso 1: Revisar Respuestas Individuales**
1. Selecciona "View Results Tree"
2. Click en cualquier request para ver detalles
3. Revisa:
   - Response Code (debe ser 200, 201, etc.)
   - Response Time
   - Response Data
   - Request Data

**Paso 2: Filtrar Resultados**
- Usa el filtro para mostrar solo errores
- Busca patrones en las respuestas
- Identifica requests problemáticos

### 8.2 Analizar Aggregate Report

**Paso 1: Revisar Métricas Principales**
```
Label: Nombre del request
Samples: Número de requests ejecutados
Average: Tiempo promedio de respuesta
Median: Tiempo mediano
90% Line: 90% de requests bajo este tiempo
95% Line: 95% de requests bajo este tiempo
99% Line: 99% de requests bajo este tiempo
Min: Tiempo mínimo
Max: Tiempo máximo
Error %: Porcentaje de errores
Throughput: Requests por segundo
```

**Paso 2: Interpretar Resultados**
- **Tiempo promedio < 2s**: Excelente
- **Tiempo promedio 2-5s**: Aceptable
- **Tiempo promedio > 5s**: Requiere optimización
- **Error % > 1%**: Problema crítico

### 8.3 Analizar Graph Results

**Paso 1: Interpretar Gráfico**
- **Línea azul**: Tiempo de respuesta promedio
- **Línea roja**: Desviación estándar
- **Línea verde**: Throughput (requests/segundo)

**Paso 2: Identificar Patrones**
- Picos de tiempo de respuesta
- Caídas en throughput
- Patrones de carga

### 8.4 Generar Reporte

**Paso 1: Exportar Resultados**
1. Click derecho en "Aggregate Report"
2. "Save Table Data"
3. Guardar como CSV o HTML

**Paso 2: Crear Dashboard**
1. Usa los datos exportados
2. Crea gráficos en Excel o similar
3. Documenta hallazgos principales

---

## 9. Solución de Problemas Comunes

### 9.1 Error: Connection Refused

**Síntoma**: Error 404 o Connection Refused
**Causa**: Aplicación no está ejecutándose
**Solución**:
1. Verifica que tu aplicación Spring Boot esté corriendo
2. Confirma el puerto 8080
3. Prueba el endpoint manualmente con curl

### 9.2 Error: OutOfMemoryError

**Síntoma**: JMeter se cuelga o muestra error de memoria
**Causa**: Muy pocos usuarios o memoria insuficiente
**Solución**:
1. Aumenta la memoria de JMeter:
   ```bash
   set HEAP=-Xms1g -Xmx4g -XX:MaxMetaspaceSize=256m
   ```
2. Reduce el número de usuarios concurrentes
3. Aumenta el tiempo entre requests

### 9.3 Error: SSL/TLS

**Síntoma**: Errores de certificado SSL
**Causa**: Configuración SSL incorrecta
**Solución**:
1. En HTTP Request Defaults, configura:
   ```
   Protocol: https
   Port: 443
   ```
2. O deshabilita SSL para pruebas locales

### 9.4 Error: JSON Parsing

**Síntoma**: Errores 400 Bad Request
**Causa**: JSON mal formado en Body Data
**Solución**:
1. Verifica la sintaxis JSON
2. Usa un validador JSON online
3. Confirma que Content-Type sea application/json

### 9.5 Error: Timeout

**Síntoma**: Requests que tardan mucho o fallan
**Causa**: Sistema sobrecargado o configuración incorrecta
**Solución**:
1. Aumenta el timeout en HTTP Request:
   ```
   Connect Timeout: 5000
   Response Timeout: 30000
   ```
2. Reduce la carga de usuarios
3. Optimiza la aplicación

---

## 10. Mejores Prácticas

### 10.1 Configuración de Pruebas

**✅ Hacer**:
- Usar variables para URLs y datos
- Configurar timeouts apropiados
- Usar listeners apropiados para cada caso
- Documentar la configuración

**❌ No hacer**:
- Ejecutar pruebas sin preparar el sistema
- Usar datos de producción
- Ignorar los resultados de errores
- Ejecutar sin monitoreo

### 10.2 Ejecución de Pruebas

**✅ Hacer**:
- Ejecutar pruebas en horarios de bajo tráfico
- Monitorear recursos del sistema
- Documentar el entorno de pruebas
- Validar resultados antes de continuar

**❌ No hacer**:
- Ejecutar en producción sin autorización
- Ignorar advertencias de memoria
- Ejecutar sin respaldo de datos
- Modificar configuración durante la ejecución

### 10.3 Análisis de Resultados

**✅ Hacer**:
- Revisar todos los tipos de errores
- Comparar con benchmarks establecidos
- Documentar hallazgos importantes
- Crear reportes ejecutivos

**❌ No hacer**:
- Ignorar outliers en los datos
- No considerar el contexto del sistema
- Hacer conclusiones sin evidencia
- No documentar limitaciones

### 10.4 Configuración Recomendada

**Para Pruebas de Desarrollo**:
```
Number of Threads: 10-50
Ramp-up: 30-60 segundos
Loop Count: 5-10
Duration: 5-10 minutos
```

**Para Pruebas de Carga**:
```
Number of Threads: 100-500
Ramp-up: 120-300 segundos
Loop Count: 10-20
Duration: 15-30 minutos
```

**Para Pruebas de Estrés**:
```
Number of Threads: 500-1000
Ramp-up: 300-600 segundos
Loop Count: 20-50
Duration: 30-60 minutos
```

---

## 📊 Ejemplo de Resultados Esperados

### Resultados para 10 Usuarios
```
Label                    | Samples | Average | 90% Line | Error % | Throughput
POST /api/auth/login     | 100     | 0.8s    | 1.2s     | 0%      | 12.5/sec
GET /api/productos       | 200     | 0.5s    | 0.8s     | 0%      | 40.0/sec
POST /api/pedidos        | 50      | 1.2s    | 2.0s     | 0%      | 8.3/sec
```

### Resultados para 50 Usuarios
```
Label                    | Samples | Average | 90% Line | Error % | Throughput
POST /api/auth/login     | 1000    | 1.2s    | 2.1s     | 0%      | 41.7/sec
GET /api/productos       | 1000    | 0.9s    | 1.5s     | 0%      | 55.6/sec
POST /api/pedidos        | 1000    | 2.1s    | 4.0s     | 0%      | 23.8/sec
```

### Resultados para 100 Usuarios
```
Label                    | Samples | Average | 90% Line | Error % | Throughput
POST /api/auth/login     | 500     | 1.8s    | 3.5s     | 1%      | 55.6/sec
GET /api/productos       | 500     | 1.3s    | 2.2s     | 0%      | 76.9/sec
POST /api/pedidos        | 500     | 3.2s    | 6.1s     | 2%      | 31.3/sec
```

---

## 🎯 Checklist de Verificación

### Antes de Ejecutar Pruebas
- [ ] JMeter instalado y configurado
- [ ] Aplicación Ferremas ejecutándose
- [ ] Base de datos con datos de prueba
- [ ] Variables configuradas correctamente
- [ ] Endpoints verificados manualmente
- [ ] Listeners configurados

### Durante la Ejecución
- [ ] Monitorear uso de CPU y memoria
- [ ] Verificar que no hay errores críticos
- [ ] Observar tiempo de respuesta
- [ ] Documentar comportamientos inesperados

### Después de la Ejecución
- [ ] Exportar resultados
- [ ] Analizar métricas principales
- [ ] Identificar cuellos de botella
- [ ] Documentar hallazgos
- [ ] Generar reporte ejecutivo

---

## 🔧 Configuración Avanzada

### Configurar Think Time

**Paso 1: Agregar Constant Timer**
1. Click derecho en HTTP Request
2. "Add" → "Timer" → "Constant Timer"
3. Configurar:
```
Thread Delay (milliseconds): 2000
```

### Configurar Assertions

**Paso 1: Agregar Response Assertion**
1. Click derecho en HTTP Request
2. "Add" → "Assertions" → "Response Assertion"
3. Configurar:
```
Apply to: Main sample only
Field to test: Response Code
Pattern to test: 200
```

### Configurar CSV Data Set Config

**Paso 1: Crear archivo de datos**
```csv
email,password,productoId,usuarioId
admin@ferremas.com,admin123,1,1
cliente1@test.com,cliente123,2,2
cliente2@test.com,cliente123,3,3
```

**Paso 2: Configurar en JMeter**
```
Filename: test_data.csv
Variable Names: email,password,productoId,usuarioId
Delimiter: ,
Recycle on EOF: true
Stop thread on EOF: false
```

---

## 📈 Interpretación de Gráficos

### View Results Tree
- **Verde**: Request exitoso
- **Rojo**: Request fallido
- **Gris**: Request en progreso

### Aggregate Report
- **Samples**: Total de requests
- **Average**: Tiempo promedio
- **Median**: Tiempo mediano
- **90% Line**: 90% de requests bajo este tiempo
- **95% Line**: 95% de requests bajo este tiempo
- **99% Line**: 99% de requests bajo este tiempo
- **Min**: Tiempo mínimo
- **Max**: Tiempo máximo
- **Error %**: Porcentaje de errores
- **Throughput**: Requests por segundo

### Graph Results
- **Línea azul**: Tiempo de respuesta promedio
- **Línea roja**: Desviación estándar
- **Línea verde**: Throughput (requests/segundo)

---

**Documento preparado para**: ASY5131 - Integración de Plataformas  
**Sistema**: Ferremas  
**Herramienta**: Apache JMeter 5.6.3  
**Versión**: 1.0 