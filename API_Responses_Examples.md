# Ejemplos de Respuestas de API - Sistema Ferremas

## Respuestas Exitosas y Errores Comunes por Endpoint

---

## 1. AUTENTICACIÓN

### 1.1 POST /api/auth/login

#### ✅ Respuesta Exitosa (200 OK)
```json
{
  "success": true,
  "message": "Login exitoso",
  "usuario": {
    "id": 1,
    "nombre": "Administrador",
    "email": "admin@ferremas.com",
    "rol": "ADMIN",
    "fechaRegistro": "2024-01-15T10:30:00"
  },
  "timestamp": "2024-01-15T10:30:00"
}
```

#### ❌ Credenciales Incorrectas (401 Unauthorized)
```json
{
  "success": false,
  "message": "Credenciales inválidas",
  "error": "AUTH_FAILED",
  "timestamp": "2024-01-15T10:30:00"
}
```

#### ❌ Usuario No Encontrado (404 Not Found)
```json
{
  "success": false,
  "message": "Usuario no encontrado",
  "error": "USER_NOT_FOUND",
  "timestamp": "2024-01-15T10:30:00"
}
```

#### ❌ Datos Inválidos (400 Bad Request)
```json
{
  "success": false,
  "message": "Datos de entrada inválidos",
  "errors": [
    {
      "field": "email",
      "message": "El email es obligatorio"
    },
    {
      "field": "password",
      "message": "La contraseña debe tener al menos 6 caracteres"
    }
  ],
  "timestamp": "2024-01-15T10:30:00"
}
```

---

## 2. GESTIÓN DE USUARIOS

### 2.1 POST /api/usuarios (Registro)

#### ✅ Respuesta Exitosa (201 Created)
```json
{
  "id": 2,
  "nombre": "Juan Pérez",
  "email": "juan@test.com",
  "rol": "CLIENTE",
  "fechaRegistro": "2024-01-15T10:30:00",
  "activo": true
}
```

#### ❌ Email Duplicado (400 Bad Request)
```json
{
  "success": false,
  "message": "El email ya está registrado",
  "error": "EMAIL_ALREADY_EXISTS",
  "timestamp": "2024-01-15T10:30:00"
}
```

### 2.2 GET /api/usuarios

#### ✅ Respuesta Exitosa (200 OK)
```json
[
  {
    "id": 1,
    "nombre": "Administrador",
    "email": "admin@ferremas.com",
    "rol": "ADMIN",
    "fechaRegistro": "2024-01-15T10:30:00",
    "activo": true
  },
  {
    "id": 2,
    "nombre": "Juan Pérez",
    "email": "juan@test.com",
    "rol": "CLIENTE",
    "fechaRegistro": "2024-01-15T10:30:00",
    "activo": true
  }
]
```

### 2.3 GET /api/usuarios/{id}

#### ✅ Respuesta Exitosa (200 OK)
```json
{
  "id": 1,
  "nombre": "Administrador",
  "email": "admin@ferremas.com",
  "rol": "ADMIN",
  "fechaRegistro": "2024-01-15T10:30:00",
  "activo": true
}
```

#### ❌ Usuario No Encontrado (404 Not Found)
```json
{
  "success": false,
  "message": "Usuario no encontrado",
  "error": "USER_NOT_FOUND",
  "timestamp": "2024-01-15T10:30:00"
}
```

---

## 3. GESTIÓN DE PRODUCTOS

### 3.1 GET /api/productos

#### ✅ Respuesta Exitosa (200 OK)
```json
[
  {
    "id": 1,
    "nombre": "Martillo Profesional",
    "descripcion": "Martillo de acero forjado para uso profesional",
    "precio": 15000,
    "categoria": "HERRAMIENTAS",
    "marca": "Stanley",
    "stock": 25,
    "imagen": "martillo.jpg",
    "activo": true
  },
  {
    "id": 2,
    "nombre": "Destornillador Phillips",
    "descripcion": "Destornillador profesional de alta calidad",
    "precio": 8500,
    "categoria": "HERRAMIENTAS",
    "marca": "DeWalt",
    "stock": 30,
    "imagen": "destornillador.jpg",
    "activo": true
  }
]
```

### 3.2 GET /api/productos/{id}

#### ✅ Respuesta Exitosa (200 OK)
```json
{
  "id": 1,
  "nombre": "Martillo Profesional",
  "descripcion": "Martillo de acero forjado para uso profesional",
  "precio": 15000,
  "categoria": "HERRAMIENTAS",
  "marca": "Stanley",
  "stock": 25,
  "imagen": "martillo.jpg",
  "activo": true,
  "fechaCreacion": "2024-01-15T10:30:00",
  "fechaActualizacion": "2024-01-15T10:30:00"
}
```

#### ❌ Producto No Encontrado (404 Not Found)
```json
{
  "success": false,
  "message": "Producto no encontrado",
  "error": "PRODUCT_NOT_FOUND",
  "timestamp": "2024-01-15T10:30:00"
}
```

