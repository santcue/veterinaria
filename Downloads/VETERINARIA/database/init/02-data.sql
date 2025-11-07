-- Datos iniciales para el sistema

USE veterinaria_db;

-- Insertar Especies
INSERT INTO especie (nombre) VALUES
('Perro'),
('Gato'),
('Conejo'),
('Ave'),
('Reptil'),
('Roedor');

-- Insertar Razas (ejemplos para Perro y Gato)
INSERT INTO raza (id_especie, nombre) VALUES
-- Razas de Perro
(1, 'Labrador Retriever'),
(1, 'Golden Retriever'),
(1, 'Pastor Alemán'),
(1, 'Bulldog'),
(1, 'Beagle'),
(1, 'Poodle'),
(1, 'Rottweiler'),
(1, 'Yorkshire Terrier'),
(1, 'Chihuahua'),
(1, 'Mestizo'),
-- Razas de Gato
(2, 'Persa'),
(2, 'Siamés'),
(2, 'Maine Coon'),
(2, 'British Shorthair'),
(2, 'Bengalí'),
(2, 'Ragdoll'),
(2, 'Esfinge'),
(2, 'Mestizo');

-- Insertar Roles
INSERT INTO rol (nombre, descripcion) VALUES
('ADMIN', 'Administrador del sistema'),
('VETERINARIO', 'Veterinario clínico'),
('RECEPCIONISTA', 'Personal de recepción'),
('ESTUDIANTE', 'Estudiante en práctica'),
('PROPIETARIO', 'Propietario de mascota');

-- Insertar Estados de Cita
INSERT INTO estado_cita (codigo, descripcion) VALUES
('PENDIENTE', 'Cita pendiente de confirmación'),
('CONFIRMADA', 'Cita confirmada'),
('ATENDIDA', 'Cita atendida'),
('CANCELADA', 'Cita cancelada'),
('NO_SHOW', 'Paciente no se presentó');

-- Insertar Tipos de Notificación
INSERT INTO tipo_notificacion (codigo, descripcion) VALUES
('EMAIL', 'Notificación por correo electrónico'),
('SMS', 'Notificación por mensaje de texto'),
('INTERNA', 'Notificación interna del sistema'),
('WHATSAPP', 'Notificación por WhatsApp');

-- Insertar Servicios
INSERT INTO servicio (nombre, descripcion, precio_base, duracion_min) VALUES
('Consulta General', 'Consulta veterinaria general', 50000.00, 30),
('Consulta Urgencia', 'Consulta de urgencia', 80000.00, 45),
('Vacunación', 'Aplicación de vacunas', 60000.00, 15),
('Desparasitación', 'Tratamiento antiparasitario', 40000.00, 15),
('Cirugía Menor', 'Procedimientos quirúrgicos menores', 200000.00, 120),
('Cirugía Mayor', 'Procedimientos quirúrgicos mayores', 500000.00, 240),
('Limpieza Dental', 'Limpieza y profilaxis dental', 150000.00, 60),
('Radiografía', 'Estudio radiológico', 80000.00, 30),
('Análisis de Sangre', 'Hemograma completo', 120000.00, 15),
('Ecografía', 'Estudio ecográfico', 150000.00, 45);

-- Insertar Medicamentos (ejemplos)
INSERT INTO medicamento (nombre, presentacion, concentracion, via) VALUES
('Amoxicilina', 'Tabletas', '500mg', 'oral'),
('Cefalexina', 'Cápsulas', '500mg', 'oral'),
('Metronidazol', 'Tabletas', '250mg', 'oral'),
('Doxiciclina', 'Cápsulas', '100mg', 'oral'),
('Ivermectina', 'Inyectable', '1%', 'SC'),
('Fenobarbital', 'Tabletas', '100mg', 'oral'),
('Prednisolona', 'Tabletas', '5mg', 'oral'),
('Carprofeno', 'Tabletas', '25mg', 'oral'),
('Enrofloxacina', 'Inyectable', '5%', 'IM'),
('Dexametasona', 'Inyectable', '2mg/ml', 'IM');

-- Usuario administrador por defecto (password: admin123)
-- Hash generado con BCrypt para 'admin123'
INSERT INTO usuario (nombre, email, telefono, hash_password, activo) VALUES
('Administrador', 'admin@veterinaria.edu.co', '3001234567', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', TRUE);

-- Asignar rol de administrador
INSERT INTO usuario_rol (id_usuario, id_rol) VALUES
(1, 1);

