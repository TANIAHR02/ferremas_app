# FERREMAS - Sistema Mejorado de Gestión Ferretera

## 🚀 Descripción General

FERREMAS es una aplicación web integral desarrollada en Java + Spring Boot que permite gestionar una distribuidora ferretera completa. El sistema incluye gestión de usuarios, productos, pedidos, pagos, y más, con funcionalidades específicas para cada rol de usuario.

## ✨ Nuevas Características Implementadas

### 🔐 Sistema de Autenticación y Autorización Mejorado
- **JWT (JSON Web Tokens)** para autenticación segura
- **Autorización basada en roles** con Spring Security
- **Filtros de seguridad** personalizados
- **Gestión de sesiones** stateless
- **CORS configurado** para integración frontend

### 👥 Gestión de Roles y Permisos
- **5 roles específicos**: Cliente, Administrador, Vendedor, Bodeguero, Contador
- **Endpoints protegidos** por rol
- **Funcionalidades específicas** para cada tipo de usuario
- **Dashboard personalizado** por rol

### 📧 Sistema de Notificaciones
- **Servicio de email** integrado
- **Notificaciones automáticas** para:
  - Registro de usuarios
  - Confirmación de pedidos
  - Cambios de estado de pedidos
  - Reseteo de contraseñas
  - Ofertas especiales

### 🎯 Funcionalidades por Rol

#### 👤 Cliente
- Ver catálogo de productos
- Buscar productos por categoría
- Crear y gestionar pedidos
- Seguimiento de pedidos en tiempo real
- Sistema de favoritos
- Estadísticas personales

#### 👨‍💼 Administrador
- Gestión completa de usuarios
- Activar/desactivar usuarios
- Resetear contraseñas
- Estadísticas del sistema
- Dashboard administrativo

#### 💼 Vendedor
- Ver productos disponibles
- Aprobar/rechazar pedidos
- Enviar pedidos a bodega
- Gestión de stock
- Dashboard de ventas

#### 📦 Bodeguero
- Ver pedidos pendientes
- Aceptar y preparar pedidos
- Entregar pedidos a vendedores
- Gestión de inventario
- Control de stock

#### 💰 Contador
- Confirmar pagos por transferencia
- Rechazar pagos con motivo
- Registrar entregas
- Generar reportes de ventas
- Dashboard financiero

## 🛠️ Tecnologías Utilizadas

### Backend
- **Java 17**
- **Spring Boot 3.4.6**
- **Spring Security** con JWT
- **Spring Data JPA**
- **MySQL 8.0**
- **Maven**

### Nuevas Dependencias
- **JWT (jjwt)** para autenticación
- **Spring Mail** para notificaciones
- **Spring Data Redis** para cache
- **Spring Boot Actuator** para monitoreo
- **Spring Security Test** para pruebas

### Frontend
- **Thymeleaf** para templates
- **HTML5 + CSS3**
- **JavaScript** para interactividad

### Integraciones
- **WebPay** para pagos con tarjeta
- **Banco Central de Chile** para conversión de divisas
- **Swagger/OpenAPI** para documentación

## 📋 Instalación y Configuración

### Prerrequisitos
- Java 17 o superior
- MySQL 8.0
- Maven 3.6+
- Redis (opcional, para cache)

### 1. Clonar el Repositorio
```bash
git clone https://github.com/TANIAHR02/ferremas_app.git
cd ferremasapp
```

### 2. Configurar Base de Datos
```sql
CREATE DATABASE ferremas_db;
USE ferremas_db;
-- Ejecutar script ferremas_db.sql
```

### 3. Configurar Variables de Entorno
Editar `src/main/resources/application.properties`:
```properties
# Base de datos
spring.datasource.url=jdbc:mysql://localhost:3306/ferremas_db
spring.datasource.username=tu_usuario
spring.datasource.password=tu_password

# JWT
jwt.secret=tu_clave_secreta_muy_segura
jwt.expiration=86400000

# Email (Gmail)
spring.mail.username=tu_email@gmail.com
spring.mail.password=tu_app_password

# WebPay
webpay.api.key=tu_api_key
webpay.commerce.code=tu_commerce_code
```

### 4. Instalar Dependencias y Ejecutar
```bash
# Instalar dependencias
./mvnw clean install

# Ejecutar aplicación
./mvnw spring-boot:run
```

### 5. Acceder a la Aplicación
- **Aplicación**: http://localhost:8081
- **API Documentation**: http://localhost:8081/swagger-ui.html
- **Actuator**: http://localhost:8081/actuator

## 🔑 Autenticación y Uso

### Registro de Usuario
```bash
POST /api/auth/registro
{
  "nombre": "Juan",
  "apellido": "Pérez",
  "email": "juan@example.com",
  "password": "password123",
  "telefono": "+56912345678",
  "direccion": "Av. Principal 123"
}
```

### Login
```bash
POST /api/auth/login
{
  "email": "juan@example.com",
  "password": "password123"
}
```

### Usar Token JWT
```bash
# Incluir en headers de todas las peticiones autenticadas
Authorization: Bearer <token_jwt>
```

## 📚 Documentación de API

### Endpoints por Rol

