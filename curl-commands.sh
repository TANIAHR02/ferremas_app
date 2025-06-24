#!/bin/bash

# Script de comandos curl para pruebas de la API Ferremas
# ASY5131 - Integración de Plataformas

BASE_URL="http://localhost:8080"
CONTENT_TYPE="Content-Type: application/json"

echo "=== PRUEBAS DE LA API FERREMAS ==="
echo "Base URL: $BASE_URL"
echo ""

# Función para mostrar resultados
show_result() {
    echo "Status: $1"
    echo "Response: $2"
    echo "---"
}

# 1. PRUEBAS DE AUTENTICACIÓN
echo "1. PRUEBAS DE AUTENTICACIÓN"
echo "=========================="

# 1.1 Login exitoso
echo "1.1 Login exitoso:"
LOGIN_RESPONSE=$(curl -s -w "%{http_code}" -X POST "$BASE_URL/api/auth/login" \
  -H "$CONTENT_TYPE" \
  -d '{"email": "admin@ferremas.com", "password": "admin123"}')
HTTP_CODE="${LOGIN_RESPONSE: -3}"
RESPONSE_BODY="${LOGIN_RESPONSE%???}"
show_result "$HTTP_CODE" "$RESPONSE_BODY"

# 1.2 Login con credenciales incorrectas
echo "1.2 Login con credenciales incorrectas:"
LOGIN_FAIL_RESPONSE=$(curl -s -w "%{http_code}" -X POST "$BASE_URL/api/auth/login" \
  -H "$CONTENT_TYPE" \
  -d '{"email": "admin@ferremas.com", "password": "wrongpassword"}')
HTTP_CODE="${LOGIN_FAIL_RESPONSE: -3}"
RESPONSE_BODY="${LOGIN_FAIL_RESPONSE%???}"
show_result "$HTTP_CODE" "$RESPONSE_BODY"

# 1.3 Registro de usuario
echo "1.3 Registro de usuario:"
REGISTER_RESPONSE=$(curl -s -w "%{http_code}" -X POST "$BASE_URL/api/usuarios" \
  -H "$CONTENT_TYPE" \
  -d '{
    "nombre": "Usuario Test",
    "email": "test@example.com",
    "password": "test123",
    "rol": "CLIENTE"
  }')
HTTP_CODE="${REGISTER_RESPONSE: -3}"
RESPONSE_BODY="${REGISTER_RESPONSE%???}"
show_result "$HTTP_CODE" "$RESPONSE_BODY"

echo ""

# 2. PRUEBAS DE PRODUCTOS
echo "2. PRUEBAS DE PRODUCTOS"
echo "======================"

# 2.1 Listar todos los productos
echo "2.1 Listar todos los productos:"
PRODUCTS_RESPONSE=$(curl -s -w "%{http_code}" -X GET "$BASE_URL/api/productos")
HTTP_CODE="${PRODUCTS_RESPONSE: -3}"
RESPONSE_BODY="${PRODUCTS_RESPONSE%???}"
show_result "$HTTP_CODE" "$RESPONSE_BODY"

# 2.2 Obtener producto específico
echo "2.2 Obtener producto específico (ID: 1):"
PRODUCT_RESPONSE=$(curl -s -w "%{http_code}" -X GET "$BASE_URL/api/productos/1")
HTTP_CODE="${PRODUCT_RESPONSE: -3}"
RESPONSE_BODY="${PRODUCT_RESPONSE%???}"
show_result "$HTTP_CODE" "$RESPONSE_BODY"

# 2.3 Crear nuevo producto
echo "2.3 Crear nuevo producto:"
CREATE_PRODUCT_RESPONSE=$(curl -s -w "%{http_code}" -X POST "$BASE_URL/api/productos" \
  -H "$CONTENT_TYPE" \
  -d '{
    "nombre": "Taladro Profesional",
    "descripcion": "Taladro de alta potencia para uso profesional",
    "precio": 45000,
    "categoria": "HERRAMIENTAS",
    "marca": "DeWalt",
    "stock": 15
  }')
HTTP_CODE="${CREATE_PRODUCT_RESPONSE: -3}"
RESPONSE_BODY="${CREATE_PRODUCT_RESPONSE%???}"
show_result "$HTTP_CODE" "$RESPONSE_BODY"

# 2.4 Actualizar producto
echo "2.4 Actualizar producto (ID: 1):"
UPDATE_PRODUCT_RESPONSE=$(curl -s -w "%{http_code}" -X PUT "$BASE_URL/api/productos/1" \
  -H "$CONTENT_TYPE" \
  -d '{
    "nombre": "Martillo Profesional Actualizado",
    "precio": 18000,
    "stock": 30
  }')
