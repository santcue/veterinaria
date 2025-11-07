# 📁 Estructura Completa del Proyecto Nuclear

## 🏗️ Organización Profesional de Carpetas

```
VETERINARIA/
├── backend/                          # Microservicios Spring Boot WebFlux
│   ├── auth-service/                 # 🔐 Autenticación y JWT (Puerto 8080)
│   │   ├── src/main/java/co/edu/veterinaria/auth/
│   │   │   ├── domain/
│   │   │   │   ├── model/           # Usuario, Rol
│   │   │   │   ├── repository/      # UsuarioRepository
│   │   │   │   └── service/         # AuthService
│   │   │   └── infrastructure/
│   │   │       ├── security/        # JwtTokenProvider, PasswordEncoder
│   │   │       └── web/
│   │   │           ├── controller/  # AuthController
│   │   │           └── dto/         # LoginRequest
│   │   └── pom.xml
│   │
│   ├── mascotas-service/            # 🐾 CRUD Mascotas (Puerto 8081)
│   │   ├── src/main/java/co/edu/veterinaria/mascotas/
│   │   │   ├── domain/
│   │   │   │   ├── model/           # Mascota, Especie
│   │   │   │   ├── repository/      # MascotaRepository (Repository Pattern)
│   │   │   │   ├── service/         # MascotaService (Service Pattern)
│   │   │   │   ├── strategy/        # ValidacionMascotaStrategy (Strategy Pattern)
│   │   │   │   │   └── impl/        # ValidacionPerroStrategy, ValidacionGatoStrategy
│   │   │   │   └── exception/       # MascotaNotFoundException
│   │   │   └── infrastructure/
│   │   │       └── web/
│   │   │           ├── controller/  # MascotaController (Facade Pattern)
│   │   │           ├── dto/         # MascotaDTO
│   │   │           ├── mapper/      # MascotaMapper
│   │   │           └── exception/   # GlobalExceptionHandler
│   │   └── pom.xml
│   │
│   ├── propietarios-service/        # 👤 Propietarios (Puerto 8082)
│   │   ├── domain/
│   │   │   ├── factory/             # PropietarioFactory (Factory Method)
│   │   │   │   └── impl/            # PropietarioWebFactory, PropietarioRecepcionFactory
│   │   │   ├── observer/            # PropietarioObserver (Observer Pattern)
│   │   │   │   └── impl/             # HistoriaClinicaObserver, FacturacionObserver
│   │   │   └── service/             # PropietarioService
│   │   └── pom.xml
│   │
│   ├── citas-service/               # 📅 Citas (Puerto 8083)
│   │   ├── domain/
│   │   │   ├── strategy/            # AsignacionHorarioStrategy (Strategy Pattern)
│   │   │   │   └── impl/             # PrimerDisponibleStrategy, MismoVeterinarioStrategy
│   │   │   └── service/              # CitaService
│   │   └── pom.xml
│   │
│   ├── historia-service/            # 🩺 Historia Clínica (Puerto 8086)
│   │   ├── domain/
│   │   │   └── composite/           # Composite Pattern
│   │   │       ├── HistoriaComponent
│   │   │       ├── HistoriaClinicaComposite
│   │   │       └── ConsultaLeaf
│   │   └── pom.xml
│   │
│   ├── prescripciones-service/      # 💊 Prescripciones (Puerto 8087)
│   │   ├── domain/
│   │   │   ├── builder/             # PrescripcionBuilder (Builder Pattern)
│   │   │   └── model/                # Prescripcion, PrescripcionDetalle
│   │   └── pom.xml
│   │
│   ├── notificaciones-service/      # 🔔 Notificaciones (Puerto 8084)
│   │   ├── domain/
│   │   │   ├── strategy/            # NotificacionStrategy (Strategy Pattern)
│   │   │   │   └── impl/             # EmailStrategy, WhatsAppStrategy, InternaStrategy
│   │   │   └── service/              # NotificacionService
│   │   └── pom.xml
│   │
│   ├── estudiantes-service/         # 👨‍🎓 Estudiantes (Puerto 8085)
│   │   ├── domain/
│   │   │   ├── state/                # EstadoEstudiante (State Pattern)
│   │   │   │   └── impl/             # DisponibleState, OcupadoState
│   │   │   ├── observer/             # CitaObserver (Observer Pattern)
│   │   │   └── service/              # EstudianteService
│   │   └── pom.xml
│   │
│   ├── inventario-service/          # 📦 Inventario (Puerto 8088)
│   ├── pagos-service/               # 💳 Pagos y Facturación (Puerto 8089)
│   └── servicios-service/          # 💉 Servicios (Puerto 8090)
│
├── frontend/                        # React PWA
│   ├── src/
│   │   ├── pages/                   # Páginas de la aplicación
│   │   │   ├── LoginPage.tsx        # 🔐 Login
│   │   │   ├── DashboardPage.tsx    # 📊 Dashboard por perfil
│   │   │   ├── MascotasPage.tsx     # 🐾 Gestión de Mascotas
│   │   │   ├── PropietariosPage.tsx # 👤 Gestión de Propietarios
│   │   │   └── CitasPage.tsx        # 📅 Gestión de Citas
│   │   │
│   │   ├── components/              # Componentes reutilizables
│   │   │   ├── Layout.tsx           # Layout principal
│   │   │   ├── ProtectedRoute.tsx   # Rutas protegidas
│   │   │   └── NotificationCenter.tsx # 🔔 Centro de notificaciones
│   │   │
│   │   ├── contexts/                # Contextos de React
│   │   │   └── AuthContext.tsx      # Contexto de autenticación
│   │   │
│   │   ├── services/                # Servicios de API
│   │   │   ├── authService.ts       # Servicio de autenticación
│   │   │   ├── mascotaService.ts   # Servicio de mascotas
│   │   │   └── propietarioService.ts
│   │   │
│   │   └── patterns/                # Patrones de diseño
│   │       ├── mediator/            # FormMediator (Mediator Pattern)
│   │       └── factory/              # ComponentFactory (Abstract Factory)
│   │
│   ├── package.json
│   ├── vite.config.ts
│   └── tsconfig.json
│
├── database/                        # Scripts de base de datos
│   └── init/
│       ├── 01-schema.sql           # Esquema completo
│       └── 02-data.sql             # Datos iniciales
│
├── docker-compose.yml              # Configuración Docker
├── sonar-project.properties        # Configuración SonarQube
├── start-project.sh                # Script de inicio (Linux/Mac)
├── start-project.bat               # Script de inicio (Windows)
│
└── docs/                           # Documentación
    ├── README.md                   # Documentación principal
    ├── PROYECTO.md                 # Documentación técnica
    ├── INSTALL.md                  # Guía de instalación
    ├── INICIO.md                   # Guía de inicio rápido
    ├── SONARQUBE.md               # Análisis de calidad
    └── ESTRUCTURA_PROYECTO.md      # Este archivo
```

