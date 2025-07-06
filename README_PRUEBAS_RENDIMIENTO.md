# 🚀 Pruebas de Rendimiento - FerremasApp

## 📋 Resumen

Este directorio contiene todos los archivos necesarios para ejecutar pruebas de rendimiento completas en la aplicación FerremasApp utilizando Apache JMeter.

## 📁 Archivos Incluidos

| Archivo | Descripción |
|---------|-------------|
| `ferremasapp-performance-test.jmx` | Archivo principal de JMeter con todos los escenarios de prueba |
| `GUIA_PRUEBAS_RENDIMIENTO_JMETER.md` | Guía completa paso a paso para usar JMeter |
| `test-rendimiento-jmeter.bat` | Script automatizado para ejecutar las pruebas |
| `README_PRUEBAS_RENDIMIENTO.md` | Este archivo de documentación |

## ⚡ Ejecución Rápida

### Opción 1: Script Automatizado (Recomendado)
```bash
# Ejecutar el script batch
test-rendimiento-jmeter.bat
```

### Opción 2: Manual con JMeter GUI
```bash
# Abrir JMeter
jmeter.bat

# Luego: File → Open → ferremasapp-performance-test.jmx
```

### Opción 3: Línea de Comandos
```bash
# Ejecutar sin GUI (más rápido)
jmeter.bat -n -t ferremasapp-performance-test.jmx -l resultados.jtl -e -o reporte/
```

## 🎯 Escenarios de Prueba

### 1. **Navegación de Usuarios** 
- **10 usuarios concurrentes**
- **5 iteraciones por usuario**
- **Simula**: Usuarios navegando productos y usando el carrito

### 2. **Procesamiento de Pagos**
- **5 usuarios concurrentes** 
- **3 iteraciones por usuario**
- **Simula**: Flujo completo de pagos con WebPay

### 3. **Consultas de APIs**
- **15 usuarios concurrentes**
- **10 iteraciones por usuario**
- **Simula**: Carga en APIs críticas del sistema

## 📊 Métricas Medidas

- ✅ **Tiempo de respuesta** (Response Time)
- ✅ **Throughput** (Peticiones por segundo)
- ✅ **Tasa de errores** (Error Rate)
- ✅ **Latencia** (Connect Time)
- ✅ **Estabilidad** del sistema

## 🔧 Requisitos Previos

1. **Java 8+** instalado
2. **Apache JMeter 5.6.3+** instalado
3. **FerremasApp ejecutándose** en `http://localhost:8082`
4. **Base de datos** conectada y funcionando

## 📈 Resultados Esperados

### Indicadores de Buen Rendimiento
- **Páginas web**: < 2 segundos
- **APIs**: < 500ms
- **Tasa de errores**: < 1%
- **Throughput estable**: Sin degradación

### Indicadores de Problemas
- **Tiempo de respuesta**: > 5 segundos
- **Tasa de errores**: > 5%
- **Throughput decreciente**
- **Timeouts frecuentes**

## 🛠️ Personalización

### Modificar Carga de Usuarios
1. Abrir `ferremasapp-performance-test.jmx` en JMeter
2. Seleccionar **Thread Group**
3. Cambiar **Number of Threads**
4. Ajustar **Ramp-up period**

### Agregar Nuevos Endpoints
1. Crear nuevo **HTTP Request**
2. Configurar URL y método
3. Agregar **Assertions** para validaciones
4. Incluir **Timers** para tiempos realistas

## 📝 Pasos de Ejecución

### 1. Preparación
```bash
# Verificar que la app esté corriendo
curl http://localhost:8082/actuator/health
```

### 2. Ejecución
```bash
# Opción automática
test-rendimiento-jmeter.bat

# Opción manual
jmeter.bat -n -t ferremasapp-performance-test.jmx
```

### 3. Análisis
- Revisar **Aggregate Report**
- Analizar **Summary Report**
- Verificar **Graph Results**
- Documentar hallazgos

## 🚨 Solución de Problemas

### Error: "Connection refused"
```bash
# Verificar que la app esté ejecutándose
netstat -an | findstr :8082
```

### Error: "JMeter not found"
```bash
# Agregar JMeter al PATH o usar ruta completa
C:\apache-jmeter-5.6.3\bin\jmeter.bat
```

### Error: "Assertion failed"
- Verificar que las respuestas contengan el texto esperado
- Revisar el estado de la aplicación
- Ajustar assertions según sea necesario

## 📊 Ejemplo de Reporte

```
Test Results Summary:
====================
Total Requests: 680
Average Response Time: 847ms
Error Rate: 0.3%
Throughput: 18.2 requests/second
Min Response Time: 156ms
Max Response Time: 2.3s
```

