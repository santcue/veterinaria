# 🚀 Guía de Inicio Rápido - Proyecto Nuclear

## Inicio Automático (Recomendado)

### Windows
```bash
start-project.bat
```

### Linux/Mac
```bash
chmod +x start-project.sh
./start-project.sh
```

## Inicio Manual Paso a Paso

### 1️⃣ Verificar Prerrequisitos

```bash
# Verificar versiones
java -version    # Debe ser JDK 21+
node -v          # Debe ser 18+
docker --version # Docker instalado
```

### 2️⃣ Iniciar Servicios de Infraestructura

```bash
# Iniciar MySQL, RabbitMQ y Redis
docker-compose up -d

# Verificar que estén corriendo
docker-compose ps
```

**Espera 10-15 segundos** para que MySQL inicialice completamente.

### 3️⃣ Iniciar Microservicios Backend

Abre **3 terminales separadas** y ejecuta:

#### Terminal 1 - Mascotas Service
```bash
cd backend/mascotas-service
./mvnw spring-boot:run
# O en Windows: mvnw.cmd spring-boot:run
```
✅ Servicio disponible en: `http://localhost:8081`

#### Terminal 2 - Propietarios Service
```bash
cd backend/propietarios-service
./mvnw spring-boot:run
```
✅ Servicio disponible en: `http://localhost:8082`

#### Terminal 3 - Citas Service
```bash
cd backend/citas-service
./mvnw spring-boot:run
```
✅ Servicio disponible en: `http://localhost:8083`

### 4️⃣ Iniciar Frontend

Abre una **4ta terminal**:

```bash
cd frontend
npm install  # Solo la primera vez
npm run dev
```

✅ Frontend disponible en: `http://localhost:3000`

## 🧪 Verificar que Todo Funciona

### Probar Backend

```bash
# Probar Mascotas Service
curl http://localhost:8081/api/mascotas

# Probar Propietarios Service
curl http://localhost:8082/api/propietarios

# Probar Citas Service
curl http://localhost:8083/api/citas
```

### Probar Frontend

1. Abre el navegador en `http://localhost:3000`
2. Deberías ver la interfaz del sistema
3. Navega entre las secciones (Mascotas, Propietarios, Citas)

## 📊 URLs de Servicios

| Servicio | URL | Credenciales |
|----------|-----|-------------|
| **Mascotas API** | http://localhost:8081/api/mascotas | - |
| **Propietarios API** | http://localhost:8082/api/propietarios | - |
| **Citas API** | http://localhost:8083/api/citas | - |
| **Frontend** | http://localhost:3000 | - |
| **MySQL** | localhost:3306 | vet_user / vet_password |
| **RabbitMQ Management** | http://localhost:15672 | admin / admin123 |

## 🔧 Solución de Problemas

### Error: Puerto ya en uso

```bash
# Windows - Encontrar proceso
netstat -ano | findstr :8081

# Linux/Mac - Encontrar proceso
lsof -i :8081

# Detener proceso o cambiar puerto en application.yml
```

### Error: No se puede conectar a MySQL

```bash
# Ver logs de MySQL
docker-compose logs mysql

# Reiniciar MySQL
docker-compose restart mysql

# Verificar que esté corriendo
docker-compose ps
```

### Error: Maven no encuentra dependencias

```bash
# Limpiar y reinstalar
./mvnw clean install -U
```

### Error: Frontend no compila

```bash
cd frontend
rm -rf node_modules package-lock.json
npm install
npm run dev
```

## 📝 Próximos Pasos

1. ✅ Verificar que todos los servicios estén corriendo
2. 📖 Revisar la documentación en `PROYECTO.md`
3. 🧪 Ejecutar pruebas: `./mvnw test`
4. 🔍 Explorar los endpoints con Postman o similar
5. 📚 Revisar el código para entender la arquitectura

## 🎯 Comandos Útiles

```bash
# Detener todos los servicios Docker
docker-compose down

# Ver logs de un servicio
docker-compose logs -f mysql

# Reiniciar un servicio
docker-compose restart mysql

# Compilar sin ejecutar pruebas
./mvnw clean install -DskipTests

# Ejecutar pruebas
./mvnw test

# Limpiar todo y empezar de nuevo
docker-compose down -v
docker-compose up -d
```

## ✅ Checklist de Inicio

- [ ] Docker y Docker Compose instalados
- [ ] JDK 21 instalado
- [ ] Node.js 18+ instalado
- [ ] Servicios Docker corriendo (MySQL, RabbitMQ, Redis)
- [ ] Mascotas Service corriendo en puerto 8081
- [ ] Propietarios Service corriendo en puerto 8082
- [ ] Citas Service corriendo en puerto 8083
- [ ] Frontend corriendo en puerto 3000
- [ ] Puedo acceder a http://localhost:3000

¡Listo! 🎉 El proyecto está funcionando.

