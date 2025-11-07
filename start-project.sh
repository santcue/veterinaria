#!/bin/bash

# Script de inicio del Proyecto Nuclear
# Sistema de Gestión Clínica Veterinaria

set -e

echo "=========================================="
echo "  Proyecto Nuclear - Sistema Veterinaria"
echo "=========================================="
echo ""

# Colores para output
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
NC='\033[0m' # No Color

# Función para verificar si un comando existe
command_exists() {
    command -v "$1" >/dev/null 2>&1
}

# Verificar prerrequisitos
echo "Verificando prerrequisitos..."
echo ""

if ! command_exists docker; then
    echo -e "${RED}❌ Docker no está instalado${NC}"
    exit 1
fi

if ! command_exists docker-compose; then
    echo -e "${RED}❌ Docker Compose no está instalado${NC}"
    exit 1
fi

if ! command_exists java; then
    echo -e "${RED}❌ Java no está instalado${NC}"
    exit 1
fi

JAVA_VERSION=$(java -version 2>&1 | head -n 1 | cut -d'"' -f2 | sed '/^1\./s///' | cut -d'.' -f1)
if [ "$JAVA_VERSION" -lt 21 ]; then
    echo -e "${RED}❌ Se requiere Java 21 o superior. Versión actual: $JAVA_VERSION${NC}"
    exit 1
fi

if ! command_exists node; then
    echo -e "${RED}❌ Node.js no está instalado${NC}"
    exit 1
fi

echo -e "${GREEN}✅ Todos los prerrequisitos están instalados${NC}"
echo ""

# Iniciar servicios de infraestructura
echo "Iniciando servicios de infraestructura (MySQL, RabbitMQ, Redis)..."
docker-compose up -d

echo ""
echo "Esperando a que los servicios estén listos..."
sleep 10

# Verificar que los servicios estén corriendo
if ! docker-compose ps | grep -q "Up"; then
    echo -e "${RED}❌ Error al iniciar servicios Docker${NC}"
    exit 1
fi

echo -e "${GREEN}✅ Servicios de infraestructura iniciados${NC}"
echo ""

# Compilar microservicios
echo "Compilando microservicios..."
echo ""

SERVICES=("api-gateway" "auth-service" "mascotas-service" "propietarios-service" "citas-service" "notificaciones-service" "estudiantes-service" "historia-service" "prescripciones-service" "inventario-service" "pagos-service" "servicios-service")

for service in "${SERVICES[@]}"; do
    echo "Compilando $service..."
    cd "backend/$service"
    if [ -f "./mvnw" ]; then
        chmod +x ./mvnw
        ./mvnw clean install -DskipTests > /dev/null 2>&1
    else
        mvn clean install -DskipTests > /dev/null 2>&1
    fi
    cd ../..
done

echo -e "${GREEN}✅ Microservicios compilados${NC}"
echo ""

# Instalar dependencias del frontend
echo "Instalando dependencias del frontend..."
cd frontend
npm install
cd ..

echo -e "${GREEN}✅ Dependencias del frontend instaladas${NC}"
echo ""

echo "=========================================="
echo -e "${GREEN}✅ Proyecto listo para iniciar${NC}"
echo "=========================================="
echo ""
echo "Para iniciar los servicios, ejecuta en terminales separadas:"
echo ""
echo -e "${YELLOW}Terminal 1 - API Gateway (8080):${NC}"
echo "  cd backend/api-gateway && ./mvnw spring-boot:run"
echo ""
echo -e "${YELLOW}Terminal 2 - Auth Service (8091):${NC}"
echo "  cd backend/auth-service && ./mvnw spring-boot:run"
echo ""
echo -e "${YELLOW}Terminal 3 - Mascotas Service (8081):${NC}"
echo "  cd backend/mascotas-service && ./mvnw spring-boot:run"
echo ""
echo -e "${YELLOW}Terminal 4 - Propietarios Service (8082):${NC}"
echo "  cd backend/propietarios-service && ./mvnw spring-boot:run"
echo ""
echo -e "${YELLOW}Terminal 5 - Citas Service (8083):${NC}"
echo "  cd backend/citas-service && ./mvnw spring-boot:run"
echo ""
echo -e "${YELLOW}Terminal 6 - Notificaciones Service (8084):${NC}"
echo "  cd backend/notificaciones-service && ./mvnw spring-boot:run"
echo ""
echo -e "${YELLOW}Terminal 7 - Estudiantes Service (8085):${NC}"
echo "  cd backend/estudiantes-service && ./mvnw spring-boot:run"
echo ""
echo -e "${YELLOW}Terminal 8 - Historia Service (8086):${NC}"
echo "  cd backend/historia-service && ./mvnw spring-boot:run"
echo ""
echo -e "${YELLOW}Terminal 9 - Prescripciones Service (8087):${NC}"
echo "  cd backend/prescripciones-service && ./mvnw spring-boot:run"
echo ""
echo -e "${YELLOW}Terminal 10 - Inventario Service (8088):${NC}"
echo "  cd backend/inventario-service && ./mvnw spring-boot:run"
echo ""
echo -e "${YELLOW}Terminal 11 - Pagos Service (8089):${NC}"
echo "  cd backend/pagos-service && ./mvnw spring-boot:run"
echo ""
echo -e "${YELLOW}Terminal 12 - Servicios Service (8090):${NC}"
echo "  cd backend/servicios-service && ./mvnw spring-boot:run"
echo ""
echo -e "${YELLOW}Terminal 13 - Frontend (3000):${NC}"
echo "  cd frontend && npm run dev"
echo ""
echo "URLs de los servicios:"
echo "  - API Gateway: http://localhost:8080"
echo "  - Auth Service: http://localhost:8091"
echo "  - Mascotas Service: http://localhost:8081"
echo "  - Propietarios Service: http://localhost:8082"
echo "  - Citas Service: http://localhost:8083"
echo "  - Notificaciones Service: http://localhost:8084"
echo "  - Estudiantes Service: http://localhost:8085"
echo "  - Historia Service: http://localhost:8086"
echo "  - Prescripciones Service: http://localhost:8087"
echo "  - Inventario Service: http://localhost:8088"
echo "  - Pagos Service: http://localhost:8089"
echo "  - Servicios Service: http://localhost:8090"
echo "  - Frontend: http://localhost:3000"
echo "  - MySQL: localhost:3306"
echo "  - RabbitMQ Management: http://localhost:15672 (admin/admin123)"
echo ""

