# Proyecto Nuclear - Documentación Técnica

## Estructura del Proyecto

El proyecto está organizado en microservicios backend y una aplicación frontend PWA.

### Backend (Microservicios Spring Boot WebFlux)

#### 1. Mascotas Service (Puerto 8081)
- **Patrones**: Repository + Service + Strategy
- **Endpoints**:
  - `GET /api/mascotas` - Listar todas las mascotas
  - `GET /api/mascotas/{id}` - Obtener mascota por ID
  - `POST /api/mascotas` - Crear nueva mascota
  - `PUT /api/mascotas/{id}` - Actualizar mascota
  - `DELETE /api/mascotas/{id}` - Eliminar (desactivar) mascota

#### 2. Propietarios Service (Puerto 8082)
- **Patrones**: Factory Method + Observer
- **Endpoints**:
  - `GET /api/propietarios` - Listar todos
  - `POST /api/propietarios?origen=WEB|RECEPCION` - Crear propietario
  - `PUT /api/propietarios/{id}` - Actualizar
  - `DELETE /api/propietarios/{id}` - Eliminar

#### 3. Citas Service (Puerto 8083)
- **Patrones**: Strategy (Asignación de horarios)
- **Endpoints**:
  - `GET /api/citas` - Listar citas
  - `POST /api/citas?estrategia=PRIMER_DISPONIBLE|MISMO_VETERINARIO` - Crear cita
  - `PATCH /api/citas/{id}/confirmar` - Confirmar cita

### Frontend (React PWA)

- **Patrones**: Mediator (coordinación de formularios), Abstract Factory (componentes por dispositivo)
- **Puerto**: 3000
- **Tecnologías**: React 18, TypeScript, Vite, TailwindCSS, React Query

## Patrones de Diseño Implementados

### Creacionales
1. **Factory Method**: Propietarios (Web/Recepción), Servicios
2. **Builder**: Prescripciones
3. **Abstract Factory**: Frontend (Mobile/Desktop)

### Estructurales
1. **Facade**: Controllers REST
2. **Composite**: Historia Clínica
3. **Adapter**: (Pendiente - Integraciones externas)
4. **Proxy**: (Pendiente - Seguridad)

### Comportamentales
1. **Strategy**: Validación Mascotas, Asignación Horarios, Notificaciones
2. **Observer**: Propietarios, Estudiantes, Bitácora
3. **State**: Disponibilidad Estudiantes
4. **Command**: Seguimiento Clientes
5. **Mediator**: Frontend (Formularios)
6. **Chain of Responsibility**: (Pendiente - Seguridad)
7. **Template Method**: (Pendiente - Evaluación Estudiantes)

## Instalación y Ejecución

### Prerrequisitos
- JDK 21
- Node.js 18+
- Docker & Docker Compose
- Maven 3.8+

### Pasos

1. **Iniciar servicios de infraestructura**:
```bash
docker-compose up -d
```

2. **Inicializar base de datos**:
```bash
# Los scripts SQL se ejecutan automáticamente al iniciar MySQL
```

3. **Compilar y ejecutar microservicios**:
```bash
cd backend/mascotas-service
./mvnw spring-boot:run

# En otra terminal
cd backend/propietarios-service
./mvnw spring-boot:run

# En otra terminal
cd backend/citas-service
./mvnw spring-boot:run
```

4. **Ejecutar frontend**:
```bash
cd frontend
npm install
npm run dev
```

## Pruebas

### Backend
```bash
cd backend/mascotas-service
./mvnw test
```

### Frontend
```bash
cd frontend
npm test
```

## Versionamiento

El proyecto utiliza Git para control de versiones. Se recomienda seguir Semantic Versioning (MAJOR.MINOR.PATCH).

## Próximos Pasos

1. Implementar módulo de Seguridad (Proxy + Chain of Responsibility)
2. Completar módulo de Estudiantes (Observer + State)
3. Implementar módulo de Notificaciones (Strategy)
4. Agregar más pruebas unitarias e integrales
5. Configurar CI/CD
6. Documentar APIs con Swagger/OpenAPI

