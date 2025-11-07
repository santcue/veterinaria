# Guía de Contribución

## Estructura del Código

### Backend
- **Domain**: Modelos de dominio y lógica de negocio
- **Infrastructure**: Implementaciones técnicas (web, repositorios)
- **Config**: Configuraciones de Spring

### Frontend
- **src/patterns**: Implementaciones de patrones de diseño
- **src/pages**: Páginas de la aplicación
- **src/components**: Componentes reutilizables
- **src/services**: Servicios de API

## Patrones de Diseño

Al agregar nuevas funcionalidades, asegúrate de aplicar los patrones de diseño apropiados:

- **Repository**: Para acceso a datos
- **Service**: Para lógica de negocio
- **Strategy**: Para algoritmos intercambiables
- **Factory**: Para creación de objetos
- **Observer**: Para notificaciones y eventos
- **Builder**: Para construcción compleja
- **Composite**: Para estructuras jerárquicas

## Estándares de Código

1. **Nombres**: Usar español para nombres de dominio, inglés para código técnico
2. **Documentación**: Comentar clases y métodos públicos
3. **Pruebas**: Escribir pruebas unitarias para nueva funcionalidad
4. **Commits**: Mensajes descriptivos en español

## Proceso de Desarrollo

1. Crear rama desde `main`
2. Desarrollar funcionalidad
3. Escribir pruebas
4. Actualizar documentación
5. Crear Pull Request

## Pruebas

```bash
# Backend
./mvnw test

# Frontend
npm test
```

