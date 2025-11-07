@echo off
REM Script de inicio del Proyecto Nuclear para Windows
REM Sistema de Gestión Clínica Veterinaria

echo ==========================================
echo   Proyecto Nuclear - Sistema Veterinaria
echo ==========================================
echo.

REM Verificar Docker
docker --version >nul 2>&1
if errorlevel 1 (
    echo [ERROR] Docker no esta instalado
    exit /b 1
)

REM Verificar Java
java -version >nul 2>&1
if errorlevel 1 (
    echo [ERROR] Java no esta instalado
    exit /b 1
)

REM Verificar Node
node --version >nul 2>&1
if errorlevel 1 (
    echo [ERROR] Node.js no esta instalado
    exit /b 1
)

echo [OK] Prerrequisitos verificados
echo.

REM Iniciar servicios Docker
echo Iniciando servicios de infraestructura...
docker-compose up -d

timeout /t 10 /nobreak >nul

echo [OK] Servicios Docker iniciados
echo.

REM Compilar microservicios
echo Compilando microservicios...
echo.

for %%s in (api-gateway auth-service mascotas-service propietarios-service citas-service notificaciones-service estudiantes-service historia-service prescripciones-service inventario-service pagos-service servicios-service) do (
    echo Compilando %%s...
    cd backend\%%s
    if exist mvnw.cmd (
        call mvnw.cmd clean install -DskipTests >nul 2>&1
    ) else (
        call mvn clean install -DskipTests >nul 2>&1
    )
    cd ..\..
)

echo [OK] Microservicios compilados
echo.

REM Instalar dependencias frontend
echo Instalando dependencias del frontend...
cd frontend
call npm install
cd ..

echo [OK] Dependencias instaladas
echo.

echo ==========================================
echo [OK] Proyecto listo para iniciar
echo ==========================================
echo.
echo Para iniciar los servicios, abre terminales separadas:
echo.
echo Terminal 1 - API Gateway (8080):
echo   cd backend\api-gateway ^&^& mvnw.cmd spring-boot:run
echo.
echo Terminal 2 - Auth Service (8091):
echo   cd backend\auth-service ^&^& mvnw.cmd spring-boot:run
echo.
echo Terminal 3 - Mascotas Service (8081):
echo   cd backend\mascotas-service ^&^& mvnw.cmd spring-boot:run
echo.
echo Terminal 4 - Propietarios Service (8082):
echo   cd backend\propietarios-service ^&^& mvnw.cmd spring-boot:run
echo.
echo Terminal 5 - Citas Service (8083):
echo   cd backend\citas-service ^&^& mvnw.cmd spring-boot:run
echo.
echo Terminal 6 - Notificaciones Service (8084):
echo   cd backend\notificaciones-service ^&^& mvnw.cmd spring-boot:run
echo.
echo Terminal 7 - Estudiantes Service (8085):
echo   cd backend\estudiantes-service ^&^& mvnw.cmd spring-boot:run
echo.
echo Terminal 8 - Historia Service (8086):
echo   cd backend\historia-service ^&^& mvnw.cmd spring-boot:run
echo.
echo Terminal 9 - Prescripciones Service (8087):
echo   cd backend\prescripciones-service ^&^& mvnw.cmd spring-boot:run
echo.
echo Terminal 10 - Inventario Service (8088):
echo   cd backend\inventario-service ^&^& mvnw.cmd spring-boot:run
echo.
echo Terminal 11 - Pagos Service (8089):
echo   cd backend\pagos-service ^&^& mvnw.cmd spring-boot:run
echo.
echo Terminal 12 - Servicios Service (8090):
echo   cd backend\servicios-service ^&^& mvnw.cmd spring-boot:run
echo.
echo Terminal 13 - Frontend (3000):
echo   cd frontend ^&^& npm run dev
echo.
echo URLs de los servicios:
echo   - API Gateway: http://localhost:8080
echo   - Auth: http://localhost:8091
echo   - Mascotas: http://localhost:8081
echo   - Propietarios: http://localhost:8082
echo   - Citas: http://localhost:8083
echo   - Notificaciones: http://localhost:8084
echo   - Estudiantes: http://localhost:8085
echo   - Historia: http://localhost:8086
echo   - Prescripciones: http://localhost:8087
echo   - Inventario: http://localhost:8088
echo   - Pagos: http://localhost:8089
echo   - Servicios: http://localhost:8090
echo   - Frontend: http://localhost:3000
echo   - MySQL: localhost:3306
echo   - RabbitMQ: http://localhost:15672
echo.

pause

