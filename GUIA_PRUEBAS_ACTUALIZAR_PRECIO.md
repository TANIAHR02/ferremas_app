# Guía de Pruebas - Actualización de Precios de Productos (PATCH API)

## Descripción General

Esta guía describe cómo probar los endpoints PATCH para actualizar precios de productos en la aplicación Ferremas. Los endpoints implementan un sistema de historial de precios que mantiene un registro completo de todos los cambios de precios.

**Configuración**: Puerto 8081

## Endpoints Disponibles

### 1. Actualizar Precio por ID
```
PATCH /api/productos/{id}/precio
```

### 2. Actualizar Precio por Código
```
PATCH /api/productos/codigo/{codigo}/precio
```

### 3. Obtener Historial de Precios por ID
```
GET /api/productos/{id}/historial-precios
```

### 4. Obtener Historial de Precios por Código
```
GET /api/productos/codigo/{codigo}/historial-precios
```

## Estructura del Request Body

```json
{
  "nuevoPrecio": 18990.00,
  "motivo": "Ajuste de precios por inflación",
  "usuario": "admin@ferremas.cl"
}
```

### Campos:
- **nuevoPrecio** (BigDecimal, requerido): El nuevo precio del producto
- **motivo** (String, opcional): Razón del cambio de precio
- **usuario** (String, opcional): Usuario que realiza el cambio

## Respuesta Exitosa (200 OK)

```json
{
  "success": true,
  "message": "Precio actualizado exitosamente",
  "producto": {
    "id": 1,
    "codigo": "MART001",
    "nombre": "Martillo Profesional"
  },
  "precioAnterior": 15990.00,
  "precioNuevo": 18990.00,
  "variacion": 3000.00,
  "motivo": "Ajuste de precios por inflación",
  "usuario": "admin@ferremas.cl",
  "fechaActualizacion": "2024-01-15T10:30:00"
}
```

## Respuestas de Error

### 400 Bad Request - Precio Inválido
```json
{
  "success": false,
  "message": "El precio debe ser mayor a 0",
  "error": "INVALID_PRICE"
}
```

### 404 Not Found - Producto No Encontrado
```json
{
  "success": false,
  "message": "Producto no encontrado",
  "error": "PRODUCT_NOT_FOUND"
}
```

### 500 Internal Server Error
```json
{
  "success": false,
  "message": "Error al actualizar el precio: [detalle del error]",
  "error": "UPDATE_ERROR"
}
```

## Casos de Prueba

### Caso 1: Actualización Exitosa por ID
**Endpoint**: `PATCH /api/productos/1/precio`
**Request**:
```json
{
  "nuevoPrecio": 18990.00,
  "motivo": "Ajuste de precios por inflación",
  "usuario": "admin@ferremas.cl"
}
```
**Resultado Esperado**: 200 OK con detalles de la actualización

### Caso 2: Actualización Exitosa por Código
**Endpoint**: `PATCH /api/productos/codigo/MART001/precio`
**Request**:
```json
{
  "nuevoPrecio": 15990.00,
  "motivo": "Promoción especial",
  "usuario": "gerente@ferremas.cl"
}
```
**Resultado Esperado**: 200 OK con detalles de la actualización

### Caso 3: Precio Negativo (Error)
**Endpoint**: `PATCH /api/productos/1/precio`
**Request**:
```json
{
  "nuevoPrecio": -100.00,
  "motivo": "Precio negativo inválido",
  "usuario": "test@ferremas.cl"
}
```
**Resultado Esperado**: 400 Bad Request

### Caso 4: Producto Inexistente (Error)
**Endpoint**: `PATCH /api/productos/999/precio`
**Request**:
```json
{
  "nuevoPrecio": 25000.00,
  "motivo": "Producto que no existe",
  "usuario": "test@ferremas.cl"
}
```
**Resultado Esperado**: 404 Not Found

### Caso 5: Precio con Decimales
**Endpoint**: `PATCH /api/productos/3/precio`
**Request**:
```json
{
  "nuevoPrecio": 89990.50,
  "motivo": "Ajuste de precios con decimales",
  "usuario": "finanzas@ferremas.cl"
}
```
**Resultado Esperado**: 200 OK

