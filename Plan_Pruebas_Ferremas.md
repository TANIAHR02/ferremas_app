# Plan de Pruebas - Sistema Ferremas
## ASY5131 - Integración de Plataformas

---

## 📋 Tabla de Contenidos

1. [Información del Proyecto](#1-información-del-proyecto)
2. [Objetivos de las Pruebas](#2-objetivos-de-las-pruebas)
3. [Alcance de las Pruebas](#3-alcance-de-las-pruebas)
4. [Estrategia de Pruebas](#4-estrategia-de-pruebas)
5. [Recursos y Herramientas](#5-recursos-y-herramientas)
6. [Cronograma de Pruebas](#6-cronograma-de-pruebas)
7. [Criterios de Aceptación](#7-criterios-de-aceptación)
8. [Riesgos y Mitigaciones](#8-riesgos-y-mitigaciones)
9. [Entregables](#9-entregables)
10. [Anexos](#10-anexos)

---

## 1. Información del Proyecto

### 1.1 Datos Generales
- **Nombre del Proyecto**: Sistema de Gestión Ferremas
- **Versión**: 1.0
- **Cliente**: ASY5131 - Integración de Plataformas
- **Fecha de Inicio**: Enero 2024
- **Fecha de Finalización**: Enero 2024
- **Líder del Proyecto**: [Nombre del Estudiante]
- **Equipo de Pruebas**: [Nombre del Estudiante]

### 1.2 Descripción del Sistema
El Sistema Ferremas es una aplicación web desarrollada en Spring Boot que permite la gestión integral de una ferretería, incluyendo:

- **Gestión de Usuarios**: Registro, autenticación y administración de usuarios
- **Catálogo de Productos**: CRUD completo de productos con categorías y marcas
- **Gestión de Pedidos**: Creación y seguimiento de pedidos de clientes
- **Control de Stock**: Monitoreo y actualización de inventario
- **Sistema de Pagos**: Integración con Transbank para procesamiento de pagos
- **Interfaz Web**: Frontend responsivo con Thymeleaf

### 1.3 Arquitectura Técnica
- **Backend**: Spring Boot 3.x
- **Base de Datos**: H2 (desarrollo) / PostgreSQL (producción)
- **Frontend**: Thymeleaf + CSS + JavaScript
- **Seguridad**: Spring Security
- **API**: RESTful con documentación Swagger
- **Pagos**: Integración Transbank WebPay

---

## 2. Objetivos de las Pruebas

### 2.1 Objetivo Principal
Validar que el Sistema Ferremas cumple con todos los requisitos funcionales, de rendimiento y seguridad establecidos, asegurando su correcto funcionamiento antes de la entrega final.

### 2.2 Objetivos Específicos

#### Funcionales
- ✅ Verificar que todos los módulos del sistema funcionan correctamente
- ✅ Validar la integración entre componentes
- ✅ Confirmar el cumplimiento de requisitos de negocio
- ✅ Asegurar la correcta gestión de errores

#### Rendimiento
- ✅ Evaluar el comportamiento bajo carga normal (10 usuarios)
- ✅ Probar el rendimiento bajo carga media (50 usuarios)
- ✅ Analizar el comportamiento bajo carga alta (100 usuarios)
- ✅ Identificar cuellos de botella y optimizaciones

#### Seguridad
- ✅ Validar la autenticación y autorización
- ✅ Verificar la protección contra ataques comunes
- ✅ Confirmar el manejo seguro de datos sensibles
- ✅ Probar la validación de entrada de datos

#### Usabilidad
- ✅ Verificar la navegación y funcionalidad de la interfaz web
- ✅ Validar la responsividad en diferentes dispositivos
- ✅ Confirmar la claridad de mensajes y errores
- ✅ Asegurar la accesibilidad básica

---

## 3. Alcance de las Pruebas

### 3.1 Funcionalidades Incluidas

#### Módulo de Autenticación
- [x] Registro de usuarios
- [x] Inicio de sesión
- [x] Gestión de roles (ADMIN, CLIENTE)
- [x] Validación de credenciales

#### Módulo de Productos
- [x] Listado de productos
- [x] Creación de productos
- [x] Actualización de productos
- [x] Eliminación lógica de productos
- [x] Búsqueda y filtrado
- [x] Gestión de categorías y marcas

#### Módulo de Pedidos
- [x] Creación de pedidos
- [x] Gestión del carrito de compras
- [x] Validación de stock
- [x] Seguimiento de estado
- [x] Historial de pedidos

#### Módulo de Stock
- [x] Consulta de stock disponible
- [x] Actualización manual de stock
- [x] Alertas de stock bajo
- [x] Reserva automática en pedidos

#### Módulo de Pagos
- [x] Integración con Transbank
- [x] Creación de transacciones
- [x] Procesamiento de pagos
- [x] Seguimiento de estado de pago

#### Interfaz Web
- [x] Páginas principales (Home, Login, Registro)
- [x] Catálogo de productos
- [x] Carrito de compras
- [x] Perfil de usuario
- [x] Páginas informativas

### 3.2 Funcionalidades Excluidas
- [ ] Reportes avanzados
- [ ] Notificaciones por email
- [ ] Integración con sistemas externos (excepto Transbank)
- [ ] Aplicación móvil
- [ ] Funcionalidades de administración avanzada

---

## 4. Estrategia de Pruebas

### 4.1 Tipos de Pruebas

#### Pruebas Funcionales
- **Objetivo**: Verificar que cada funcionalidad cumple con los requisitos
- **Cobertura**: 100% de los casos de uso principales
- **Herramientas**: Postman, curl, Swagger UI
- **Criterios**: Todos los casos de prueba deben pasar

#### Pruebas de Rendimiento
- **Objetivo**: Evaluar el comportamiento bajo diferentes cargas
- **Escenarios**: 10, 50 y 100 usuarios concurrentes
- **Herramientas**: Apache JMeter
- **Métricas**: Tiempo de respuesta, throughput, tasa de error

#### Pruebas de Seguridad
- **Objetivo**: Validar la protección del sistema
- **Áreas**: Autenticación, autorización, validación de entrada
- **Herramientas**: Pruebas manuales, análisis de código
- **Criterios**: No vulnerabilidades críticas

#### Pruebas de Interfaz
- **Objetivo**: Verificar la usabilidad y funcionalidad web
- **Cobertura**: Todas las páginas principales
- **Herramientas**: Navegadores web, pruebas manuales
- **Criterios**: Funcionalidad completa y responsiva

### 4.2 Metodología de Pruebas

#### Enfoque de Pruebas
1. **Pruebas Unitarias**: Validación de componentes individuales
2. **Pruebas de Integración**: Verificación de interacción entre módulos
3. **Pruebas de Sistema**: Validación del sistema completo
4. **Pruebas de Aceptación**: Verificación de requisitos de usuario

#### Estrategia de Ejecución
1. **Preparación**: Configuración del entorno y datos de prueba
2. **Ejecución**: Aplicación sistemática de casos de prueba
3. **Análisis**: Evaluación de resultados y identificación de defectos
4. **Reporte**: Documentación de hallazgos y recomendaciones

---

## 5. Recursos y Herramientas

### 5.1 Equipo de Pruebas
- **Líder de Pruebas**: [Nombre del Estudiante]
- **Analista de Pruebas**: [Nombre del Estudiante]
- **Técnico de Pruebas**: [Nombre del Estudiante]

### 5.2 Herramientas de Pruebas

#### Funcionales
- **Postman**: Pruebas de API REST
- **Swagger UI**: Documentación y pruebas de endpoints
- **curl**: Pruebas desde línea de comandos
- **Navegadores Web**: Chrome, Firefox, Edge

#### Rendimiento
- **Apache JMeter**: Pruebas de carga y rendimiento
- **JConsole**: Monitoreo de JVM
- **H2 Console**: Monitoreo de base de datos

#### Desarrollo
- **IntelliJ IDEA**: IDE principal
- **Git**: Control de versiones
- **Maven**: Gestión de dependencias

### 5.3 Entorno de Pruebas

#### Hardware
- **Servidor de Pruebas**: 
  - CPU: Intel i5 o superior
  - RAM: 8GB mínimo
  - Almacenamiento: 256GB SSD
- **Cliente de Pruebas**:
  - CPU: Intel i3 o superior
  - RAM: 4GB mínimo
  - Conexión: Red local o internet estable

#### Software
- **Sistema Operativo**: Windows 10/11, macOS, Linux
- **Java**: JDK 17 o superior
- **Base de Datos**: H2 embebida
- **Navegadores**: Chrome 90+, Firefox 88+, Edge 90+

---

## 6. Cronograma de Pruebas

### 6.1 Fases de Pruebas

#### Fase 1: Preparación (1 día)
- [x] Configuración del entorno de pruebas
- [x] Preparación de datos de prueba
- [x] Configuración de herramientas
- [x] Revisión de documentación

#### Fase 2: Pruebas Funcionales (2 días)
- [x] Pruebas de autenticación
- [x] Pruebas de gestión de productos
- [x] Pruebas de gestión de pedidos
- [x] Pruebas de control de stock
- [x] Pruebas de sistema de pagos

#### Fase 3: Pruebas de Interfaz (1 día)
- [x] Pruebas de navegación web
- [x] Pruebas de responsividad
- [x] Pruebas de usabilidad
- [x] Validación de formularios

#### Fase 4: Pruebas de Rendimiento (1 día)
- [x] Configuración de JMeter
- [x] Pruebas con 10 usuarios
- [x] Pruebas con 50 usuarios
- [x] Pruebas con 100 usuarios
- [x] Análisis de resultados

#### Fase 5: Pruebas de Seguridad (1 día)
- [x] Validación de autenticación
- [x] Pruebas de autorización
- [x] Validación de entrada de datos
- [x] Pruebas de inyección SQL

#### Fase 6: Análisis y Reporte (1 día)
- [x] Consolidación de resultados
- [x] Análisis de defectos
- [x] Generación de reportes
- [x] Recomendaciones

### 6.2 Cronograma Detallado

| Fase | Actividad | Duración | Responsable | Estado |
|------|-----------|----------|-------------|--------|
| **Fase 1** | Preparación | 1 día | [Estudiante] | ✅ Completado |
| **Fase 2** | Funcionales | 2 días | [Estudiante] | ✅ Completado |
| **Fase 3** | Interfaz | 1 día | [Estudiante] | ✅ Completado |
| **Fase 4** | Rendimiento | 1 día | [Estudiante] | ✅ Completado |
| **Fase 5** | Seguridad | 1 día | [Estudiante] | ✅ Completado |
| **Fase 6** | Reporte | 1 día | [Estudiante] | ✅ Completado |

**Total**: 7 días

---

## 7. Criterios de Aceptación

### 7.1 Criterios Funcionales
- ✅ Todos los endpoints de la API responden correctamente
- ✅ Las operaciones CRUD funcionan en todos los módulos
- ✅ La integración entre módulos es correcta
- ✅ El manejo de errores es consistente y apropiado
- ✅ La validación de datos previene entradas incorrectas

### 7.2 Criterios de Rendimiento
- ✅ Tiempo de respuesta promedio < 2 segundos (carga normal)
- ✅ Tiempo de respuesta p95 < 5 segundos (carga normal)
- ✅ Throughput > 10 transacciones por segundo
- ✅ Tasa de error < 1% en condiciones normales
- ✅ El sistema no colapsa bajo carga de 100 usuarios

### 7.3 Criterios de Seguridad
- ✅ La autenticación es requerida para recursos protegidos
- ✅ La autorización por roles funciona correctamente
- ✅ Los datos sensibles no se exponen en respuestas
- ✅ La validación de entrada previene ataques comunes
- ✅ Las sesiones se manejan de forma segura

### 7.4 Criterios de Usabilidad
- ✅ La interfaz web es responsiva en dispositivos móviles
- ✅ La navegación es intuitiva y consistente
- ✅ Los mensajes de error son claros y útiles
- ✅ Los formularios validan entrada en tiempo real
- ✅ La funcionalidad es accesible para usuarios básicos

---

## 8. Riesgos y Mitigaciones

### 8.1 Riesgos Identificados

#### Riesgos Técnicos
| Riesgo | Probabilidad | Impacto | Mitigación |
|--------|--------------|---------|------------|
| Problemas de configuración del entorno | Media | Alto | Configuración temprana y documentación detallada |
| Incompatibilidades de versiones | Baja | Medio | Uso de versiones estables y probadas |
| Problemas de rendimiento | Media | Alto | Pruebas de rendimiento tempranas |
| Errores en integración | Media | Alto | Pruebas de integración incrementales |

#### Riesgos de Recursos
| Riesgo | Probabilidad | Impacto | Mitigación |
|--------|--------------|---------|------------|
| Falta de tiempo | Alta | Alto | Planificación detallada y priorización |
| Limitaciones de hardware | Media | Medio | Optimización de recursos |
| Falta de experiencia | Media | Medio | Documentación y capacitación |

#### Riesgos de Calidad
| Riesgo | Probabilidad | Impacto | Mitigación |
|--------|--------------|---------|------------|
| Cobertura insuficiente | Media | Alto | Casos de prueba detallados |
| Defectos no detectados | Baja | Alto | Múltiples tipos de pruebas |
| Criterios poco claros | Baja | Medio | Definición clara de criterios |

### 8.2 Plan de Contingencia
- **Escenario 1**: Retrasos en el cronograma
  - **Acción**: Priorización de pruebas críticas
  - **Responsable**: Líder de pruebas

- **Escenario 2**: Problemas técnicos graves
  - **Acción**: Escalación inmediata y búsqueda de alternativas
  - **Responsable**: Equipo técnico

- **Escenario 3**: Cambios en requisitos
  - **Acción**: Evaluación de impacto y ajuste del plan
  - **Responsable**: Líder del proyecto

---

## 9. Entregables

### 9.1 Documentos de Pruebas
- [x] **Plan de Pruebas** (este documento)
- [x] **Casos de Prueba Detallados** (`Detalle_Casos_Prueba_Ferremas.md`)
- [x] **Informe de Pruebas** (`Informe_Pruebas_Ferremas.md`)
- [x] **Guía de JMeter** (`Guia_JMeter_Ferremas.md`)
- [x] **Comandos de Pruebas** (`Comandos_JMeter_Ferremas.md`)

### 9.2 Artefactos de Pruebas
- [x] **Archivo JMeter** (`ferremas-performance-test.jmx`)
- [x] **Scripts de Pruebas** (`curl-commands.sh`)
- [x] **Colección Postman** (exportada)
- [x] **Datos de Prueba** (incluidos en documentación)

### 9.3 Reportes de Resultados
- [x] **Reporte de Pruebas Funcionales**
- [x] **Reporte de Pruebas de Rendimiento**
- [x] **Reporte de Pruebas de Seguridad**
- [x] **Reporte de Defectos**
- [x] **Recomendaciones de Mejora**

### 9.4 Repositorio del Proyecto
- [x] **Código fuente actualizado**
- [x] **README actualizado con mejoras**
- [x] **Documentación técnica**
- [x] **Configuraciones de seguridad**

---

## 10. Anexos

### 10.1 Glosario de Términos

| Término | Definición |
|---------|------------|
| **API** | Application Programming Interface - Interfaz de programación de aplicaciones |
| **CRUD** | Create, Read, Update, Delete - Operaciones básicas de datos |
| **JMeter** | Herramienta de Apache para pruebas de rendimiento |
| **REST** | Representational State Transfer - Estilo de arquitectura para APIs |
| **Swagger** | Herramienta para documentación de APIs |
| **Thymeleaf** | Motor de plantillas para Java |
| **WebPay** | Sistema de pagos de Transbank |

### 10.2 Referencias
- [Documentación Spring Boot](https://spring.io/projects/spring-boot)
- [Guía de JMeter](https://jmeter.apache.org/usermanual/index.html)
- [Documentación Swagger](https://swagger.io/docs/)
- [Transbank WebPay](https://www.transbankdevelopers.cl/)

### 10.3 Contactos
- **Líder del Proyecto**: [Nombre del Estudiante]
- **Email**: [email@institucion.cl]
- **Teléfono**: [Número de contacto]

---

**Documento preparado para**: ASY5131 - Integración de Plataformas  
**Sistema**: Ferremas  
**Versión**: 1.0  
**Fecha**: Enero 2024  
**Estado**: Aprobado 