HTTP_CODE="${UPDATE_PRODUCT_RESPONSE: -3}"
RESPONSE_BODY="${UPDATE_PRODUCT_RESPONSE%???}"
show_result "$HTTP_CODE" "$RESPONSE_BODY"

# 2.5 Obtener producto inexistente
echo "2.5 Obtener producto inexistente (ID: 999):"
NOT_FOUND_RESPONSE=$(curl -s -w "%{http_code}" -X GET "$BASE_URL/api/productos/999")
HTTP_CODE="${NOT_FOUND_RESPONSE: -3}"
RESPONSE_BODY="${NOT_FOUND_RESPONSE%???}"
show_result "$HTTP_CODE" "$RESPONSE_BODY"

echo ""

# 3. PRUEBAS DE PEDIDOS
echo "3. PRUEBAS DE PEDIDOS"
echo "===================="

# 3.1 Crear pedido exitoso
echo "3.1 Crear pedido exitoso:"
CREATE_ORDER_RESPONSE=$(curl -s -w "%{http_code}" -X POST "$BASE_URL/api/pedidos" \
  -H "$CONTENT_TYPE" \
  -d '{
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
  }')
HTTP_CODE="${CREATE_ORDER_RESPONSE: -3}"
RESPONSE_BODY="${CREATE_ORDER_RESPONSE%???}"
show_result "$HTTP_CODE" "$RESPONSE_BODY"

# 3.2 Listar pedidos de usuario
echo "3.2 Listar pedidos de usuario (ID: 1):"
USER_ORDERS_RESPONSE=$(curl -s -w "%{http_code}" -X GET "$BASE_URL/api/pedidos/usuario/1")
HTTP_CODE="${USER_ORDERS_RESPONSE: -3}"
RESPONSE_BODY="${USER_ORDERS_RESPONSE%???}"
show_result "$HTTP_CODE" "$RESPONSE_BODY"

# 3.3 Obtener detalle de pedido específico
echo "3.3 Obtener detalle de pedido (ID: 1):"
ORDER_DETAIL_RESPONSE=$(curl -s -w "%{http_code}" -X GET "$BASE_URL/api/pedidos/1")
HTTP_CODE="${ORDER_DETAIL_RESPONSE: -3}"
RESPONSE_BODY="${ORDER_DETAIL_RESPONSE%???}"
show_result "$HTTP_CODE" "$RESPONSE_BODY"

# 3.4 Crear pedido con stock insuficiente
echo "3.4 Crear pedido con stock insuficiente:"
INSUFFICIENT_STOCK_RESPONSE=$(curl -s -w "%{http_code}" -X POST "$BASE_URL/api/pedidos" \
  -H "$CONTENT_TYPE" \
  -d '{
    "usuarioId": 1,
    "productos": [
      {
        "productoId": 1,
        "cantidad": 999
      }
    ]
  }')
HTTP_CODE="${INSUFFICIENT_STOCK_RESPONSE: -3}"
RESPONSE_BODY="${INSUFFICIENT_STOCK_RESPONSE%???}"
show_result "$HTTP_CODE" "$RESPONSE_BODY"

echo ""

# 4. PRUEBAS DE USUARIOS
echo "4. PRUEBAS DE USUARIOS"
echo "====================="

# 4.1 Listar todos los usuarios
echo "4.1 Listar todos los usuarios:"
USERS_RESPONSE=$(curl -s -w "%{http_code}" -X GET "$BASE_URL/api/usuarios")
HTTP_CODE="${USERS_RESPONSE: -3}"
RESPONSE_BODY="${USERS_RESPONSE%???}"
show_result "$HTTP_CODE" "$RESPONSE_BODY"

# 4.2 Obtener usuario específico
echo "4.2 Obtener usuario específico (ID: 1):"
USER_RESPONSE=$(curl -s -w "%{http_code}" -X GET "$BASE_URL/api/usuarios/1")
HTTP_CODE="${USER_RESPONSE: -3}"
RESPONSE_BODY="${USER_RESPONSE%???}"
show_result "$HTTP_CODE" "$RESPONSE_BODY"

# 4.3 Actualizar usuario
echo "4.3 Actualizar usuario (ID: 1):"
UPDATE_USER_RESPONSE=$(curl -s -w "%{http_code}" -X PUT "$BASE_URL/api/usuarios/1" \
  -H "$CONTENT_TYPE" \
  -d '{
    "nombre": "Administrador Actualizado",
    "email": "admin.updated@ferremas.com"
  }')
HTTP_CODE="${UPDATE_USER_RESPONSE: -3}"
RESPONSE_BODY="${UPDATE_USER_RESPONSE%???}"
show_result "$HTTP_CODE" "$RESPONSE_BODY"

