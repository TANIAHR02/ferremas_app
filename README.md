Descripción General
FerremasApp es una aplicación integral desarrollada en Java + Spring Boot, con integración de MySQL, que permite gestionar clientes, productos, pedidos, pagos, y más, para una distribuidora ferretera.

Instrucciones de Ejecución
Clona este repositorio:
git clone https://github.com/TANIAHR02/ferremas_app.git
cd ferremasapp

Configura la base de datos MySQL en XAMPP:

Crea la base ferremas_db.

Ejecuta el script ferremas_db.sql para crear las tablas.

Configura el archivo application.properties:
properties
spring.datasource.url=jdbc:mysql://localhost:3306/ferremas_db?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=

### 3. Instalar Dependencias y Ejecutar
```bash
# Instalar dependencias
./mvnw clean install

# Ejecutar la aplicación
./mvnw spring-boot:run

Accede a la aplicación:

 Navegador: http://localhost:8080
 Documentación API: http://localhost:8080/swagger-ui.html

 Modelo de Datos
El modelo de datos está implementado en src/main/resources/ferremas_db.sql. 
Contiene las tablas de usuarios, roles, productos, pedidos, pagos, etc.
Incluye relaciones y claves foráneas.

Documentación API / Webservice
La documentación completa se genera automáticamente mediante Swagger / OpenAPI. Accede en: http://localhost:8080/swagger-ui.html
Incluye:

Endpoints CRUD para Usuarios, Productos, Pedidos, Pagos, Mensajes.

Métodos de autenticación, carrito de compras, pedidos y pagos.

Colección de Postman
Se incluye una colección Postman (FerremasApp.postman_collection.json) para realizar pruebas automatizadas de la API.

Login, registro, operaciones CRUD, pedidos, pagos, integración WebPay y Banco Central.

Integraciones
WebPay: Simulación de pagos a través de la API oficial de Transbank.

Banco Central: Conversión de divisas en tiempo real, usando la API oficial del Banco Central de Chile.

Interfaz Gráfica
La aplicación incluye vistas sencillas en HTML + CSS para:

Inicio de sesión y registro.

Catálogo de productos.

Carrito de compras.

Perfil de usuario.
Ubicadas en:

src/main/resources/templates/
src/main/resources/static/css/
src/main/resources/static/img/

Modelo 4+1 de Arquitectura

🔹 Vista Lógica:

Arquitectura en capas: Controladores REST, Servicios, Repositorios, Modelos.

🔹 Vista de Desarrollo:

Proyecto en Java + Spring Boot.

Dependencias gestionadas por Maven.

Estructura modular organizada por paquetes.

🔹 Vista de Procesos:

Servicios REST asíncronos.

Controladores web gestionan las rutas de la aplicación.

Integración con WebPay y Banco Central.

🔹 Vista Física:

Servidor local Tomcat en puerto 8080.

Base de datos MySQL en XAMPP.

Dependencias externas descargadas mediante Maven.

🔹 Vista de Escenarios:

Cliente realiza compras → WebPay gestiona pagos → Confirmación vía email.

Cliente consulta divisas → API Banco Central proporciona tasas actualizadas.








