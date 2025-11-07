package co.edu.veterinaria.propietarios.domain.service;

import co.edu.veterinaria.propietarios.domain.factory.PropietarioFactory;
import co.edu.veterinaria.propietarios.domain.model.Propietario;
import co.edu.veterinaria.propietarios.domain.observer.PropietarioObserver;
import co.edu.veterinaria.propietarios.domain.repository.PropietarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * Service Pattern: Lógica de negocio para Propietarios
 * Factory Method: Usa PropietarioFactory para crear desde distintos orígenes
 * Observer Pattern: Notifica a observadores cuando se registra un propietario
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PropietarioService {
    
    private final PropietarioRepository propietarioRepository;
    private final List<PropietarioFactory> factories;
    private final List<PropietarioObserver> observers;
    
    public Flux<Propietario> obtenerTodos() {
        return propietarioRepository.findByActivoTrue();
    }
    
    public Mono<Propietario> obtenerPorId(Long id) {
        return propietarioRepository.findByIdAndActivoTrue(id)
            .switchIfEmpty(Mono.error(new RuntimeException("Propietario no encontrado")));
    }
    
    public Mono<Propietario> obtenerPorDocumento(String documento) {
        return propietarioRepository.findByDocumentoAndActivoTrue(documento)
            .switchIfEmpty(Mono.error(new RuntimeException("Propietario no encontrado")));
    }
    
    /**
     * Crea un propietario usando Factory Method según el origen
     */
    public Mono<Propietario> crear(String tipoOrigen, Propietario propietario) {
        log.debug("Creando propietario desde origen: {}", tipoOrigen);
        
        // Buscar factory apropiada
        PropietarioFactory factory = factories.stream()
            .filter(f -> f.getTipoOrigen().equalsIgnoreCase(tipoOrigen))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Tipo de origen no válido: " + tipoOrigen));
        
        // Crear propietario usando factory
        return factory.crearPropietario(propietario)
            .flatMap(p -> {
                // Verificar si ya existe
                return propietarioRepository.findByDocumentoAndActivoTrue(p.getDocumento())
                    .flatMap(existente -> Mono.<Propietario>error(new RuntimeException("Propietario ya existe")))
                    .switchIfEmpty(
                        propietarioRepository.save(p)
                            .flatMap(guardado -> {
                                // Notificar a todos los observadores (Observer Pattern)
                                return notificarObservadores(guardado)
                                    .thenReturn(guardado);
                            })
                    );
            })
            .doOnSuccess(p -> log.info("Propietario creado exitosamente: {}", p.getId()))
            .doOnError(error -> log.error("Error al crear propietario: {}", error.getMessage()));
    }
    
    public Mono<Propietario> actualizar(Long id, Propietario propietario) {
        return propietarioRepository.findByIdAndActivoTrue(id)
            .switchIfEmpty(Mono.error(new RuntimeException("Propietario no encontrado")))
            .flatMap(existente -> {
                existente.setNombres(propietario.getNombres());
                existente.setApellidos(propietario.getApellidos());
                existente.setEmail(propietario.getEmail());
                existente.setTelefono(propietario.getTelefono());
                existente.setDireccion(propietario.getDireccion());
                return propietarioRepository.save(existente);
            });
    }
    
    public Mono<Void> eliminar(Long id) {
        return propietarioRepository.findByIdAndActivoTrue(id)
            .switchIfEmpty(Mono.error(new RuntimeException("Propietario no encontrado")))
            .flatMap(propietario -> {
                propietario.setActivo(false);
                return propietarioRepository.save(propietario).then();
            });
    }
    
    /**
     * Observer Pattern: Notifica a todos los observadores
     */
    private Mono<Void> notificarObservadores(Propietario propietario) {
        log.debug("Notificando a {} observadores sobre nuevo propietario", observers.size());
        
        return Flux.fromIterable(observers)
            .flatMap(observer -> {
                log.debug("Notificando a: {}", observer.getNombre());
                return observer.onPropietarioRegistrado(propietario)
                    .onErrorResume(error -> {
                        log.error("Error al notificar a {}: {}", observer.getNombre(), error.getMessage());
                        return Mono.empty();
                    });
            })
            .then();
    }
}