### 3.3 POST /api/productos

#### ✅ Respuesta Exitosa (201 Created)
```json
{
  "id": 3,
  "nombre": "Taladro Profesional",
  "descripcion": "Taladro de alta potencia para uso profesional",
  "precio": 45000,
  "categoria": "HERRAMIENTAS",
  "marca": "DeWalt",
  "stock": 15,
  "imagen": "taladro.jpg",
  "activo": true,
  "fechaCreacion": "2024-01-15T10:30:00",
  "fechaActualizacion": "2024-01-15T10:30:00"
}
```

#### ❌ Datos Inválidos (400 Bad Request)
```json
{
  "success": false,
  "message": "Datos de entrada inválidos",
  "errors": [
    {
      "field": "nombre",
      "message": "El nombre es obligatorio"
    },
    {
      "field": "precio",
      "message": "El precio debe ser mayor a 0"
    },
    {
      "field": "stock",
      "message": "El stock debe ser mayor o igual a 0"
    }
  ],
  "timestamp": "2024-01-15T10:30:00"
}
```

### 3.4 PUT /api/productos/{id}

#### ✅ Respuesta Exitosa (200 OK)
```json
{
  "id": 1,
  "nombre": "Martillo Profesional Actualizado",
  "descripcion": "Martillo de acero forjado para uso profesional",
  "precio": 18000,
  "categoria": "HERRAMIENTAS",
  "marca": "Stanley",
  "stock": 30,
  "imagen": "martillo.jpg",
  "activo": true,
  "fechaCreacion": "2024-01-15T10:30:00",
  "fechaActualizacion": "2024-01-15T10:35:00"
}
```

---

## 4. GESTIÓN DE PEDIDOS

### 4.1 POST /api/pedidos

#### ✅ Respuesta Exitosa (201 Created)
```json
{
  "id": 1,
  "usuarioId": 1,
  "fecha": "2024-01-15T10:30:00",
  "estado": "PENDIENTE",
  "total": 30000,
  "productos": [
    {
      "productoId": 1,
      "nombre": "Martillo Profesional",
      "cantidad": 2,
      "precioUnitario": 15000,
      "subtotal": 30000
    }
  ],
  "direccionEnvio": {
    "calle": "Av. Providencia 123",
    "ciudad": "Santiago",
    "codigoPostal": "7500000"
  }
}
```

#### ❌ Stock Insuficiente (400 Bad Request)
```json
{
  "success": false,
  "message": "Stock insuficiente para el producto Martillo Profesional",
  "error": "INSUFFICIENT_STOCK",
  "productoId": 1,
  "stockDisponible": 25,
  "stockSolicitado": 30,
  "timestamp": "2024-01-15T10:30:00"
}
```

#### ❌ Carrito Vacío (400 Bad Request)
```json
{
  "success": false,
  "message": "El carrito no puede estar vacío",
  "error": "EMPTY_CART",
  "timestamp": "2024-01-15T10:30:00"
}
```

### 4.2 GET /api/pedidos/usuario/{usuarioId}

#### ✅ Respuesta Exitosa (200 OK)
```json
[
  {
    "id": 1,
    "fecha": "2024-01-15T10:30:00",
    "estado": "PENDIENTE",
    "total": 30000,
    "cantidadProductos": 2
  },
  {
    "id": 2,
    "fecha": "2024-01-14T15:20:00",
    "estado": "ENTREGADO",
    "total": 8500,
    "cantidadProductos": 1
  }
]
```

### 4.3 GET /api/pedidos/{id}

#### ✅ Respuesta Exitosa (200 OK)
```json
{
  "id": 1,
  "usuarioId": 1,
  "fecha": "2024-01-15T10:30:00",
  "estado": "PENDIENTE",
  "total": 30000,
  "productos": [
    {
      "productoId": 1,
      "nombre": "Martillo Profesional",
      "cantidad": 2,
      "precioUnitario": 15000,
      "subtotal": 30000
    }
  ],
  "direccionEnvio": {
    "calle": "Av. Providencia 123",
    "ciudad": "Santiago",
    "codigoPostal": "7500000"
  },
  "historialEstados": [
    {
      "estado": "PENDIENTE",
      "fecha": "2024-01-15T10:30:00"
    }
  ]
}
```

---

## 5. GESTIÓN DE STOCK

### 5.1 GET /api/productos/{id}/stock

#### ✅ Respuesta Exitosa (200 OK)
```json
{
  "productoId": 1,
  "nombre": "Martillo Profesional",
  "stockActual": 25,
  "stockMinimo": 5,
  "estado": "DISPONIBLE",
  "ultimaActualizacion": "2024-01-15T10:30:00"
}
```

#### ❌ Producto No Encontrado (404 Not Found)
```json
{
  "success": false,
  "message": "Producto no encontrado",
  "error": "PRODUCT_NOT_FOUND",
  "timestamp": "2024-01-15T10:30:00"
}
```

