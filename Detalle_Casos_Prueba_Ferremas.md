# Detalle de Casos de Prueba - Sistema Ferremas
## ASY5131 - Integración de Plataformas

---

## 📋 Tabla de Contenidos

1. [Información General](#1-información-general)
2. [Casos de Prueba de Autenticación](#2-casos-de-prueba-de-autenticación)
3. [Casos de Prueba de Gestión de Productos](#3-casos-de-prueba-de-gestión-de-productos)
4. [Casos de Prueba de Gestión de Pedidos](#4-casos-de-prueba-de-gestión-de-pedidos)
5. [Casos de Prueba de Gestión de Usuarios](#5-casos-de-prueba-de-gestión-de-usuarios)
6. [Casos de Prueba de Control de Stock](#6-casos-de-prueba-de-control-de-stock)
7. [Casos de Prueba de Sistema de Pagos](#7-casos-de-prueba-de-sistema-de-pagos)
8. [Casos de Prueba de Interfaz Web](#8-casos-de-prueba-de-interfaz-web)
9. [Casos de Prueba de Rendimiento](#9-casos-de-prueba-de-rendimiento)
10. [Casos de Prueba de Seguridad](#10-casos-de-prueba-de-seguridad)

---

## 1. Información General

### 1.1 Datos de Prueba

**Usuarios de Prueba**:
| Email | Contraseña | Rol | Estado |
|-------|------------|-----|--------|
| admin@ferremas.com | admin123 | ADMIN | Activo |
| cliente1@test.com | cliente123 | CLIENTE | Activo |
| cliente2@test.com | cliente123 | CLIENTE | Activo |
| test@example.com | test123 | CLIENTE | Activo |

**Productos de Prueba**:
| ID | Nombre | Precio | Stock | Categoría | Marca |
|----|--------|--------|-------|-----------|-------|
| 1 | Martillo Profesional | 15000 | 25 | HERRAMIENTAS | Stanley |
| 2 | Destornillador Phillips | 8500 | 30 | HERRAMIENTAS | DeWalt |
| 3 | Taladro Eléctrico | 45000 | 15 | HERRAMIENTAS | Bosch |
| 4 | Sierra Circular | 75000 | 8 | HERRAMIENTAS | Makita |
| 5 | Lijadora Orbital | 35000 | 12 | HERRAMIENTAS | Black & Decker |

### 1.2 Configuración del Entorno

**URL Base**: `http://localhost:8080`
**Base de Datos**: H2 (embebida)
**Herramientas de Prueba**: 
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- Postman
- curl
- JMeter

---

## 2. Casos de Prueba de Autenticación

### TC-001: Registro de Usuario Exitoso

**Prioridad**: Alta
**Tipo**: Funcional
**Módulo**: Autenticación

**Precondiciones**:
- Sistema disponible y accesible
- Email no registrado previamente

**Pasos de Ejecución**:
1. Acceder al endpoint `POST /api/usuarios`
2. Enviar datos de registro:
   ```json
   {
     "nombre": "Juan Pérez",
     "email": "juan.perez@test.com",
     "password": "juan123",
     "rol": "CLIENTE"
   }
   ```
3. Verificar respuesta del sistema

**Datos de Entrada**:
- Nombre: "Juan Pérez"
- Email: "juan.perez@test.com"
- Contraseña: "juan123"
- Rol: "CLIENTE"

**Resultado Esperado**:
- **Status Code**: 201 Created
- **Response Body**:
  ```json
  {
    "id": 5,
    "nombre": "Juan Pérez",
    "email": "juan.perez@test.com",
    "rol": "CLIENTE",
    "fechaRegistro": "2024-01-15T10:30:00",
    "activo": true
  }
  ```

**Criterios de Aceptación**:
- Usuario creado exitosamente
- ID asignado automáticamente
- Fecha de registro establecida
- Estado activo por defecto

---

### TC-002: Registro con Email Duplicado

**Prioridad**: Alta
**Tipo**: Funcional
**Módulo**: Autenticación

**Precondiciones**:
- Usuario con email "admin@ferremas.com" ya existe

**Pasos de Ejecución**:
1. Acceder al endpoint `POST /api/usuarios`
2. Enviar datos con email existente:
   ```json
   {
     "nombre": "Otro Usuario",
     "email": "admin@ferremas.com",
     "password": "password123",
     "rol": "CLIENTE"
   }
   ```
3. Verificar respuesta del sistema

**Datos de Entrada**:
- Nombre: "Otro Usuario"
- Email: "admin@ferremas.com" (ya existe)
- Contraseña: "password123"
- Rol: "CLIENTE"

**Resultado Esperado**:
- **Status Code**: 400 Bad Request
- **Response Body**:
  ```json
  {
    "success": false,
    "message": "El email ya está registrado",
    "error": "EMAIL_ALREADY_EXISTS",
    "timestamp": "2024-01-15T10:30:00"
  }
  ```

**Criterios de Aceptación**:
- Sistema rechaza el registro
- Mensaje de error claro
- No se crea usuario duplicado

---

### TC-003: Login Exitoso

**Prioridad**: Alta
**Tipo**: Funcional
**Módulo**: Autenticación

**Precondiciones**:
- Usuario "admin@ferremas.com" existe en el sistema

**Pasos de Ejecución**:
1. Acceder al endpoint `POST /api/auth/login`
2. Enviar credenciales válidas:
   ```json
   {
     "email": "admin@ferremas.com",
     "password": "admin123"
   }
   ```
3. Verificar respuesta del sistema

**Datos de Entrada**:
- Email: "admin@ferremas.com"
- Contraseña: "admin123"

**Resultado Esperado**:
- **Status Code**: 200 OK
- **Response Body**:
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

**Criterios de Aceptación**:
- Autenticación exitosa
- Datos del usuario devueltos
- Rol correcto asignado

---

### TC-004: Login con Credenciales Incorrectas

**Prioridad**: Alta
**Tipo**: Funcional
**Módulo**: Autenticación

**Precondiciones**:
- Usuario "admin@ferremas.com" existe en el sistema

**Pasos de Ejecución**:
1. Acceder al endpoint `POST /api/auth/login`
2. Enviar credenciales incorrectas:
   ```json
   {
     "email": "admin@ferremas.com",
     "password": "password_incorrecta"
   }
   ```
3. Verificar respuesta del sistema

**Datos de Entrada**:
- Email: "admin@ferremas.com"
- Contraseña: "password_incorrecta"

**Resultado Esperado**:
- **Status Code**: 401 Unauthorized
- **Response Body**:
  ```json
  {
    "success": false,
    "message": "Credenciales inválidas",
    "error": "AUTH_FAILED",
    "timestamp": "2024-01-15T10:30:00"
  }
  ```

**Criterios de Aceptación**:
- Sistema rechaza la autenticación
- Mensaje de error apropiado
- No se genera sesión

---

## 3. Casos de Prueba de Gestión de Productos

### TC-005: Listar Todos los Productos

**Prioridad**: Alta
**Tipo**: Funcional
**Módulo**: Productos

**Precondiciones**:
- Sistema con productos cargados
- Endpoint accesible

**Pasos de Ejecución**:
1. Acceder al endpoint `GET /api/productos`
2. Verificar respuesta del sistema

**Datos de Entrada**:
- Ninguno (request GET sin parámetros)

**Resultado Esperado**:
- **Status Code**: 200 OK
- **Response Body**:
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

**Criterios de Aceptación**:
- Lista completa de productos
- Solo productos activos
- Información completa de cada producto

---

### TC-006: Obtener Producto por ID

**Prioridad**: Alta
**Tipo**: Funcional
**Módulo**: Productos

**Precondiciones**:
- Producto con ID 1 existe en el sistema

**Pasos de Ejecución**:
1. Acceder al endpoint `GET /api/productos/1`
2. Verificar respuesta del sistema

**Datos de Entrada**:
- ID del producto: 1

**Resultado Esperado**:
- **Status Code**: 200 OK
- **Response Body**:
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

**Criterios de Aceptación**:
- Producto encontrado correctamente
- Información completa del producto
- Fechas de creación y actualización incluidas

---

### TC-007: Crear Nuevo Producto

**Prioridad**: Alta
**Tipo**: Funcional
**Módulo**: Productos

**Precondiciones**:
- Usuario autenticado con rol ADMIN
- Categorías y marcas válidas

**Pasos de Ejecución**:
1. Acceder al endpoint `POST /api/productos`
2. Enviar datos del nuevo producto:
   ```json
   {
     "nombre": "Sierra de Mesa",
     "descripcion": "Sierra de mesa profesional para cortes precisos",
     "precio": 120000,
     "categoria": "HERRAMIENTAS",
     "marca": "DeWalt",
     "stock": 5
   }
   ```
3. Verificar respuesta del sistema

**Datos de Entrada**:
- Nombre: "Sierra de Mesa"
- Descripción: "Sierra de mesa profesional para cortes precisos"
- Precio: 120000
- Categoría: "HERRAMIENTAS"
- Marca: "DeWalt"
- Stock: 5

**Resultado Esperado**:
- **Status Code**: 201 Created
- **Response Body**:
  ```json
  {
    "id": 6,
    "nombre": "Sierra de Mesa",
    "descripcion": "Sierra de mesa profesional para cortes precisos",
    "precio": 120000,
    "categoria": "HERRAMIENTAS",
    "marca": "DeWalt",
    "stock": 5,
    "imagen": null,
    "activo": true,
    "fechaCreacion": "2024-01-15T10:35:00",
    "fechaActualizacion": "2024-01-15T10:35:00"
  }
  ```

**Criterios de Aceptación**:
- Producto creado exitosamente
- ID asignado automáticamente
- Fechas de creación establecidas
- Estado activo por defecto

---

### TC-008: Actualizar Producto Existente

**Prioridad**: Media
**Tipo**: Funcional
**Módulo**: Productos

**Precondiciones**:
- Producto con ID 1 existe en el sistema
- Usuario autenticado con rol ADMIN

**Pasos de Ejecución**:
1. Acceder al endpoint `PUT /api/productos/1`
2. Enviar datos de actualización:
   ```json
   {
     "nombre": "Martillo Profesional Actualizado",
     "precio": 18000,
     "stock": 30
   }
   ```
3. Verificar respuesta del sistema

**Datos de Entrada**:
- ID del producto: 1
- Nombre: "Martillo Profesional Actualizado"
- Precio: 18000
- Stock: 30

**Resultado Esperado**:
- **Status Code**: 200 OK
- **Response Body**:
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
    "fechaActualizacion": "2024-01-15T10:40:00"
  }
  ```

**Criterios de Aceptación**:
- Producto actualizado correctamente
- Solo campos especificados modificados
- Fecha de actualización actualizada

---

### TC-009: Obtener Producto Inexistente

**Prioridad**: Media
**Tipo**: Funcional
**Módulo**: Productos

**Precondiciones**:
- Producto con ID 999 no existe en el sistema

**Pasos de Ejecución**:
1. Acceder al endpoint `GET /api/productos/999`
2. Verificar respuesta del sistema

**Datos de Entrada**:
- ID del producto: 999 (inexistente)

**Resultado Esperado**:
- **Status Code**: 404 Not Found
- **Response Body**:
  ```json
  {
    "success": false,
    "message": "Producto no encontrado",
    "error": "PRODUCT_NOT_FOUND",
    "timestamp": "2024-01-15T10:30:00"
  }
  ```

**Criterios de Aceptación**:
- Sistema devuelve error 404
- Mensaje de error claro
- No se devuelve información del producto

---

## 4. Casos de Prueba de Gestión de Pedidos

### TC-010: Crear Pedido Exitoso

**Prioridad**: Alta
**Tipo**: Funcional
**Módulo**: Pedidos

**Precondiciones**:
- Usuario con ID 1 existe
- Productos con stock suficiente disponibles

**Pasos de Ejecución**:
1. Acceder al endpoint `POST /api/pedidos`
2. Enviar datos del pedido:
   ```json
   {
     "usuarioId": 1,
     "productos": [
       {
         "productoId": 1,
         "cantidad": 2
       },
       {
         "productoId": 2,
         "cantidad": 1
       }
     ]
   }
   ```
3. Verificar respuesta del sistema

**Datos de Entrada**:
- Usuario ID: 1
- Productos: [{"productoId": 1, "cantidad": 2}, {"productoId": 2, "cantidad": 1}]

**Resultado Esperado**:
- **Status Code**: 201 Created
- **Response Body**:
  ```json
  {
    "id": 1,
    "usuarioId": 1,
    "fecha": "2024-01-15T10:30:00",
    "estado": "PENDIENTE",
    "total": 38500,
    "productos": [
      {
        "productoId": 1,
        "nombre": "Martillo Profesional",
        "cantidad": 2,
        "precioUnitario": 15000,
        "subtotal": 30000
      },
      {
        "productoId": 2,
        "nombre": "Destornillador Phillips",
        "cantidad": 1,
        "precioUnitario": 8500,
        "subtotal": 8500
      }
    ]
  }
  ```

**Criterios de Aceptación**:
- Pedido creado exitosamente
- Total calculado correctamente
- Stock reservado automáticamente
- Estado inicial "PENDIENTE"

---

### TC-011: Crear Pedido con Stock Insuficiente

**Prioridad**: Alta
**Tipo**: Funcional
**Módulo**: Pedidos

**Precondiciones**:
- Producto con ID 1 tiene stock de 25 unidades

**Pasos de Ejecución**:
1. Acceder al endpoint `POST /api/pedidos`
2. Enviar pedido con cantidad mayor al stock:
   ```json
   {
     "usuarioId": 1,
     "productos": [
       {
         "productoId": 1,
         "cantidad": 30
       }
     ]
   }
   ```
3. Verificar respuesta del sistema

**Datos de Entrada**:
- Usuario ID: 1
- Productos: [{"productoId": 1, "cantidad": 30}]

**Resultado Esperado**:
- **Status Code**: 400 Bad Request
- **Response Body**:
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

**Criterios de Aceptación**:
- Sistema rechaza el pedido
- Mensaje de error específico
- Información de stock disponible
- No se crea el pedido

---

### TC-012: Listar Pedidos de Usuario

**Prioridad**: Media
**Tipo**: Funcional
**Módulo**: Pedidos

**Precondiciones**:
- Usuario con ID 1 tiene pedidos en el sistema

**Pasos de Ejecución**:
1. Acceder al endpoint `GET /api/pedidos/usuario/1`
2. Verificar respuesta del sistema

**Datos de Entrada**:
- ID del usuario: 1

**Resultado Esperado**:
- **Status Code**: 200 OK
- **Response Body**:
  ```json
  [
    {
      "id": 1,
      "fecha": "2024-01-15T10:30:00",
      "estado": "PENDIENTE",
      "total": 38500,
      "cantidadProductos": 3
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

**Criterios de Aceptación**:
- Lista de pedidos del usuario
- Información resumida de cada pedido
- Ordenados por fecha (más reciente primero)

---

## 5. Casos de Prueba de Gestión de Usuarios

### TC-013: Listar Todos los Usuarios

**Prioridad**: Media
**Tipo**: Funcional
**Módulo**: Usuarios

**Precondiciones**:
- Sistema con usuarios registrados
- Usuario autenticado con rol ADMIN

**Pasos de Ejecución**:
1. Acceder al endpoint `GET /api/usuarios`
2. Verificar respuesta del sistema

**Datos de Entrada**:
- Ninguno (request GET sin parámetros)

**Resultado Esperado**:
- **Status Code**: 200 OK
- **Response Body**:
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
      "nombre": "Cliente 1",
      "email": "cliente1@test.com",
      "rol": "CLIENTE",
      "fechaRegistro": "2024-01-15T10:30:00",
      "activo": true
    }
  ]
  ```

**Criterios de Aceptación**:
- Lista completa de usuarios
- Información básica de cada usuario
- Solo usuarios activos

---

### TC-014: Obtener Usuario por ID

**Prioridad**: Media
**Tipo**: Funcional
**Módulo**: Usuarios

**Precondiciones**:
- Usuario con ID 1 existe en el sistema

**Pasos de Ejecución**:
1. Acceder al endpoint `GET /api/usuarios/1`
2. Verificar respuesta del sistema

**Datos de Entrada**:
- ID del usuario: 1

**Resultado Esperado**:
- **Status Code**: 200 OK
- **Response Body**:
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

**Criterios de Aceptación**:
- Usuario encontrado correctamente
- Información completa del usuario
- Datos sensibles no expuestos

---

### TC-015: Actualizar Perfil de Usuario

**Prioridad**: Media
**Tipo**: Funcional
**Módulo**: Usuarios

**Precondiciones**:
- Usuario con ID 1 existe en el sistema
- Usuario autenticado

**Pasos de Ejecución**:
1. Acceder al endpoint `PUT /api/usuarios/1`
2. Enviar datos de actualización:
   ```json
   {
     "nombre": "Administrador Actualizado",
     "email": "admin.updated@ferremas.com"
   }
   ```
3. Verificar respuesta del sistema

**Datos de Entrada**:
- ID del usuario: 1
- Nombre: "Administrador Actualizado"
- Email: "admin.updated@ferremas.com"

**Resultado Esperado**:
- **Status Code**: 200 OK
- **Response Body**:
  ```json
  {
    "id": 1,
    "nombre": "Administrador Actualizado",
    "email": "admin.updated@ferremas.com",
    "rol": "ADMIN",
    "fechaRegistro": "2024-01-15T10:30:00",
    "activo": true
  }
  ```

**Criterios de Aceptación**:
- Usuario actualizado correctamente
- Solo campos especificados modificados
- Rol y fecha de registro no cambian

---

## 6. Casos de Prueba de Control de Stock

### TC-016: Consultar Stock de Producto

**Prioridad**: Media
**Tipo**: Funcional
**Módulo**: Stock

**Precondiciones**:
- Producto con ID 1 existe en el sistema

**Pasos de Ejecución**:
1. Acceder al endpoint `GET /api/productos/1/stock`
2. Verificar respuesta del sistema

**Datos de Entrada**:
- ID del producto: 1

**Resultado Esperado**:
- **Status Code**: 200 OK
- **Response Body**:
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

**Criterios de Aceptación**:
- Información de stock actual
- Estado del producto (DISPONIBLE/AGOTADO)
- Fecha de última actualización

---

### TC-017: Actualizar Stock Manualmente

**Prioridad**: Media
**Tipo**: Funcional
**Módulo**: Stock

**Precondiciones**:
- Producto con ID 1 existe en el sistema
- Usuario autenticado con rol ADMIN

**Pasos de Ejecución**:
1. Acceder al endpoint `PUT /api/productos/1/stock`
2. Enviar nueva cantidad:
   ```json
   {
     "cantidad": 50
   }
   ```
3. Verificar respuesta del sistema

**Datos de Entrada**:
- ID del producto: 1
- Nueva cantidad: 50

**Resultado Esperado**:
- **Status Code**: 200 OK
- **Response Body**:
  ```json
  {
    "productoId": 1,
    "nombre": "Martillo Profesional",
    "stockAnterior": 25,
    "stockNuevo": 50,
    "fechaActualizacion": "2024-01-15T10:35:00"
  }
  ```

**Criterios de Aceptación**:
- Stock actualizado correctamente
- Información de cambio de stock
- Fecha de actualización registrada

---

## 7. Casos de Prueba de Sistema de Pagos

### TC-018: Crear Transacción de Pago

**Prioridad**: Alta
**Tipo**: Funcional
**Módulo**: Pagos

**Precondiciones**:
- Pedido con ID 1 existe en el sistema
- Integración con Transbank configurada

**Pasos de Ejecución**:
1. Acceder al endpoint `POST /api/pagos`
2. Enviar datos del pago:
   ```json
   {
     "pedidoId": 1,
     "monto": 38500,
     "metodoPago": "WEBPAY"
   }
   ```
3. Verificar respuesta del sistema

**Datos de Entrada**:
- ID del pedido: 1
- Monto: 38500
- Método de pago: "WEBPAY"

**Resultado Esperado**:
- **Status Code**: 201 Created
- **Response Body**:
  ```json
  {
    "id": 1,
    "pedidoId": 1,
    "monto": 38500,
    "metodoPago": "WEBPAY",
    "estado": "PENDIENTE",
    "fechaCreacion": "2024-01-15T10:30:00",
    "urlPago": "https://webpay.transbank.cl/pay/123456789",
    "token": "abc123def456"
  }
  ```

**Criterios de Aceptación**:
- Transacción creada exitosamente
- URL de pago generada
- Token de transacción asignado
- Estado inicial "PENDIENTE"

---

### TC-019: Obtener Estado de Pago

**Prioridad**: Media
**Tipo**: Funcional
**Módulo**: Pagos

**Precondiciones**:
- Transacción de pago con ID 1 existe en el sistema

**Pasos de Ejecución**:
1. Acceder al endpoint `GET /api/pagos/1`
2. Verificar respuesta del sistema

**Datos de Entrada**:
- ID de la transacción: 1

**Resultado Esperado**:
- **Status Code**: 200 OK
- **Response Body**:
  ```json
  {
    "id": 1,
    "pedidoId": 1,
    "monto": 38500,
    "metodoPago": "WEBPAY",
    "estado": "APROBADO",
    "fechaCreacion": "2024-01-15T10:30:00",
    "fechaAprobacion": "2024-01-15T10:32:00",
    "codigoAutorizacion": "AUTH123456",
    "ultimosDigitos": "1234"
  }
  ```

**Criterios de Aceptación**:
- Estado actual de la transacción
- Información de autorización
- Fechas de creación y aprobación

---

## 8. Casos de Prueba de Interfaz Web

### TC-020: Acceso a Página de Login

**Prioridad**: Alta
**Tipo**: Funcional
**Módulo**: Interfaz Web

**Precondiciones**:
- Aplicación web ejecutándose
- Navegador web disponible

**Pasos de Ejecución**:
1. Abrir navegador web
2. Navegar a `http://localhost:8080/login`
3. Verificar que la página se carga correctamente

**Datos de Entrada**:
- URL: `http://localhost:8080/login`

**Resultado Esperado**:
- **Status Code**: 200 OK
- **Contenido**: Página de login con formulario
- **Elementos visibles**:
  - Campo de email
  - Campo de contraseña
  - Botón de login
  - Enlace de registro

**Criterios de Aceptación**:
- Página carga sin errores
- Formulario funcional
- Diseño responsivo
- Elementos interactivos funcionando

---

### TC-021: Navegación a Catálogo de Productos

**Prioridad**: Alta
**Tipo**: Funcional
**Módulo**: Interfaz Web

**Precondiciones**:
- Aplicación web ejecutándose
- Productos disponibles en el sistema

**Pasos de Ejecución**:
1. Abrir navegador web
2. Navegar a `http://localhost:8080/productos`
3. Verificar que se muestran los productos

**Datos de Entrada**:
- URL: `http://localhost:8080/productos`

**Resultado Esperado**:
- **Status Code**: 200 OK
- **Contenido**: Lista de productos
- **Elementos visibles**:
  - Grid de productos
  - Imágenes de productos
  - Precios
  - Botones de "Agregar al carrito"

**Criterios de Aceptación**:
- Productos se muestran correctamente
- Información completa visible
- Funcionalidad de carrito operativa
- Diseño responsivo

---

## 9. Casos de Prueba de Rendimiento

### TC-022: Prueba de Carga - 10 Usuarios

**Prioridad**: Alta
**Tipo**: Rendimiento
**Módulo**: Sistema Completo

**Precondiciones**:
- JMeter configurado
- Aplicación ejecutándose
- Datos de prueba cargados

**Pasos de Ejecución**:
1. Abrir JMeter
2. Cargar archivo `ferremas-performance-test.jmx`
3. Seleccionar Thread Group "10 Users - Login Test"
4. Ejecutar prueba
5. Analizar resultados

**Datos de Entrada**:
- Número de usuarios: 10
- Duración: 5 minutos
- Ramp-up: 30 segundos

**Resultado Esperado**:
- **Tiempo promedio**: < 2 segundos
- **Tiempo p95**: < 5 segundos
- **Throughput**: > 10 TPS
- **Tasa de error**: < 1%

**Criterios de Aceptación**:
- Sistema responde dentro de los límites
- No hay errores críticos
- Rendimiento estable

---

### TC-023: Prueba de Carga - 50 Usuarios

**Prioridad**: Media
**Tipo**: Rendimiento
**Módulo**: Sistema Completo

**Precondiciones**:
- JMeter configurado
- Aplicación ejecutándose
- Datos de prueba cargados

**Pasos de Ejecución**:
1. Abrir JMeter
2. Cargar archivo `ferremas-performance-test.jmx`
3. Seleccionar Thread Group "50 Users - Products Test"
4. Ejecutar prueba
5. Analizar resultados

**Datos de Entrada**:
- Número de usuarios: 50
- Duración: 10 minutos
- Ramp-up: 60 segundos

**Resultado Esperado**:
- **Tiempo promedio**: < 3 segundos
- **Tiempo p95**: < 8 segundos
- **Throughput**: > 20 TPS
- **Tasa de error**: < 2%

**Criterios de Aceptación**:
- Sistema maneja la carga
- Degradación de rendimiento aceptable
- No hay fallos del sistema

---

### TC-024: Prueba de Carga - 100 Usuarios

**Prioridad**: Baja
**Tipo**: Rendimiento
**Módulo**: Sistema Completo

**Precondiciones**:
- JMeter configurado
- Aplicación ejecutándose
- Datos de prueba cargados

**Pasos de Ejecución**:
1. Abrir JMeter
2. Cargar archivo `ferremas-performance-test.jmx`
3. Seleccionar Thread Group "100 Users - Orders Test"
4. Ejecutar prueba
5. Analizar resultados

**Datos de Entrada**:
- Número de usuarios: 100
- Duración: 15 minutos
- Ramp-up: 120 segundos

**Resultado Esperado**:
- **Tiempo promedio**: < 5 segundos
- **Tiempo p95**: < 12 segundos
- **Throughput**: > 30 TPS
- **Tasa de error**: < 5%

**Criterios de Aceptación**:
- Sistema no colapsa
- Rendimiento degradado pero funcional
- Identificación de cuellos de botella

---

## 10. Casos de Prueba de Seguridad

### TC-025: Validación de Entrada de Datos

**Prioridad**: Alta
**Tipo**: Seguridad
**Módulo**: Sistema Completo

**Precondiciones**:
- Sistema ejecutándose
- Endpoints accesibles

**Pasos de Ejecución**:
1. Acceder al endpoint `POST /api/usuarios`
2. Enviar datos con caracteres especiales:
   ```json
   {
     "nombre": "<script>alert('XSS')</script>",
     "email": "test@test.com",
     "password": "password123"
   }
   ```
3. Verificar respuesta del sistema

**Datos de Entrada**:
- Nombre con script malicioso
- Email válido
- Contraseña válida

**Resultado Esperado**:
- **Status Code**: 400 Bad Request
- **Response Body**: Mensaje de validación
- **Comportamiento**: Script no se ejecuta

**Criterios de Aceptación**:
- Sistema valida entrada
- Previene inyección de scripts
- Mensaje de error apropiado

---

### TC-026: Control de Acceso por Roles

**Prioridad**: Alta
**Tipo**: Seguridad
**Módulo**: Autenticación

**Precondiciones**:
- Usuario cliente autenticado
- Endpoints de administración disponibles

**Pasos de Ejecución**:
1. Autenticar como usuario CLIENTE
2. Intentar acceder a `GET /api/usuarios`
3. Verificar respuesta del sistema

**Datos de Entrada**:
- Usuario: cliente1@test.com
- Contraseña: cliente123
- Endpoint: `/api/usuarios`

**Resultado Esperado**:
- **Status Code**: 403 Forbidden
- **Response Body**:
  ```json
  {
    "success": false,
    "message": "Acceso denegado",
    "error": "ACCESS_DENIED"
  }
  ```

**Criterios de Aceptación**:
- Sistema controla acceso
- Usuario cliente no puede acceder a recursos de admin
- Mensaje de error apropiado

---

### TC-027: Prueba de Inyección SQL

**Prioridad**: Alta
**Tipo**: Seguridad
**Módulo**: Base de Datos

**Precondiciones**:
- Sistema ejecutándose
- Base de datos configurada

**Pasos de Ejecución**:
1. Acceder al endpoint `GET /api/productos/1' OR '1'='1`
2. Verificar respuesta del sistema

**Datos de Entrada**:
- ID con inyección SQL: `1' OR '1'='1`

**Resultado Esperado**:
- **Status Code**: 400 Bad Request
- **Response Body**: Mensaje de error
- **Comportamiento**: No se ejecuta la consulta maliciosa

**Criterios de Aceptación**:
- Sistema previene inyección SQL
- Parámetros validados
- No se exponen datos sensibles

---

### TC-028: Prueba de Rate Limiting

**Prioridad**: Media
**Tipo**: Seguridad
**Módulo**: Sistema Completo

**Precondiciones**:
- Sistema ejecutándose
- Rate limiting configurado

**Pasos de Ejecución**:
1. Enviar múltiples requests rápidos al endpoint de login
2. Verificar respuesta del sistema después del límite

**Datos de Entrada**:
- 100 requests en 10 segundos
- Endpoint: `POST /api/auth/login`

**Resultado Esperado**:
- **Status Code**: 429 Too Many Requests
- **Response Body**:
  ```json
  {
    "success": false,
    "message": "Demasiadas solicitudes",
    "error": "RATE_LIMIT_EXCEEDED"
  }
  ```

**Criterios de Aceptación**:
- Sistema limita requests
- Previene ataques de fuerza bruta
- Mensaje de error apropiado

---

## 📊 Resumen de Casos de Prueba

| Módulo | Casos de Prueba | Prioridad Alta | Prioridad Media | Prioridad Baja |
|--------|----------------|----------------|-----------------|----------------|
| **Autenticación** | 4 | 4 | 0 | 0 |
| **Productos** | 5 | 3 | 2 | 0 |
| **Pedidos** | 3 | 2 | 1 | 0 |
| **Usuarios** | 3 | 0 | 3 | 0 |
| **Stock** | 2 | 0 | 2 | 0 |
| **Pagos** | 2 | 1 | 1 | 0 |
| **Interfaz Web** | 2 | 2 | 0 | 0 |
| **Rendimiento** | 3 | 1 | 1 | 1 |
| **Seguridad** | 4 | 3 | 1 | 0 |
| **TOTAL** | **28** | **16** | **11** | **1** |

---

## 🎯 Criterios de Aceptación Generales

### Funcionales
- ✅ Todos los endpoints responden correctamente
- ✅ Validaciones de datos implementadas
- ✅ Manejo de errores consistente
- ✅ Integración entre módulos funcional

### Rendimiento
- ✅ Tiempo de respuesta < 2 segundos (carga normal)
- ✅ Tiempo de respuesta < 5 segundos (carga alta)
- ✅ Throughput > 10 TPS
- ✅ Tasa de error < 1%

### Seguridad
- ✅ Autenticación requerida
- ✅ Autorización por roles
- ✅ Validación de entrada
- ✅ Prevención de ataques comunes

### Usabilidad
- ✅ Interfaz responsiva
- ✅ Navegación intuitiva
- ✅ Mensajes de error claros
- ✅ Funcionalidad completa

---

**Documento preparado para**: ASY5131 - Integración de Plataformas  
**Sistema**: Ferremas  
**Versión**: 1.0  
**Fecha**: Enero 2024 