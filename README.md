# Sistema Ferremas - Aplicación Integral de Gestión Ferretera
## ASY5131 - Integración de Plataformas

---

## 📋 Descripción General

FerremasApp es una aplicación integral desarrollada en **Java + Spring Boot 3.x**, con integración de **H2/MySQL**, que permite gestionar clientes, productos, pedidos, pagos y más, para una distribuidora ferretera. El sistema incluye una interfaz web moderna y responsiva, integración con sistemas de pago y APIs externas.

### 🚀 Características Principales

- ✅ **Gestión completa de usuarios** con roles y autenticación
- ✅ **Catálogo de productos** con categorías y marcas
- ✅ **Sistema de pedidos** con carrito de compras
- ✅ **Control de stock** automático
- ✅ **Integración con Transbank WebPay** para pagos
- ✅ **API del Banco Central** para conversión de divisas
- ✅ **Interfaz web moderna** y responsiva
- ✅ **Documentación API** con Swagger
- ✅ **Sistema de seguridad** configurable

---

## 🛠️ Instrucciones de Ejecución

### 1. Clonar el Repositorio
```bash
git clone https://github.com/TANIAHR02/ferremas_app.git
cd ferremasapp
```

### 2. Configuración de Base de Datos

#### Opción A: H2 (Recomendado para Desarrollo)
La aplicación está configurada por defecto para usar H2 (base de datos embebida). No requiere configuración adicional.

#### Opción B: MySQL (Para Producción)
Si prefieres usar MySQL:

1. **Configura XAMPP**:
   - Inicia Apache y MySQL
   - Crea la base de datos `ferremas_db`

2. **Ejecuta el script SQL**:
   ```sql
   -- Ejecuta el archivo ferremas_db.sql para crear las tablas
   ```

3. **Configura application.properties**:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/ferremas_db?useSSL=false&serverTimezone=UTC
   spring.datasource.username=root
   spring.datasource.password=
   ```

### 3. Instalar Dependencias y Ejecutar
```bash
# Instalar dependencias
./mvnw clean install

# Ejecutar la aplicación
./mvnw spring-boot:run
```

### 4. Acceder a la Aplicación

| Recurso | URL | Descripción |
|---------|-----|-------------|
| **Aplicación Web** | http://localhost:8080 | Interfaz principal |
| **Documentación API** | http://localhost:8080/swagger-ui.html | Swagger UI |
| **Base de Datos H2** | http://localhost:8080/h2-console | Consola H2 (si está habilitada) |

---

## 🔐 Configuración de Seguridad

### Modo Desarrollo (Seguridad Deshabilitada)
Por defecto, la aplicación está configurada para desarrollo con seguridad mínima:

```properties
# application.properties
spring.security.enabled=false
spring.security.basic.enabled=false
```

**Características del modo desarrollo**:
- ✅ Acceso libre a todas las APIs
- ✅ No requiere autenticación
- ✅ Ideal para pruebas y desarrollo
- ⚠️ **NO usar en producción**

### Modo Producción (Seguridad Habilitada)
Para habilitar la seguridad completa:

1. **Modifica application.properties**:
   ```properties
   spring.security.enabled=true
   spring.security.basic.enabled=true
   ```

2. **Configuración de seguridad**:
   ```java
   // SecurityConfig.java
   @Configuration
   @EnableWebSecurity
   public class SecurityConfig {
       
       @Bean
       public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
           http
               .authorizeHttpRequests(authz -> authz
                   .requestMatchers("/api/auth/**").permitAll()
                   .requestMatchers("/api/productos/**").permitAll()
                   .requestMatchers("/api/usuarios/**").hasRole("ADMIN")
                   .requestMatchers("/api/pedidos/**").authenticated()
                   .anyRequest().authenticated()
               )
               .csrf(csrf -> csrf.disable())
               .sessionManagement(session -> session
                   .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
               );
           
           return http.build();
       }
   }
   ```

3. **Roles de Usuario**:
   - **ADMIN**: Acceso completo al sistema
   - **CLIENTE**: Acceso limitado a funcionalidades de cliente

### Usuarios de Prueba
```bash
# Administrador
Email: admin@ferremas.com
Contraseña: admin123
Rol: ADMIN