### 5.2 PUT /api/productos/{id}/stock

#### ✅ Respuesta Exitosa (200 OK)
```json
{
  "productoId": 1,
  "nombre": "Martillo Profesional",
  "stockAnterior": 25,
  "stockNuevo": 50,
  "fechaActualizacion": "2024-01-15T10:35:00"
}
```

---

## 6. SISTEMA DE PAGOS

### 6.1 POST /api/pagos

#### ✅ Respuesta Exitosa (201 Created)
```json
{
  "id": 1,
  "pedidoId": 1,
  "monto": 30000,
  "metodoPago": "WEBPAY",
  "estado": "PENDIENTE",
  "fechaCreacion": "2024-01-15T10:30:00",
  "urlPago": "https://webpay.transbank.cl/pay/123456789",
  "token": "abc123def456"
}
```

#### ❌ Pedido No Encontrado (404 Not Found)
```json
{
  "success": false,
  "message": "Pedido no encontrado",
  "error": "ORDER_NOT_FOUND",
  "timestamp": "2024-01-15T10:30:00"
}
```

### 6.2 GET /api/pagos/{id}

#### ✅ Respuesta Exitosa (200 OK)
```json
{
  "id": 1,
  "pedidoId": 1,
  "monto": 30000,
  "metodoPago": "WEBPAY",
  "estado": "APROBADO",
  "fechaCreacion": "2024-01-15T10:30:00",
  "fechaAprobacion": "2024-01-15T10:32:00",
  "codigoAutorizacion": "AUTH123456",
  "ultimosDigitos": "1234"
}
```

---

## 7. ERRORES COMUNES DEL SISTEMA

### 7.1 500 Internal Server Error
```json
{
  "success": false,
  "message": "Error interno del servidor",
  "error": "INTERNAL_SERVER_ERROR",
  "timestamp": "2024-01-15T10:30:00"
}
```

### 7.2 404 Not Found
```json
{
  "success": false,
  "message": "Recurso no encontrado",
  "error": "RESOURCE_NOT_FOUND",
  "timestamp": "2024-01-15T10:30:00"
}
```

### 7.3 405 Method Not Allowed
```json
{
  "success": false,
  "message": "Método HTTP no permitido",
  "error": "METHOD_NOT_ALLOWED",
  "timestamp": "2024-01-15T10:30:00"
}
```

### 7.4 415 Unsupported Media Type
```json
{
  "success": false,
  "message": "Tipo de contenido no soportado",
  "error": "UNSUPPORTED_MEDIA_TYPE",
  "timestamp": "2024-01-15T10:30:00"
}
```

### 7.5 422 Unprocessable Entity
```json
{
  "success": false,
  "message": "Entidad no procesable",
  "errors": [
    {
      "field": "email",
      "message": "Formato de email inválido"
    }
  ],
  "timestamp": "2024-01-15T10:30:00"
}
```

---

## 8. HEADERS DE RESPUESTA COMUNES

### 8.1 Headers de Éxito
```
HTTP/1.1 200 OK
Content-Type: application/json
Content-Length: 1234
Date: Mon, 15 Jan 2024 10:30:00 GMT
Server: Spring Boot
```

### 8.2 Headers de Creación
```
HTTP/1.1 201 Created
Content-Type: application/json
Location: /api/productos/3
Content-Length: 567
Date: Mon, 15 Jan 2024 10:30:00 GMT
Server: Spring Boot
```

### 8.3 Headers de Error
```
HTTP/1.1 400 Bad Request
Content-Type: application/json
Content-Length: 234
Date: Mon, 15 Jan 2024 10:30:00 GMT
Server: Spring Boot
```

---

## 9. CÓDIGOS DE ESTADO HTTP UTILIZADOS

| Código | Descripción | Uso |
|--------|-------------|-----|
| 200 | OK | Respuesta exitosa para GET, PUT, DELETE |
| 201 | Created | Recurso creado exitosamente (POST) |
| 400 | Bad Request | Datos de entrada inválidos |
| 401 | Unauthorized | Autenticación requerida |
| 403 | Forbidden | Acceso denegado |
| 404 | Not Found | Recurso no encontrado |
| 405 | Method Not Allowed | Método HTTP no soportado |
| 409 | Conflict | Conflicto de datos (ej: email duplicado) |
| 422 | Unprocessable Entity | Datos válidos pero no procesables |
| 500 | Internal Server Error | Error interno del servidor |

---

## 10. FORMATO DE FECHAS

Todas las fechas en la API siguen el formato ISO 8601:
```
YYYY-MM-DDTHH:mm:ss
```

Ejemplo: `2024-01-15T10:30:00`

---

**Documento preparado para**: ASY5131 - Integración de Plataformas  
**Sistema**: Ferremas  
**Versión**: 1.0 