echo ""

# 5. PRUEBAS DE STOCK
echo "5. PRUEBAS DE STOCK"
echo "=================="

# 5.1 Consultar stock de producto
echo "5.1 Consultar stock de producto (ID: 1):"
STOCK_RESPONSE=$(curl -s -w "%{http_code}" -X GET "$BASE_URL/api/productos/1/stock")
HTTP_CODE="${STOCK_RESPONSE: -3}"
RESPONSE_BODY="${STOCK_RESPONSE%???}"
show_result "$HTTP_CODE" "$RESPONSE_BODY"

# 5.2 Actualizar stock
echo "5.2 Actualizar stock de producto (ID: 1):"
UPDATE_STOCK_RESPONSE=$(curl -s -w "%{http_code}" -X PUT "$BASE_URL/api/productos/1/stock" \
  -H "$CONTENT_TYPE" \
  -d '{"cantidad": 50}')
HTTP_CODE="${UPDATE_STOCK_RESPONSE: -3}"
RESPONSE_BODY="${UPDATE_STOCK_RESPONSE%???}"
show_result "$HTTP_CODE" "$RESPONSE_BODY"

echo ""

# 6. PRUEBAS DE PAGOS
echo "6. PRUEBAS DE PAGOS"
echo "=================="

# 6.1 Crear transacción de pago
echo "6.1 Crear transacción de pago:"
PAYMENT_RESPONSE=$(curl -s -w "%{http_code}" -X POST "$BASE_URL/api/pagos" \
  -H "$CONTENT_TYPE" \
  -d '{
    "pedidoId": 1,
    "monto": 30000,
    "metodoPago": "WEBPAY"
  }')
HTTP_CODE="${PAYMENT_RESPONSE: -3}"
RESPONSE_BODY="${PAYMENT_RESPONSE%???}"
show_result "$HTTP_CODE" "$RESPONSE_BODY"

# 6.2 Obtener estado de pago
echo "6.2 Obtener estado de pago (ID: 1):"
PAYMENT_STATUS_RESPONSE=$(curl -s -w "%{http_code}" -X GET "$BASE_URL/api/pagos/1")
HTTP_CODE="${PAYMENT_STATUS_RESPONSE: -3}"
RESPONSE_BODY="${PAYMENT_STATUS_RESPONSE%???}"
show_result "$HTTP_CODE" "$RESPONSE_BODY"

echo ""

# 7. PRUEBAS DE ERRORES
echo "7. PRUEBAS DE ERRORES"
echo "===================="

# 7.1 Endpoint inexistente
echo "7.1 Endpoint inexistente:"
NOT_FOUND_ENDPOINT_RESPONSE=$(curl -s -w "%{http_code}" -X GET "$BASE_URL/api/endpoint-inexistente")
HTTP_CODE="${NOT_FOUND_ENDPOINT_RESPONSE: -3}"
RESPONSE_BODY="${NOT_FOUND_ENDPOINT_RESPONSE%???}"
show_result "$HTTP_CODE" "$RESPONSE_BODY"

# 7.2 Método HTTP no permitido
echo "7.2 Método HTTP no permitido:"
METHOD_NOT_ALLOWED_RESPONSE=$(curl -s -w "%{http_code}" -X DELETE "$BASE_URL/api/productos")
HTTP_CODE="${METHOD_NOT_ALLOWED_RESPONSE: -3}"
RESPONSE_BODY="${METHOD_NOT_ALLOWED_RESPONSE%???}"
show_result "$HTTP_CODE" "$RESPONSE_BODY"

# 7.3 Datos JSON inválidos
echo "7.3 Datos JSON inválidos:"
INVALID_JSON_RESPONSE=$(curl -s -w "%{http_code}" -X POST "$BASE_URL/api/productos" \
  -H "$CONTENT_TYPE" \
  -d '{"nombre": "Producto", "precio": "no-es-numero"}')
HTTP_CODE="${INVALID_JSON_RESPONSE: -3}"
RESPONSE_BODY="${INVALID_JSON_RESPONSE%???}"
show_result "$HTTP_CODE" "$RESPONSE_BODY"

echo ""
echo "=== FIN DE LAS PRUEBAS ==="
echo "Resumen:"
echo "- Pruebas de autenticación: 3 casos"
echo "- Pruebas de productos: 5 casos"
echo "- Pruebas de pedidos: 4 casos"
echo "- Pruebas de usuarios: 3 casos"
echo "- Pruebas de stock: 2 casos"
echo "- Pruebas de pagos: 2 casos"
echo "- Pruebas de errores: 3 casos"
echo "Total: 22 casos de prueba" 