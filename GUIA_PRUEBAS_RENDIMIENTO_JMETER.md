# Guía de Pruebas de Rendimiento con JMeter - FerremasApp

## 📋 Descripción General

Este documento describe cómo ejecutar pruebas de rendimiento en la aplicación FerremasApp utilizando Apache JMeter. El archivo `ferremasapp-performance-test.jmx` contiene escenarios realistas que simulan el comportamiento de usuarios reales.

## 🎯 Objetivos de las Pruebas

- **Evaluar el rendimiento** de la aplicación bajo carga
- **Identificar cuellos de botella** en el sistema
- **Medir tiempos de respuesta** de diferentes funcionalidades
- **Simular usuarios concurrentes** navegando por la aplicación
- **Probar la estabilidad** del sistema

## 📁 Archivos Incluidos

- `ferremasapp-performance-test.jmx` - Archivo principal de JMeter
- `GUIA_PRUEBAS_RENDIMIENTO_JMETER.md` - Esta guía

## 🚀 Configuración Inicial

### 1. Requisitos Previos

- **Apache JMeter** instalado (versión 5.6.3 o superior)
- **Java 8** o superior
- **Aplicación FerremasApp** ejecutándose en `http://localhost:8082`

### 2. Verificar que la Aplicación esté Ejecutándose

```bash
# Verificar que la aplicación esté corriendo
curl http://localhost:8082/actuator/health
```

### 3. Abrir JMeter

```bash
# En Windows
jmeter.bat

# En Linux/Mac
./jmeter.sh
```

## 📊 Escenarios de Prueba Incluidos

### 1. **Usuarios Navegando Productos** (10 usuarios, 5 iteraciones)
- **Objetivo**: Simular usuarios navegando por la aplicación
- **Acciones**:
  - Cargar página principal
  - Navegar a productos
  - Agregar productos al carrito
  - Ver carrito de compras
- **Tiempo de rampa**: 30 segundos
- **Tiempo de espera**: 1-3 segundos entre acciones

### 2. **Usuarios Realizando Pagos** (5 usuarios, 3 iteraciones)
- **Objetivo**: Probar el flujo de pagos con WebPay
- **Acciones**:
  - Inicializar pago WebPay
  - Consultar estado del pago
- **Tiempo de rampa**: 15 segundos
- **Tiempo de espera**: 2-5 segundos entre acciones

### 3. **Usuarios Consultando APIs** (15 usuarios, 10 iteraciones)
- **Objetivo**: Probar APIs críticas del sistema
- **Acciones**:
  - Consultar divisas del Banco Central
  - Obtener lista de productos
  - Verificar health check
- **Tiempo de rampa**: 20 segundos
- **Tiempo de espera**: 0.5-1.5 segundos entre acciones

## 🔧 Configuración de Variables

El archivo JMeter incluye las siguientes variables configurables:

| Variable | Valor Predeterminado | Descripción |
|----------|---------------------|-------------|
| `BASE_URL` | `http://localhost:8082` | URL base de la aplicación |
| `THINK_TIME_MIN` | `1000` | Tiempo mínimo de espera (ms) |
| `THINK_TIME_MAX` | `3000` | Tiempo máximo de espera (ms) |

## 📈 Métricas que se Miden

### 1. **Tiempos de Respuesta**
- **Response Time**: Tiempo total de respuesta
- **Connect Time**: Tiempo de conexión
- **Latency**: Latencia de red

### 2. **Throughput**
- **Requests per Second**: Peticiones por segundo
- **KB per Second**: Kilobytes por segundo

### 3. **Errores**
- **Error Rate**: Porcentaje de errores
- **Error Count**: Número total de errores

### 4. **Recursos del Sistema**
- **CPU Usage**: Uso de CPU
- **Memory Usage**: Uso de memoria
- **Database Connections**: Conexiones a base de datos

## 🎮 Cómo Ejecutar las Pruebas

### Paso 1: Abrir el Archivo JMeter
1. Abrir Apache JMeter
2. Ir a **File** → **Open**
3. Seleccionar `ferremasapp-performance-test.jmx`

### Paso 2: Verificar Configuración
1. Revisar las variables en **Test Plan** → **Variables de Usuario**
2. Asegurarse que `BASE_URL` apunte a `http://localhost:8082`
3. Verificar que la aplicación esté ejecutándose