#### 🔐 Autenticación (Público)
- `POST /api/auth/login` - Iniciar sesión
- `POST /api/auth/registro` - Registrar usuario
- `POST /api/auth/logout` - Cerrar sesión
- `GET /api/auth/perfil` - Obtener perfil

#### 👤 Cliente
- `GET /api/cliente/productos` - Ver catálogo
- `GET /api/cliente/pedidos` - Mis pedidos
- `POST /api/cliente/pedidos` - Crear pedido
- `GET /api/cliente/favoritos` - Mis favoritos

#### 👨‍💼 Administrador
- `GET /api/admin/usuarios` - Listar usuarios
- `PUT /api/admin/usuarios/{id}/estado` - Activar/desactivar
- `POST /api/admin/usuarios/{id}/reset-password` - Resetear password
- `GET /api/admin/dashboard/stats` - Estadísticas

#### 💼 Vendedor
- `GET /api/vendedor/productos` - Productos disponibles
- `GET /api/vendedor/pedidos/pendientes` - Pedidos pendientes
- `PUT /api/vendedor/pedidos/{id}/aprobar` - Aprobar pedido
- `POST /api/vendedor/pedidos/{id}/enviar-bodega` - Enviar a bodega

#### 📦 Bodeguero
- `GET /api/bodeguero/pedidos/pendientes` - Pedidos pendientes
- `PUT /api/bodeguero/pedidos/{id}/aceptar` - Aceptar pedido
- `PUT /api/bodeguero/pedidos/{id}/preparar` - Marcar como preparado
- `GET /api/bodeguero/inventario` - Ver inventario

#### 💰 Contador
- `GET /api/contador/pagos/pendientes` - Pagos pendientes
- `PUT /api/contador/pagos/{id}/confirmar` - Confirmar pago
- `PUT /api/contador/pedidos/{id}/confirmar-entrega` - Confirmar entrega
- `GET /api/contador/reportes/ventas` - Reporte de ventas

## 🧪 Pruebas

### Pruebas Automatizadas
```bash
# Ejecutar todas las pruebas
./mvnw test

# Pruebas de seguridad
./mvnw test -Dtest=SecurityTest

# Pruebas de integración
./mvnw test -Dtest=IntegrationTest
```

### Pruebas Manuales
- **Postman**: Colección incluida en el proyecto
- **Swagger UI**: http://localhost:8081/swagger-ui.html
- **JMeter**: Scripts de prueba de rendimiento incluidos

## 📊 Monitoreo y Métricas

### Actuator Endpoints
- `/actuator/health` - Estado de la aplicación
- `/actuator/info` - Información de la aplicación
- `/actuator/metrics` - Métricas del sistema

### Logs
- Configurados para DEBUG en desarrollo
- Rotación automática de logs
- Niveles configurables por paquete

## 🔒 Seguridad

### Medidas Implementadas
- **JWT Tokens** con expiración configurable
- **CORS** configurado para dominios específicos
- **CSRF** deshabilitado para API REST
- **Passwords encriptados** con BCrypt
- **Autorización basada en roles**
- **Validación de entrada** en todos los endpoints

### Configuración de Seguridad
```java
// Rutas públicas
/api/auth/**
/api/public/**
/swagger-ui/**

// Rutas por rol
/api/admin/** -> ROLE_ADMIN
/api/vendedor/** -> ROLE_VENDEDOR
/api/bodeguero/** -> ROLE_BODEGUERO
/api/contador/** -> ROLE_CONTADOR
/api/cliente/** -> ROLE_CLIENTE
```

## 🚀 Despliegue

### Desarrollo
```bash
./mvnw spring-boot:run
```

### Producción
```bash
# Crear JAR
./mvnw clean package

# Ejecutar JAR
java -jar target/ferremasapp-0.0.1-SNAPSHOT.jar
```

### Docker (Opcional)
```dockerfile
FROM openjdk:17-jdk-slim
COPY target/ferremasapp-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","/app.jar"]
```

## 📈 Próximas Mejoras

### Funcionalidades Planificadas
- [ ] **Sistema de notificaciones push**
- [ ] **Chat en tiempo real** para soporte
- [ ] **App móvil** nativa
- [ ] **Analytics avanzados**
- [ ] **Integración con ERP**
- [ ] **Sistema de cupones y descuentos**
- [ ] **Múltiples sucursales**
- [ ] **Reportes avanzados**

### Optimizaciones Técnicas
- [ ] **Cache distribuido** con Redis
- [ ] **Microservicios** para escalabilidad
- [ ] **API Gateway** para gestión de tráfico
- [ ] **Monitoreo con Prometheus/Grafana**
- [ ] **CI/CD pipeline** automatizado

## 🤝 Contribución

1. Fork el proyecto
2. Crear una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abrir un Pull Request

## 📄 Licencia

Este proyecto está bajo la Licencia MIT. Ver el archivo `LICENSE` para más detalles.

## 📞 Soporte

- **Email**: soporte@ferremas.com
- **Documentación**: http://localhost:8081/swagger-ui.html
- **Issues**: GitHub Issues

---

**FERREMAS** - Tu ferretería de confianza 🛠️ 