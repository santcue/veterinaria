-- Proyecto Nuclear - Base de Datos
-- Sistema de Gestión Clínica Veterinaria

CREATE DATABASE IF NOT EXISTS veterinaria_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE veterinaria_db;

-- Tabla: Especie
CREATE TABLE IF NOT EXISTS especie (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(30) NOT NULL UNIQUE,
    activo BOOLEAN DEFAULT TRUE,
    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla: Raza
CREATE TABLE IF NOT EXISTS raza (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_especie BIGINT NOT NULL,
    nombre VARCHAR(80) NOT NULL,
    activo BOOLEAN DEFAULT TRUE,
    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_especie) REFERENCES especie(id) ON DELETE RESTRICT,
    INDEX idx_especie (id_especie)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla: Propietario
CREATE TABLE IF NOT EXISTS propietario (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    documento VARCHAR(30) NOT NULL UNIQUE,
    nombres VARCHAR(120) NOT NULL,
    apellidos VARCHAR(120) NOT NULL,
    email VARCHAR(120) NOT NULL,
    telefono VARCHAR(20),
    direccion VARCHAR(200),
    activo BOOLEAN DEFAULT TRUE,
    fecha_registro DATETIME DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_documento (documento),
    INDEX idx_email (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla: Mascota
CREATE TABLE IF NOT EXISTS mascota (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_propietario BIGINT NOT NULL,
    id_especie BIGINT NOT NULL,
    id_raza BIGINT,
    nombre VARCHAR(80) NOT NULL,
    fecha_nacimiento DATE,
    sexo ENUM('M', 'H') NOT NULL,
    color VARCHAR(60),
    microchip VARCHAR(80),
    peso_kg DECIMAL(5,2),
    esterilizado BOOLEAN DEFAULT FALSE,
    activo BOOLEAN DEFAULT TRUE,
    fecha_registro DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_propietario) REFERENCES propietario(id) ON DELETE RESTRICT,
    FOREIGN KEY (id_especie) REFERENCES especie(id) ON DELETE RESTRICT,
    FOREIGN KEY (id_raza) REFERENCES raza(id) ON DELETE SET NULL,
    INDEX idx_propietario (id_propietario),
    INDEX idx_especie (id_especie),
    INDEX idx_nombre (nombre)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla: Rol
CREATE TABLE IF NOT EXISTS rol (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(30) NOT NULL UNIQUE,
    descripcion VARCHAR(200),
    activo BOOLEAN DEFAULT TRUE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla: Usuario
CREATE TABLE IF NOT EXISTS usuario (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(120) NOT NULL,
    email VARCHAR(120) NOT NULL UNIQUE,
    telefono VARCHAR(20),
    hash_password VARCHAR(255) NOT NULL,
    activo BOOLEAN DEFAULT TRUE,
    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_email (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla: UsuarioRol
CREATE TABLE IF NOT EXISTS usuario_rol (
    id_usuario BIGINT NOT NULL,
    id_rol BIGINT NOT NULL,
    fecha_asignacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id_usuario, id_rol),
    FOREIGN KEY (id_usuario) REFERENCES usuario(id) ON DELETE CASCADE,
    FOREIGN KEY (id_rol) REFERENCES rol(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla: Servicio
CREATE TABLE IF NOT EXISTS servicio (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(220) NOT NULL,
    descripcion TEXT,
    precio_base DECIMAL(12,2) NOT NULL DEFAULT 0.00,
    duracion_min INT NOT NULL DEFAULT 30,
    activo BOOLEAN DEFAULT TRUE,
    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_nombre (nombre)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla: EstadoCita
CREATE TABLE IF NOT EXISTS estado_cita (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    codigo VARCHAR(20) NOT NULL UNIQUE,
    descripcion VARCHAR(100),
    activo BOOLEAN DEFAULT TRUE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla: Cita
CREATE TABLE IF NOT EXISTS cita (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_mascota BIGINT NOT NULL,
    id_servicio BIGINT NOT NULL,
    id_estado BIGINT NOT NULL,
    id_veterinario BIGINT,
    id_creada_por BIGINT NOT NULL,
    fecha_hora DATETIME NOT NULL,
    motivo VARCHAR(250),
    prioridad ENUM('LOW', 'MEDIUM', 'HIGH') DEFAULT 'MEDIUM',
    observaciones TEXT,
    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_mascota) REFERENCES mascota(id) ON DELETE RESTRICT,
    FOREIGN KEY (id_servicio) REFERENCES servicio(id) ON DELETE RESTRICT,
    FOREIGN KEY (id_estado) REFERENCES estado_cita(id) ON DELETE RESTRICT,
    FOREIGN KEY (id_veterinario) REFERENCES usuario(id) ON DELETE SET NULL,
    FOREIGN KEY (id_creada_por) REFERENCES usuario(id) ON DELETE RESTRICT,
    INDEX idx_mascota (id_mascota),
    INDEX idx_fecha_hora (fecha_hora),
    INDEX idx_veterinario (id_veterinario),
    INDEX idx_estado (id_estado)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla: EstudiantePractica
CREATE TABLE IF NOT EXISTS estudiante_practica (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_usuario BIGINT NOT NULL,
    codigo VARCHAR(30) NOT NULL UNIQUE,
    semestre INT NOT NULL,
    programa VARCHAR(120) NOT NULL,
    tutor_academico VARCHAR(120),
    fecha_inicio DATE NOT NULL,
    fecha_fin DATE,
    activo BOOLEAN DEFAULT TRUE,
    fecha_registro DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_usuario) REFERENCES usuario(id) ON DELETE RESTRICT,
    INDEX idx_codigo (codigo),
    INDEX idx_usuario (id_usuario)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla: AsignacionEstudianteCita
CREATE TABLE IF NOT EXISTS asignacion_estudiante_cita (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_estudiante BIGINT NOT NULL,
    id_cita BIGINT NOT NULL,
    rol_en_cita ENUM('OBSERVADOR', 'ASISTENTE', 'RESPONSABLE') NOT NULL DEFAULT 'OBSERVADOR',
    fecha_asignacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    horas_reportadas DECIMAL(5,2) DEFAULT 0.00,
    observaciones VARCHAR(200),
    UNIQUE KEY uk_estudiante_cita (id_estudiante, id_cita),
    FOREIGN KEY (id_estudiante) REFERENCES estudiante_practica(id) ON DELETE CASCADE,
    FOREIGN KEY (id_cita) REFERENCES cita(id) ON DELETE CASCADE,
    INDEX idx_estudiante (id_estudiante),
    INDEX idx_cita (id_cita)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla: HistoriaClinica
CREATE TABLE IF NOT EXISTS historia_clinica (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_cita BIGINT NOT NULL UNIQUE,
    anamnesis TEXT,
    examen_fisico TEXT,
    signos_vitales JSON,
    diagnostico TEXT,
    plan TEXT,
    recomendaciones TEXT,
    fecha_cierre DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_cita) REFERENCES cita(id) ON DELETE RESTRICT,
    INDEX idx_cita (id_cita)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla: Medicamento
CREATE TABLE IF NOT EXISTS medicamento (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(120) NOT NULL,
    presentacion VARCHAR(120),
    concentracion VARCHAR(60),
    via VARCHAR(40),
    activo BOOLEAN DEFAULT TRUE,
    fecha_registro DATETIME DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_nombre (nombre)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla: Prescripcion
CREATE TABLE IF NOT EXISTS prescripcion (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_historia BIGINT NOT NULL,
    fecha_emision DATETIME DEFAULT CURRENT_TIMESTAMP,
    indicaciones_generales TEXT,
    FOREIGN KEY (id_historia) REFERENCES historia_clinica(id) ON DELETE CASCADE,
    INDEX idx_historia (id_historia)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla: PrescripcionDetalle
CREATE TABLE IF NOT EXISTS prescripcion_detalle (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_prescripcion BIGINT NOT NULL,
    id_medicamento BIGINT NOT NULL,
    dosis VARCHAR(80) NOT NULL,
    frecuencia VARCHAR(80) NOT NULL,
    duracion VARCHAR(80) NOT NULL,
    observaciones VARCHAR(200),
    FOREIGN KEY (id_prescripcion) REFERENCES prescripcion(id) ON DELETE CASCADE,
    FOREIGN KEY (id_medicamento) REFERENCES medicamento(id) ON DELETE RESTRICT,
    INDEX idx_prescripcion (id_prescripcion),
    INDEX idx_medicamento (id_medicamento)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla: BitacoraPractica
CREATE TABLE IF NOT EXISTS bitacora_practica (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_estudiante BIGINT NOT NULL,
    fecha DATE NOT NULL,
    actividad TEXT NOT NULL,
    horas DECIMAL(5,2) NOT NULL DEFAULT 0.00,
    evidencia_url VARCHAR(200),
    aprobado BOOLEAN DEFAULT FALSE,
    fecha_registro DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_estudiante) REFERENCES estudiante_practica(id) ON DELETE CASCADE,
    INDEX idx_estudiante (id_estudiante),
    INDEX idx_fecha (fecha)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla: EvaluacionEstudiante
CREATE TABLE IF NOT EXISTS evaluacion_estudiante (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_estudiante BIGINT NOT NULL,
    periodo VARCHAR(20) NOT NULL,
    criterio_tecnica TINYINT NOT NULL CHECK (criterio_tecnica BETWEEN 1 AND 5),
    criterio_etica TINYINT NOT NULL CHECK (criterio_etica BETWEEN 1 AND 5),
    criterio_comunicacion TINYINT NOT NULL CHECK (criterio_comunicacion BETWEEN 1 AND 5),
    criterio_responsabilidad TINYINT NOT NULL CHECK (criterio_responsabilidad BETWEEN 1 AND 5),
    comentarios TEXT,
    evaluador VARCHAR(120) NOT NULL,
    fecha DATE NOT NULL,
    fecha_registro DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_estudiante) REFERENCES estudiante_practica(id) ON DELETE CASCADE,
    INDEX idx_estudiante (id_estudiante),
    INDEX idx_periodo (periodo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla: TipoNotificacion
CREATE TABLE IF NOT EXISTS tipo_notificacion (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    codigo VARCHAR(20) NOT NULL UNIQUE,
    descripcion VARCHAR(200)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla: Notificacion
CREATE TABLE IF NOT EXISTS notificacion (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_usuario_destino BIGINT,
    id_propietario_destino BIGINT,
    id_tipo BIGINT NOT NULL,
    titulo VARCHAR(120) NOT NULL,
    mensaje TEXT NOT NULL,
    estado ENUM('PENDIENTE', 'ENVIADA', 'FALLIDA', 'LEIDA') DEFAULT 'PENDIENTE',
    canal_referencia VARCHAR(120),
    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    fecha_envio DATETIME,
    FOREIGN KEY (id_usuario_destino) REFERENCES usuario(id) ON DELETE CASCADE,
    FOREIGN KEY (id_propietario_destino) REFERENCES propietario(id) ON DELETE CASCADE,
    FOREIGN KEY (id_tipo) REFERENCES tipo_notificacion(id) ON DELETE RESTRICT,
    INDEX idx_usuario (id_usuario_destino),
    INDEX idx_propietario (id_propietario_destino),
    INDEX idx_estado (estado),
    INDEX idx_fecha_creacion (fecha_creacion)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla: Auditoria
CREATE TABLE IF NOT EXISTS auditoria (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    entidad VARCHAR(50) NOT NULL,
    identidad BIGINT,
    accion ENUM('CREATE', 'UPDATE', 'DELETE', 'LOGIN', 'READ') NOT NULL,
    usuario_email VARCHAR(120),
    ip VARCHAR(45),
    detalle JSON,
    fecha DATETIME DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_entidad (entidad, identidad),
    INDEX idx_usuario (usuario_email),
    INDEX idx_fecha (fecha)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