### Caso 6: Obtener Historial de Precios
**Endpoint**: `GET /api/productos/1/historial-precios`
**Resultado Esperado**: 200 OK con lista de precios históricos

## Métodos de Prueba

### 1. Usando Postman (Recomendado)
1. Importa el archivo `Ferremas-Actualizar-Precios.postman_collection.json`
2. La variable `baseUrl` ya está configurada para puerto 8081
3. Ejecuta las pruebas en el siguiente orden:
   - **Verificar Aplicación** (para confirmar que está ejecutándose)
   - **Actualizar Precio por ID**
   - **Actualizar Precio por Código**
   - **Precio Inválido (Error 400)**
   - **Producto Inexistente (Error 404)**
   - **Código Inexistente (Error 404)**
   - **Precio con Decimales**
   - **Actualizar Sin Motivo**
   - **Motivo Largo**
   - **Obtener Historial por ID**
   - **Obtener Historial por Código**

### 2. Usando el Archivo HTTP
1. Abre el archivo `pruebas-actualizar-precio.http` en VS Code
2. Instala la extensión "REST Client" si no la tienes
3. Ejecuta cada prueba haciendo clic en "Send Request"

### 3. Usando el Script Batch
1. Asegúrate de que la aplicación esté ejecutándose en puerto 8081
2. Ejecuta `test-actualizar-precio.bat`
3. Revisa los resultados en la consola

### 4. Usando cURL Manualmente
```bash
curl -X PATCH http://localhost:8081/api/productos/1/precio \
  -H "Content-Type: application/json" \
  -d '{"nuevoPrecio": 18990.00, "motivo": "Ajuste de precios", "usuario": "admin@ferremas.cl"}'
```

## Verificación de Resultados

### 1. Verificar Actualización Exitosa
- Status code: 200
- Campo `success`: true
- Campo `precioAnterior` y `precioNuevo` diferentes
- Campo `variacion` calculado correctamente

### 2. Verificar Historial de Precios
- El precio anterior se cierra con `fechaFin`
- Se crea un nuevo registro con `fechaInicio`
- El historial mantiene todos los cambios

### 3. Verificar Validaciones
- Precios negativos o cero devuelven 400
- Productos inexistentes devuelven 404
- Errores del servidor devuelven 500

## Características del Sistema

### Historial de Precios
- Mantiene registro completo de cambios
- Cierra automáticamente el precio anterior
- Crea nuevo registro con timestamp
- Permite auditoría completa

### Validaciones
- Precio debe ser mayor a 0
- Producto debe existir
- Manejo de errores robusto

### Respuestas Informativas
- Detalles completos del cambio
- Cálculo de variación
- Información del producto
- Timestamps de actualización

## Troubleshooting

### Error: "Producto no encontrado"
- Verificar que el ID o código existe
- Revisar la base de datos
- Confirmar que la aplicación está ejecutándose en puerto 8081

### Error: "El precio debe ser mayor a 0"
- Verificar el formato del JSON
- Asegurar que el precio es un número positivo
- Revisar que no hay caracteres especiales

### Error: "Error al actualizar el precio"
- Revisar logs de la aplicación
- Verificar conexión a la base de datos
- Confirmar que el servicio de historial funciona

### Error de Conexión
- Verificar que la aplicación esté ejecutándose en puerto 8081
- Confirmar que no hay firewall bloqueando el puerto
- Revisar logs de la aplicación para errores de inicio

## Notas Importantes

1. **Puerto 8081**: Todos los endpoints usan el puerto 8081
2. **Método PATCH**: Se usa específicamente para actualizaciones parciales
3. **Historial Automático**: Cada cambio genera un registro en el historial
4. **Validaciones**: El sistema valida tanto el precio como la existencia del producto
5. **Auditoría**: Todos los cambios quedan registrados con usuario y motivo
6. **Compatibilidad**: Los endpoints funcionan tanto con ID como con código de producto

## Próximos Pasos

1. Ejecutar las pruebas básicas
2. Verificar el historial de precios
3. Probar casos edge (valores límite)
4. Validar respuestas de error
5. Documentar cualquier problema encontrado 