## 🎯 Patrones de Diseño por Módulo

### Backend

| Módulo | Patrones Implementados |
|--------|----------------------|
| **Mascotas** | Repository, Service, Strategy, Facade |
| **Propietarios** | Factory Method, Observer, Repository, Service |
| **Citas** | Strategy, Repository, Service |
| **Historia Clínica** | Composite, Repository, Service |
| **Prescripciones** | Builder, Repository, Service |
| **Notificaciones** | Strategy, Service |
| **Estudiantes** | State, Observer, Repository, Service |
| **Auth** | JWT, Security, Service |

### Frontend

| Componente | Patrones Implementados |
|-----------|----------------------|
| **Formularios** | Mediator Pattern |
| **Componentes** | Abstract Factory (Mobile/Desktop) |
| **Autenticación** | Context API, Protected Routes |
| **Notificaciones** | Observer Pattern (mediante Mediator) |

## 📊 Servicios y Puertos

| Servicio | Puerto | Estado |
|----------|--------|--------|
| Auth Service | 8080 | ✅ Completo |
| Mascotas Service | 8081 | ✅ Completo |
| Propietarios Service | 8082 | ✅ Completo |
| Citas Service | 8083 | ✅ Completo |
| Notificaciones Service | 8084 | ✅ Completo |
| Estudiantes Service | 8085 | ✅ Completo |
| Historia Service | 8086 | ⚠️ Parcial |
| Prescripciones Service | 8087 | ⚠️ Parcial |
| Inventario Service | 8088 | ⏳ Pendiente |
| Pagos Service | 8089 | ⏳ Pendiente |
| Servicios Service | 8090 | ⏳ Pendiente |
| Frontend | 3000 | ✅ Completo |

## 🔐 Perfiles de Usuario

1. **ADMIN** - Acceso completo al sistema
2. **VETERINARIO** - Gestión de citas, historias clínicas, prescripciones
3. **RECEPCIONISTA** - Gestión de citas, propietarios, pagos
4. **ESTUDIANTE** - Ver citas asignadas, registrar bitácora
5. **PROPIETARIO** - Ver información de sus mascotas

## 🚀 Inicio del Proyecto

Ver `INICIO.md` para instrucciones detalladas.

```bash
# Windows
start-project.bat

# Linux/Mac
./start-project.sh
```

## ✅ Checklist de Completitud

### Backend
- [x] Autenticación con JWT
- [x] Mascotas (Repository + Service + Strategy)
- [x] Propietarios (Factory Method + Observer)
- [x] Citas (Strategy)
- [x] Notificaciones (Strategy)
- [x] Estudiantes (State + Observer)
- [ ] Historia Clínica completa (Composite)
- [ ] Prescripciones completa (Builder)
- [ ] Inventario
- [ ] Pagos y Facturación
- [ ] Servicios (Factory Method)

### Frontend
- [x] Login y autenticación
- [x] Dashboard por perfil
- [x] Sistema de notificaciones
- [x] Vistas de Mascotas
- [x] Vistas de Propietarios
- [x] Vistas de Citas
- [ ] Vistas de Historia Clínica
- [ ] Vistas de Prescripciones
- [ ] Vistas de Estudiantes
- [ ] Vistas de Inventario
- [ ] Vistas de Pagos

## 📝 Notas

- Todos los servicios usan Spring Boot WebFlux (reactivo)
- Base de datos: MySQL 8.0 con R2DBC
- Message Broker: RabbitMQ para eventos asíncronos
- Frontend: React 18 + TypeScript + Vite + TailwindCSS
- Autenticación: JWT tokens
- Patrones de diseño aplicados según especificaciones

