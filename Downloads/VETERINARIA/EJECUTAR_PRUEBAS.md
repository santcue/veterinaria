# 🧪 Cómo Ejecutar las Pruebas del Backend

## ✅ Pruebas Unitarias Implementadas

Se han creado **11 archivos de prueba** para todos los servicios del backend:

1. ✅ `MascotaServiceTest.java`
2. ✅ `AuthServiceTest.java`
3. ✅ `PropietarioServiceTest.java`
4. ✅ `CitaServiceTest.java`
5. ✅ `PrescripcionBuilderTest.java`
6. ✅ `InventarioServiceTest.java`
7. ✅ `FacturacionServiceTest.java`
8. ✅ `DisponibleStateTest.java`
9. ✅ `NotificacionServiceTest.java`
10. ✅ `HistoriaClinicaCompositeTest.java`
11. ✅ `ConsultaFactoryTest.java`

---

## 🚀 Ejecutar Pruebas

### **Opción 1: Ejecutar pruebas de un servicio específico**

```bash
# Ir al servicio
cd backend/mascotas-service

# Ejecutar todas las pruebas
./mvnw test

# O en Windows
mvnw.cmd test
```

### **Opción 2: Ejecutar todas las pruebas del proyecto**

```bash
# Desde la raíz del proyecto
cd backend

# Ejecutar pruebas de todos los servicios
for dir in */; do
  echo "Ejecutando pruebas en $dir"
  cd "$dir"
  ./mvnw test
  cd ..
done
```

**En Windows (PowerShell):**
```powershell
Get-ChildItem -Directory | ForEach-Object {
    Write-Host "Ejecutando pruebas en $($_.Name)"
    Set-Location $_.FullName
    .\mvnw.cmd test
    Set-Location ..
}
```

### **Opción 3: Ejecutar con cobertura**

```bash
cd backend/mascotas-service
./mvnw test jacoco:report
```

---

## 📊 Ver Resultados

Después de ejecutar las pruebas, verás algo como:

```
[INFO] Tests run: 3, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
```

---

## 🔍 Verificar que las Pruebas Existen

```bash
# Buscar todos los archivos de prueba
find backend -name "*Test.java" -type f

# O en Windows
dir /s /b backend\*Test.java
```

Deberías ver **11 archivos** de prueba.

---

## ✅ Dependencias de Testing

Todos los servicios ahora tienen:
- ✅ `spring-boot-starter-test` - Framework de pruebas
- ✅ `reactor-test` - Testing de código reactivo (Mono/Flux)

Estas dependencias están configuradas en todos los `pom.xml`.

---

## 🎯 Ejemplo de Ejecución

```bash
# 1. Ir a un servicio
cd backend/auth-service

# 2. Ejecutar pruebas
./mvnw test

# 3. Ver resultado
# Deberías ver:
# Tests run: 3, Failures: 0, Errors: 0, Skipped: 0
```

---

## 📝 Notas

- Las pruebas usan **JUnit 5** y **Mockito**
- Se usa **Reactor Test** para probar código reactivo
- Todas las pruebas están en la carpeta `src/test/java`
- Los archivos de prueba siguen el patrón `*Test.java`

---

**¡Las pruebas están listas para ejecutarse!** 🎉