### Paso 3: Ejecutar las Pruebas
1. Hacer clic en el botón **Start** (▶️) en la barra de herramientas
2. Observar los resultados en tiempo real en **View Results Tree**
3. Las pruebas se ejecutarán automáticamente

### Paso 4: Analizar Resultados
1. **Aggregate Report**: Resumen estadístico
2. **Summary Report**: Resumen general
3. **Graph Results**: Gráficos de rendimiento
4. **View Results Tree**: Detalles de cada petición

## 📊 Interpretación de Resultados

### Indicadores de Buen Rendimiento
- **Response Time < 2 segundos** para páginas web
- **Response Time < 500ms** para APIs
- **Error Rate < 1%**
- **Throughput estable** sin degradación

### Indicadores de Problemas
- **Response Time > 5 segundos**
- **Error Rate > 5%**
- **Throughput decreciente**
- **Timeouts frecuentes**

## 🔍 Análisis de Resultados

### 1. **View Results Tree**
- Ver detalles de cada petición
- Identificar errores específicos
- Analizar tiempos de respuesta individuales

### 2. **Aggregate Report**
- Estadísticas por endpoint
- Promedios de tiempo de respuesta
- Porcentajes de éxito/error

### 3. **Summary Report**
- Resumen general de la prueba
- Total de peticiones procesadas
- Tiempo total de ejecución

### 4. **Graph Results**
- Gráficos de rendimiento en tiempo real
- Visualización de patrones de carga
- Identificación de picos de uso

## 🛠️ Personalización de Pruebas

### Modificar Número de Usuarios
1. Seleccionar **Thread Group**
2. Cambiar **Number of Threads**
3. Ajustar **Ramp-up period**

### Agregar Nuevos Escenarios
1. Crear nuevo **Thread Group**
2. Agregar **HTTP Request** samplers
3. Configurar **Assertions** para validaciones
4. Agregar **Timers** para tiempos de espera

### Modificar Datos de Prueba
1. Usar **CSV Data Set Config** para datos dinámicos
2. Implementar **Random Variables** para valores aleatorios
3. Usar **User Defined Variables** para configuración

## 🚨 Solución de Problemas

### Error: "Connection refused"
- Verificar que la aplicación esté ejecutándose
- Confirmar el puerto correcto (8082)
- Revisar firewall/antivirus

### Error: "Timeout"
- Aumentar **Connect Timeout** en HTTP Request
- Verificar rendimiento del servidor
- Revisar logs de la aplicación

### Error: "Assertion failed"
- Verificar que las respuestas contengan el texto esperado
- Revisar el estado de la aplicación
- Ajustar las assertions según sea necesario

## 📝 Recomendaciones

### Antes de Ejecutar
1. **Limpiar logs** de la aplicación
2. **Reiniciar** la aplicación
3. **Verificar** conexión a base de datos
4. **Monitorear** recursos del sistema

### Durante la Ejecución
1. **Observar** logs de la aplicación
2. **Monitorear** uso de CPU y memoria
3. **Verificar** conexiones a base de datos
4. **Documentar** cualquier error

### Después de la Ejecución
1. **Guardar** resultados en archivo
2. **Analizar** métricas clave
3. **Identificar** cuellos de botella
4. **Documentar** hallazgos

## 📊 Ejemplo de Resultados Esperados

### Escenario: 10 Usuarios Navegando
- **Total Requests**: ~200
- **Average Response Time**: 800ms
- **Error Rate**: < 1%
- **Throughput**: 15 requests/second

### Escenario: 5 Usuarios Pagando
- **Total Requests**: ~30
- **Average Response Time**: 1200ms
- **Error Rate**: < 2%
- **Throughput**: 3 requests/second

### Escenario: 15 Usuarios Consultando APIs
- **Total Requests**: ~450
- **Average Response Time**: 300ms
- **Error Rate**: < 0.5%
- **Throughput**: 25 requests/second

## 🔗 Enlaces Útiles

- [Documentación oficial de JMeter](https://jmeter.apache.org/usermanual/index.html)
- [Guía de mejores prácticas](https://jmeter.apache.org/usermanual/best-practices.html)
- [Plugins recomendados](https://jmeter-plugins.org/)

---

**Nota**: Esta guía está diseñada específicamente para la aplicación FerremasApp. Ajusta los parámetros según las necesidades específicas de tu entorno y objetivos de rendimiento. 