# Cliente
Email: cliente1@test.com
Contraseña: cliente123
Rol: CLIENTE
```

---

## 🏗️ Arquitectura del Sistema

### Modelo 4+1 de Arquitectura

#### 🔹 Vista Lógica
- **Arquitectura en capas**: Controladores REST, Servicios, Repositorios, Modelos
- **Separación de responsabilidades** clara
- **Inyección de dependencias** con Spring

#### 🔹 Vista de Desarrollo
- **Proyecto Java + Spring Boot 3.x**
- **Gestión de dependencias** con Maven
- **Estructura modular** organizada por paquetes
- **Patrones de diseño** implementados

#### 🔹 Vista de Procesos
- **Servicios REST** asíncronos
- **Controladores web** para rutas de la aplicación
- **Integración con APIs externas** (WebPay, Banco Central)
- **Gestión de transacciones** y estados

#### 🔹 Vista Física
- **Servidor Tomcat** en puerto 8080
- **Base de datos H2/MySQL**
- **Dependencias externas** gestionadas por Maven
- **Recursos estáticos** servidos por Spring Boot

#### 🔹 Vista de Escenarios
- **Flujo de compra**: Cliente → Carrito → WebPay → Confirmación
- **Consulta de divisas**: Cliente → API Banco Central → Tasas actualizadas
- **Gestión de stock**: Productos → Reservas → Actualización automática

---

## 📚 Documentación API / Webservice

### Swagger UI
La documentación completa se genera automáticamente mediante **Swagger / OpenAPI**:
- **URL**: http://localhost:8080/swagger-ui.html
- **Especificación OpenAPI**: http://localhost:8080/v3/api-docs

### Endpoints Principales

#### Autenticación
- `POST /api/auth/login` - Inicio de sesión
- `POST /api/usuarios` - Registro de usuarios

#### Productos
- `GET /api/productos` - Listar productos
- `POST /api/productos` - Crear producto
- `PUT /api/productos/{id}` - Actualizar producto
- `DELETE /api/productos/{id}` - Eliminar producto

#### Pedidos
- `POST /api/pedidos` - Crear pedido
- `GET /api/pedidos/usuario/{id}` - Pedidos por usuario
- `PUT /api/pedidos/{id}/estado` - Actualizar estado

#### Pagos
- `POST /api/pagos` - Crear transacción
- `GET /api/pagos/{id}` - Estado de pago
- `POST /api/webpay/crear` - Crear pago WebPay

#### Usuarios
- `GET /api/usuarios` - Listar usuarios (ADMIN)
- `PUT /api/usuarios/{id}` - Actualizar usuario
- `GET /api/usuarios/{id}` - Obtener usuario

---

## 🎨 Interfaz Gráfica

### Características de la Interfaz
- ✅ **Diseño moderno y responsivo**
- ✅ **Navegación intuitiva**
- ✅ **Formularios validados**
- ✅ **Mensajes de error claros**
- ✅ **Compatibilidad móvil**

### Páginas Principales
| Página | URL | Descripción |
|--------|-----|-------------|
| **Home** | `/` | Página principal con información |
| **Login** | `/login` | Inicio de sesión |
| **Registro** | `/registro` | Registro de usuarios |
| **Productos** | `/productos` | Catálogo de productos |
| **Carrito** | `/carrito` | Gestión del carrito de compras |
| **Perfil** | `/perfil` | Perfil de usuario |
| **Quienes Somos** | `/quienes-somos` | Información de la empresa |
| **Tipos de Pago** | `/tipos-pago` | Opciones de pago disponibles |

### Tecnologías Frontend
- **Thymeleaf**: Motor de plantillas
- **CSS3**: Estilos modernos y responsivos
- **JavaScript**: Interactividad del lado cliente
- **Bootstrap**: Framework CSS (opcional)

---

## 🔗 Integraciones

### Transbank WebPay
- **Propósito**: Procesamiento de pagos en línea
- **Funcionalidades**:
  - Creación de transacciones
  - Procesamiento de pagos
  - Confirmación de transacciones
  - Manejo de errores

### Banco Central de Chile
- **Propósito**: Conversión de divisas en tiempo real
- **Funcionalidades**:
  - Consulta de tasas de cambio
  - Conversión de monedas
  - Historial de precios

---

## 🧪 Pruebas y Calidad

### Herramientas de Pruebas
- **JMeter**: Pruebas de rendimiento y carga
- **Postman**: Pruebas de API
- **Swagger UI**: Pruebas interactivas
- **curl**: Pruebas desde línea de comandos

### Casos de Prueba
- ✅ **28 casos de prueba funcionales** documentados
- ✅ **Pruebas de rendimiento** (10, 50, 100 usuarios)
- ✅ **Pruebas de seguridad** implementadas
- ✅ **Pruebas de interfaz** completadas

### Documentación de Pruebas
- `Detalle_Casos_Prueba_Ferremas.md` - Casos de prueba detallados
- `Plan_Pruebas_Ferremas.md` - Plan de pruebas completo
- `Informe_Pruebas_Ferremas.md` - Informe de resultados
- `Guia_JMeter_Ferremas.md` - Guía de uso de JMeter
- `Comandos_JMeter_Ferremas.md` - Comandos de automatización

---

## 📦 Estructura del Proyecto

```
ferremasapp/
├── src/
│   ├── main/
│   │   ├── java/cl/duoc/ferremasapp/
│   │   │   ├── config/          # Configuraciones
│   │   │   ├── controller/      # Controladores REST
│   │   │   ├── dto/            # Objetos de transferencia
│   │   │   ├── model/          # Entidades JPA
│   │   │   ├── repository/     # Repositorios de datos
│   │   │   ├── service/        # Lógica de negocio
│   │   │   └── util/           # Utilidades
│   │   └── resources/
│   │       ├── static/         # Recursos estáticos
│   │       ├── templates/      # Plantillas Thymeleaf
│   │       └── application.properties
│   └── test/                   # Pruebas unitarias
├── docs/                       # Documentación
├── scripts/                    # Scripts de automatización
└── README.md
```

---

## 🚀 Mejoras Implementadas

### Versión 1.0 - Enero 2024

#### 🔧 Mejoras Técnicas
- ✅ **Migración a Spring Boot 3.x**
- ✅ **Configuración de seguridad mejorada**
- ✅ **Validación de datos robusta**
- ✅ **Manejo de errores centralizado**
- ✅ **Logging mejorado**

#### 🎨 Mejoras de Interfaz
- ✅ **Rediseño completo de la UI**
- ✅ **Diseño responsivo moderno**
- ✅ **Navegación mejorada**
- ✅ **Formularios validados**
- ✅ **Mensajes de usuario claros**

#### 🔐 Mejoras de Seguridad
- ✅ **Configuración de Spring Security**
- ✅ **Autenticación por roles**
- ✅ **Validación de entrada**
- ✅ **Protección CSRF**
- ✅ **Manejo seguro de sesiones**

#### 📊 Mejoras de Rendimiento
- ✅ **Optimización de consultas**
- ✅ **Caché implementado**
- ✅ **Compresión de respuestas**
- ✅ **Monitoreo de rendimiento**

#### 🧪 Mejoras de Pruebas
- ✅ **Suite de pruebas completa**
- ✅ **Pruebas de rendimiento**
- ✅ **Pruebas de seguridad**
- ✅ **Automatización de pruebas**

---

## 📞 Soporte y Contacto

### Información del Proyecto
- **Curso**: ASY5131 - Integración de Plataformas
- **Estudiante**: [Nombre del Estudiante]
- **Email**: [email@institucion.cl]
- **Repositorio**: https://github.com/TANIAHR02/ferremas_app.git

### Recursos Adicionales
- **Documentación API**: http://localhost:8080/swagger-ui.html
- **Base de Datos**: http://localhost:8080/h2-console
- **Reportes de Pruebas**: Ver archivos en `/docs/`

---

## 📄 Licencia

Este proyecto es desarrollado para fines educativos en el contexto del curso ASY5131 - Integración de Plataformas.

---

**Versión**: 1.0  
**Última actualización**: Enero 2024  
**Estado**: ✅ Completado y probado








