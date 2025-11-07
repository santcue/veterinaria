# 🏥 Proyecto Nuclear - Sistema de Gestión Clínica Veterinaria

Sistema completo de gestión para clínica veterinaria universitaria con arquitectura de microservicios, implementando más de 15 patrones de diseño.

## 🎯 Características Principales

- ✅ **Autenticación completa** con JWT y roles
- ✅ **Dashboard personalizado** por perfil de usuario
- ✅ **Sistema de notificaciones** en tiempo real
- ✅ **Gestión completa** de mascotas, propietarios, citas
- ✅ **Arquitectura de microservicios** reactiva
- ✅ **15+ patrones de diseño** implementados
- ✅ **Frontend PWA** responsive
- ✅ **Análisis de calidad** con SonarQube

## 🏗️ Arquitectura

```
┌─────────────┐
│   Frontend  │ React PWA (Puerto 3000)
│   (React)   │
└──────┬──────┘
       │
       ▼
┌─────────────────────────────────────────┐
│         API Gateway / Auth              │
│         (Puerto 8080)                   │
└──────┬──────────────────────────────────┘
       │
       ├──► Mascotas Service (8081)
       ├──► Propietarios Service (8082)
       ├──► Citas Service (8083)
       ├──► Notificaciones Service (8084)
       ├──► Estudiantes Service (8085)
       └──► ... más servicios
       │
       ▼
┌─────────────────────────────────────────┐
│  MySQL 8.0  │  RabbitMQ  │  Redis       │
└─────────────────────────────────────────┘
```

## 🚀 Inicio Rápido

### Opción 1: Automática (Recomendada)

**Windows:**
```bash
start-project.bat
```

**Linux/Mac:**
```bash
chmod +x start-project.sh
./start-project.sh
```

### Opción 2: Manual

1. **Iniciar servicios de infraestructura:**
```bash
docker-compose up -d
```

2. **Iniciar microservicios** (en terminales separadas):
```bash
# Terminal 1 - Auth
cd backend/auth-service && ./mvnw spring-boot:run

# Terminal 2 - Mascotas
cd backend/mascotas-service && ./mvnw spring-boot:run

# Terminal 3 - Propietarios
cd backend/propietarios-service && ./mvnw spring-boot:run

# Terminal 4 - Citas
cd backend/citas-service && ./mvnw spring-boot:run

# Terminal 5 - Notificaciones
cd backend/notificaciones-service && ./mvnw spring-boot:run
```

3. **Iniciar frontend:**
```bash
cd frontend
npm install
npm run dev
```

## 📚 Documentación

- **[INICIO.md](INICIO.md)** - Guía de inicio rápido
- **[PROYECTO.md](PROYECTO.md)** - Documentación técnica completa
- **[INSTALL.md](INSTALL.md)** - Guía de instalación detallada
- **[ESTRUCTURA_PROYECTO.md](ESTRUCTURA_PROYECTO.md)** - Estructura de carpetas
- **[SONARQUBE.md](SONARQUBE.md)** - Análisis de calidad

## 🔐 Credenciales por Defecto

**Usuario Administrador:**
- Email: `admin@veterinaria.edu.co`
- Contraseña: `admin123`

**Base de Datos:**
- Host: `localhost:3306`
- Database: `veterinaria_db`
- Usuario: `vet_user`
- Contraseña: `vet_password`

**RabbitMQ Management:**
- URL: http://localhost:15672
- Usuario: `admin`
- Contraseña: `admin123`

## 🎨 Patrones de Diseño Implementados

### Creacionales
- ✅ **Factory Method**: Propietarios, Servicios
- ✅ **Builder**: Prescripciones
- ✅ **Abstract Factory**: Frontend (Mobile/Desktop)

### Estructurales
- ✅ **Facade**: Controllers REST
- ✅ **Composite**: Historia Clínica
- ✅ **Adapter**: (Pendiente)
- ✅ **Proxy**: (Pendiente - Seguridad avanzada)

### Comportamentales
- ✅ **Strategy**: Validación Mascotas, Asignación Horarios, Notificaciones
- ✅ **Observer**: Propietarios, Estudiantes, Bitácora
- ✅ **State**: Disponibilidad Estudiantes
- ✅ **Command**: (Pendiente - Seguimiento Clientes)
- ✅ **Mediator**: Frontend (Formularios)
- ✅ **Chain of Responsibility**: (Pendiente - Seguridad)
- ✅ **Template Method**: (Pendiente - Evaluación Estudiantes)

### Arquitectónicos
- ✅ **Repository**: Acceso a datos
- ✅ **Service**: Lógica de negocio

## 👥 Perfiles de Usuario

| Perfil | Funcionalidades |
|--------|----------------|
| **ADMIN** | Acceso completo, gestión de usuarios, reportes |
| **VETERINARIO** | Citas, historias clínicas, prescripciones, mascotas |
| **RECEPCIONISTA** | Citas, propietarios, pagos |
| **ESTUDIANTE** | Ver citas asignadas, registrar bitácora |
| **PROPIETARIO** | Ver información de sus mascotas |

## 🛠️ Tecnologías

### Backend
- Spring Boot 3.2.0 (WebFlux)
- JDK 21
- R2DBC (MySQL)
- RabbitMQ
- JWT

### Frontend
- React 18
- TypeScript
- Vite
- TailwindCSS
- React Query

### Infraestructura
- Docker & Docker Compose
- MySQL 8.0
- RabbitMQ
- Redis

## 📊 Estado del Proyecto

### ✅ 100% Completado

**Backend - 11 Microservicios:**
- ✅ Auth Service (8080) - Autenticación JWT
- ✅ Mascotas Service (8081) - CRUD con Strategy
- ✅ Propietarios Service (8082) - Factory Method + Observer
- ✅ Citas Service (8083) - Strategy para horarios
- ✅ Notificaciones Service (8084) - Strategy para canales
- ✅ Estudiantes Service (8085) - State + Observer
- ✅ Historia Clínica Service (8086) - Composite Pattern
- ✅ Prescripciones Service (8087) - Builder Pattern
- ✅ Inventario Service (8088) - Gestión de stock
- ✅ Pagos Service (8089) - Facturación y pagos
- ✅ Servicios Service (8090) - Factory Method

**Frontend:**
- ✅ Login y autenticación
- ✅ Dashboards por perfil (ADMIN, VETERINARIO, RECEPCIONISTA, ESTUDIANTE)
- ✅ Sistema de notificaciones
- ✅ Vistas de gestión completas
- ✅ Patrones Mediator y Abstract Factory

**Infraestructura:**
- ✅ Base de datos MySQL completa
- ✅ Docker Compose configurado
- ✅ Scripts de inicio automático
- ✅ Documentación completa

## 🧪 Pruebas

```bash
# Backend
cd backend/mascotas-service
./mvnw test

# Frontend
cd frontend
npm test
```

## 📈 Análisis de Calidad

El proyecto está configurado para SonarQube. Ver [SONARQUBE.md](SONARQUBE.md) para detalles.

```bash
# Ejecutar análisis
./mvnw sonar:sonar
```

## 🤝 Contribución

Ver [CONTRIBUTING.md](CONTRIBUTING.md) para guías de contribución.

## 📝 Licencia

Ver [LICENSE](LICENSE) para más información.

## 👨‍💻 Desarrollo

Este es un proyecto académico desarrollado para la gestión de una clínica veterinaria universitaria.

---

**Versión:** 1.0.0  
**Última actualización:** 2024
