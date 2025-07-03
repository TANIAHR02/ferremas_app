# Comandos JMeter - Sistema Ferremas
## Ejecución desde Línea de Comandos

---

## 📋 Tabla de Contenidos

1. [Configuración del Entorno](#1-configuración-del-entorno)
2. [Comandos Básicos](#2-comandos-básicos)
3. [Ejecución de Pruebas](#3-ejecución-de-pruebas)
4. [Generación de Reportes](#4-generación-de-reportes)
5. [Scripts de Automatización](#5-scripts-de-automatización)
6. [Monitoreo y Logs](#6-monitoreo-y-logs)

---

## 1. Configuración del Entorno

### 1.1 Variables de Entorno

**Windows (CMD)**:
```cmd
set JMETER_HOME=C:\apache-jmeter-5.6.3
set PATH=%PATH%;%JMETER_HOME%\bin
set JAVA_OPTS=-Xms1g -Xmx4g -XX:MaxMetaspaceSize=256m
```

**Windows (PowerShell)**:
```powershell
$env:JMETER_HOME = "C:\apache-jmeter-5.6.3"
$env:PATH += ";$env:JMETER_HOME\bin"
$env:JAVA_OPTS = "-Xms1g -Xmx4g -XX:MaxMetaspaceSize=256m"
```

**Linux/Mac**:
```bash
export JMETER_HOME=/opt/apache-jmeter-5.6.3
export PATH=$PATH:$JMETER_HOME/bin
export JAVA_OPTS="-Xms1g -Xmx4g -XX:MaxMetaspaceSize=256m"
```

### 1.2 Verificar Instalación

```bash
# Verificar JMeter
jmeter -v

# Verificar Java
java -version

# Verificar variables de entorno
echo $JMETER_HOME
echo $JAVA_OPTS
```

---

## 2. Comandos Básicos

### 2.1 Comandos de Ayuda

```bash
# Ayuda general
jmeter -h

# Ayuda específica para opciones
jmeter -?

# Versión de JMeter
jmeter -v

# Listar plugins disponibles
jmeter -l
```

### 2.2 Comandos de Validación

```bash
# Validar archivo JMX sin ejecutar
jmeter -t ferremas-performance-test.jmx -l validation.log

# Validar y mostrar configuración
jmeter -t ferremas-performance-test.jmx --logfile validation.log --nongui
```

---

## 3. Ejecución de Pruebas

### 3.1 Ejecución Básica

**Comando básico**:
```bash
jmeter -t ferremas-performance-test.jmx -l results.jtl
```

**Con interfaz gráfica**:
```bash
jmeter -t ferremas-performance-test.jmx -l results.jtl -e -o report/
```

**Sin interfaz gráfica (modo servidor)**:
```bash
jmeter -t ferremas-performance-test.jmx -l results.jtl -n
```

### 3.2 Ejecución con Parámetros Específicos

**Ejecutar Thread Group específico**:
```bash
jmeter -t ferremas-performance-test.jmx -l results.jtl -Jthreadgroup="10 Users - Login Test"
```

**Ejecutar con propiedades personalizadas**:
```bash
jmeter -t ferremas-performance-test.jmx -l results.jtl \
  -Jbase_url=http://localhost:8080 \
  -Jtest_user_email=admin@ferremas.com \
  -Jtest_user_password=admin123
```

**Ejecutar con archivo de propiedades**:
```bash
jmeter -t ferremas-performance-test.jmx -l results.jtl -p jmeter.properties
```

### 3.3 Ejecución Distribuida

**Configurar servidores remotos**:
```bash
# En el servidor maestro
jmeter -t ferremas-performance-test.jmx -l results.jtl -R 192.168.1.100,192.168.1.101

# En servidores esclavos
jmeter-server -Djava.rmi.server.hostname=192.168.1.100
```

**Ejecutar con múltiples servidores**:
```bash
jmeter -t ferremas-performance-test.jmx -l results.jtl \
  -R 192.168.1.100:1099,192.168.1.101:1099 \
  -Gthreads=50 \
  -Gduration=300
```

---

## 4. Generación de Reportes

### 4.1 Reporte HTML Automático

**Generar reporte HTML**:
```bash
jmeter -t ferremas-performance-test.jmx -l results.jtl -e -o report/
```

**Con opciones adicionales**:
```bash
jmeter -t ferremas-performance-test.jmx \
  -l results.jtl \
  -e \
  -o report/ \
  -Jjmeter.reportgenerator.report_title="Ferremas Performance Test" \
  -Jjmeter.reportgenerator.overall_granularity=1000
```

### 4.2 Reporte Personalizado

**Generar reporte con configuración específica**:
```bash
jmeter -t ferremas-performance-test.jmx \
  -l results.jtl \
  -e \
  -o report/ \
  -Jjmeter.reportgenerator.report_title="Ferremas API Performance" \
  -Jjmeter.reportgenerator.graph.responseTimesDistribution.title="Response Time Distribution" \
  -Jjmeter.reportgenerator.graph.responseTimesDistribution.property_granularity=1000
```

### 4.3 Reporte CSV

**Exportar resultados a CSV**:
```bash
jmeter -t ferremas-performance-test.jmx -l results.csv -f
```

**Convertir JTL a CSV**:
```bash
jmeter -g results.jtl -o report/
```

---

## 5. Scripts de Automatización

### 5.1 Script Windows (batch)

**Crear archivo `run_tests.bat`**:
```batch
@echo off
echo ========================================
echo    PRUEBAS DE RENDIMIENTO FERREMAS
echo ========================================

REM Configurar variables
set JMETER_HOME=C:\apache-jmeter-5.6.3
set PATH=%PATH%;%JMETER_HOME%\bin
set JAVA_OPTS=-Xms1g -Xmx4g -XX:MaxMetaspaceSize=256m

REM Crear directorio de resultados
set TIMESTAMP=%date:~-4,4%%date:~-10,2%%date:~-7,2%_%time:~0,2%%time:~3,2%%time:~6,2%
set TIMESTAMP=%TIMESTAMP: =0%
set RESULTS_DIR=results_%TIMESTAMP%
mkdir %RESULTS_DIR%

echo Iniciando pruebas...
echo Timestamp: %TIMESTAMP%
echo Directorio de resultados: %RESULTS_DIR%

REM Ejecutar pruebas
jmeter -t ferremas-performance-test.jmx ^
  -l %RESULTS_DIR%\results.jtl ^
  -e ^
  -o %RESULTS_DIR%\report ^
  -Jbase_url=http://localhost:8080 ^
  -Jtest_user_email=admin@ferremas.com ^
  -Jtest_user_password=admin123

echo.
echo ========================================
echo    PRUEBAS COMPLETADAS
echo ========================================
echo Resultados guardados en: %RESULTS_DIR%
echo Reporte HTML: %RESULTS_DIR%\report\index.html
pause
```

### 5.2 Script Linux/Mac (bash)

**Crear archivo `run_tests.sh`**:
```bash
#!/bin/bash

echo "========================================"
echo "   PRUEBAS DE RENDIMIENTO FERREMAS"
echo "========================================"

# Configurar variables
export JMETER_HOME=/opt/apache-jmeter-5.6.3
export PATH=$PATH:$JMETER_HOME/bin
export JAVA_OPTS="-Xms1g -Xmx4g -XX:MaxMetaspaceSize=256m"

# Crear directorio de resultados
TIMESTAMP=$(date +%Y%m%d_%H%M%S)
RESULTS_DIR="results_${TIMESTAMP}"
mkdir -p $RESULTS_DIR

echo "Iniciando pruebas..."
echo "Timestamp: $TIMESTAMP"
echo "Directorio de resultados: $RESULTS_DIR"

# Verificar que la aplicación esté ejecutándose
echo "Verificando que la aplicación esté ejecutándose..."
if ! curl -s http://localhost:8080/api/productos > /dev/null; then
    echo "ERROR: La aplicación no está ejecutándose en http://localhost:8080"
    exit 1
fi

# Ejecutar pruebas
jmeter -t ferremas-performance-test.jmx \
  -l $RESULTS_DIR/results.jtl \
  -e \
  -o $RESULTS_DIR/report \
  -Jbase_url=http://localhost:8080 \
  -Jtest_user_email=admin@ferremas.com \
  -Jtest_user_password=admin123

echo ""
echo "========================================"
echo "   PRUEBAS COMPLETADAS"
echo "========================================"
echo "Resultados guardados en: $RESULTS_DIR"
echo "Reporte HTML: $RESULTS_DIR/report/index.html"
```

### 5.3 Script de Ejecución Gradual

**Crear archivo `run_gradual_tests.sh`**:
```bash
#!/bin/bash

echo "Ejecutando pruebas graduales de rendimiento..."

# Configuración
JMX_FILE="ferremas-performance-test.jmx"
BASE_URL="http://localhost:8080"

# Función para ejecutar prueba
run_test() {
    local users=$1
    local duration=$2
    local name=$3
    
    echo "Ejecutando prueba: $name ($users usuarios, $duration segundos)"
    
    jmeter -t $JMX_FILE \
      -l "results_${name}.jtl" \
      -e \
      -o "report_${name}" \
      -Jbase_url=$BASE_URL \
      -Jthreads=$users \
      -Jduration=$duration \
      -n
}

# Ejecutar pruebas graduales
run_test 10 300 "10_users_5min"
run_test 50 600 "50_users_10min"
run_test 100 900 "100_users_15min"

echo "Todas las pruebas completadas"
```

---

## 6. Monitoreo y Logs

### 6.1 Configuración de Logs

**Ejecutar con logs detallados**:
```bash
jmeter -t ferremas-performance-test.jmx \
  -l results.jtl \
  -L DEBUG \
  --logfile jmeter.log
```

**Niveles de log disponibles**:
```bash
# Solo errores
jmeter -L ERROR

# Información básica
jmeter -L INFO

# Debug completo
jmeter -L DEBUG

# Log personalizado
jmeter -L com.example=DEBUG
```

### 6.2 Monitoreo en Tiempo Real

**Ejecutar con monitoreo**:
```bash
# En una terminal
jmeter -t ferremas-performance-test.jmx -l results.jtl -n

# En otra terminal, monitorear logs
tail -f jmeter.log

# Monitorear uso de recursos
top -p $(pgrep java)
```

### 6.3 Análisis de Logs

**Filtrar errores**:
```bash
grep "ERROR" jmeter.log
grep "Exception" jmeter.log
grep "Failed" jmeter.log
```

**Análisis de rendimiento**:
```bash
# Contar requests exitosos
grep "200" results.jtl | wc -l

# Contar errores
grep "500\|400\|404" results.jtl | wc -l

# Tiempo promedio de respuesta
awk -F',' '{sum+=$1; count++} END {print "Tiempo promedio:", sum/count "ms"}' results.jtl
```

---

## 7. Comandos Específicos para Ferremas

### 7.1 Prueba de Login

```bash
# Prueba específica de login
jmeter -t ferremas-performance-test.jmx \
  -l login_results.jtl \
  -Jthreadgroup="10 Users - Login Test" \
  -Jbase_url=http://localhost:8080 \
  -n
```

### 7.2 Prueba de Productos

```bash
# Prueba específica de productos
jmeter -t ferremas-performance-test.jmx \
  -l productos_results.jtl \
  -Jthreadgroup="50 Users - Products Test" \
  -Jbase_url=http://localhost:8080 \
  -n
```

### 7.3 Prueba de Pedidos

```bash
# Prueba específica de pedidos
jmeter -t ferremas-performance-test.jmx \
  -l pedidos_results.jtl \
  -Jthreadgroup="100 Users - Orders Test" \
  -Jbase_url=http://localhost:8080 \
  -n
```

### 7.4 Prueba Completa

```bash
# Ejecutar todas las pruebas
jmeter -t ferremas-performance-test.jmx \
  -l ferremas_complete_results.jtl \
  -e \
  -o ferremas_report \
  -Jbase_url=http://localhost:8080 \
  -Jtest_user_email=admin@ferremas.com \
  -Jtest_user_password=admin123 \
  -n
```

---

## 8. Optimización de Comandos

### 8.1 Configuración de Memoria

**Para pruebas con muchos usuarios**:
```bash
export JAVA_OPTS="-Xms2g -Xmx8g -XX:MaxMetaspaceSize=512m"
jmeter -t ferremas-performance-test.jmx -l results.jtl -n
```

**Para pruebas de desarrollo**:
```bash
export JAVA_OPTS="-Xms512m -Xmx2g -XX:MaxMetaspaceSize=256m"
jmeter -t ferremas-performance-test.jmx -l results.jtl -n
```

### 8.2 Configuración de Red

**Optimizar para red lenta**:
```bash
jmeter -t ferremas-performance-test.jmx \
  -l results.jtl \
  -Jhttpclient.timeout=30000 \
  -Jhttpclient.connect_timeout=10000 \
  -n
```

**Optimizar para red rápida**:
```bash
jmeter -t ferremas-performance-test.jmx \
  -l results.jtl \
  -Jhttpclient.timeout=5000 \
  -Jhttpclient.connect_timeout=2000 \
  -n
```

---

## 9. Troubleshooting

### 9.1 Problemas Comunes

**Error: OutOfMemoryError**
```bash
# Aumentar memoria
export JAVA_OPTS="-Xms2g -Xmx8g -XX:MaxMetaspaceSize=512m"
```

**Error: Connection Refused**
```bash
# Verificar que la aplicación esté ejecutándose
curl http://localhost:8080/api/productos
```

**Error: SSL/TLS**
```bash
# Deshabilitar SSL para pruebas locales
jmeter -t ferremas-performance-test.jmx \
  -l results.jtl \
  -Jhttps.use.cached.ssl.context=false \
  -n
```

### 9.2 Comandos de Diagnóstico

**Verificar configuración**:
```bash
jmeter -t ferremas-performance-test.jmx --testfile --nongui
```

**Validar archivo JMX**:
```bash
jmeter -t ferremas-performance-test.jmx --logfile validation.log --nongui
```

**Verificar plugins**:
```bash
jmeter --version
```

---

## 10. Ejemplos de Uso Completo

### 10.1 Ejecución Básica Completa

```bash
#!/bin/bash
# Script completo de ejecución

echo "Iniciando pruebas de rendimiento Ferremas..."

# Configuración
JMX_FILE="ferremas-performance-test.jmx"
BASE_URL="http://localhost:8080"
TIMESTAMP=$(date +%Y%m%d_%H%M%S)
RESULTS_DIR="results_${TIMESTAMP}"

# Crear directorio
mkdir -p $RESULTS_DIR

# Verificar aplicación
echo "Verificando aplicación..."
if ! curl -s $BASE_URL/api/productos > /dev/null; then
    echo "ERROR: Aplicación no disponible"
    exit 1
fi

# Ejecutar pruebas
echo "Ejecutando pruebas..."
jmeter -t $JMX_FILE \
  -l $RESULTS_DIR/results.jtl \
  -e \
  -o $RESULTS_DIR/report \
  -Jbase_url=$BASE_URL \
  -Jtest_user_email=admin@ferremas.com \
  -Jtest_user_password=admin123 \
  -n

# Análisis básico
echo "Analizando resultados..."
echo "Requests totales: $(grep -c "200" $RESULTS_DIR/results.jtl)"
echo "Errores: $(grep -c "500\|400\|404" $RESULTS_DIR/results.jtl)"

echo "Pruebas completadas. Resultados en: $RESULTS_DIR"
```

### 10.2 Ejecución con Monitoreo

```bash
#!/bin/bash
# Script con monitoreo en tiempo real

echo "Ejecutando pruebas con monitoreo..."

# Iniciar monitoreo en background
(
    while true; do
        echo "$(date): CPU: $(top -bn1 | grep "Cpu(s)" | awk '{print $2}')%, Mem: $(free -m | awk 'NR==2{printf "%.1f%%", $3*100/$2}')"
        sleep 5
    done
) > monitoring.log &

MONITOR_PID=$!

# Ejecutar pruebas
jmeter -t ferremas-performance-test.jmx \
  -l results.jtl \
  -e \
  -o report \
  -n

# Detener monitoreo
kill $MONITOR_PID

echo "Monitoreo guardado en: monitoring.log"
echo "Pruebas completadas"
```

---

**Documento preparado para**: ASY5131 - Integración de Plataformas  
**Sistema**: Ferremas  
**Herramienta**: Apache JMeter 5.6.3  
**Versión**: 1.0 
