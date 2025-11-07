package co.edu.veterinaria.estudiantes.domain.service;

import co.edu.veterinaria.estudiantes.domain.model.EstudiantePractica;
import co.edu.veterinaria.estudiantes.domain.observer.CitaObserver;
import co.edu.veterinaria.estudiantes.domain.repository.EstudiantePracticaRepository;
import co.edu.veterinaria.estudiantes.domain.state.EstadoEstudiante;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Service Pattern: Lógica de negocio para Estudiantes
 * State Pattern: Maneja estados de disponibilidad
 * Observer Pattern: Notifica cambios a observadores
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EstudianteService {
    
    private final EstudiantePracticaRepository repository;
    private final List<EstadoEstudiante> estados;
    private final List<CitaObserver> observadores;
    
    private Map<String, EstadoEstudiante> estadosMap;
    
    @PostConstruct
    public void init() {
        estadosMap = estados.stream()
            .collect(Collectors.toMap(EstadoEstudiante::getNombre, Function.identity()));
    }
    
    public Flux<EstudiantePractica> obtenerTodos() {
        return repository.findByActivoTrue();
    }
    
    public Mono<EstudiantePractica> obtenerPorId(Long id) {
        return repository.findByIdAndActivoTrue(id)
            .switchIfEmpty(Mono.error(new RuntimeException("Estudiante no encontrado")));
    }
    
    public Mono<Boolean> asignarACita(Long idEstudiante, Long idCita) {
        return repository.findByIdAndActivoTrue(idEstudiante)
            .switchIfEmpty(Mono.error(new RuntimeException("Estudiante no encontrado")))
            .flatMap(estudiante -> {
                // Verificar que el estudiante pueda ser asignado
                EstadoEstudiante estado = estadosMap.get(estudiante.getEstado());
                if (estado == null) {
                    estado = estadosMap.get("DISPONIBLE");
                }
                
                return estado.puedeSerAsignado(estudiante)
                    .flatMap(puede -> {
                        if (!puede) {
                            return Mono.error(new RuntimeException("Estudiante no disponible para asignación"));
                        }
                        return notificarObservadoresAsignacion(estudiante, idCita)
                            .thenReturn(true);
                    });
            });
    }
    
    public Mono<EstudiantePractica> cambiarEstado(EstudiantePractica estudiante, String nuevoEstado) {
        EstadoEstudiante estadoActual = estadosMap.get(estudiante.getEstado());
        if (estadoActual == null) {
            estadoActual = estadosMap.get("DISPONIBLE");
        }
        
        return estadoActual.cambiarEstado(estudiante, nuevoEstado)
            .flatMap(e -> {
                e.setEstado(nuevoEstado);
                return repository.save(e);
            });
    }
    
    private Mono<Void> notificarObservadoresAsignacion(EstudiantePractica estudiante, Long idCita) {
        return Flux.fromIterable(observadores)
            .flatMap(observer -> observer.onEstudianteAsignado(estudiante, idCita)
                .onErrorResume(error -> {
                    log.error("Error notificando a {}: {}", observer.getNombre(), error.getMessage());
                    return Mono.empty();
                }))
            .then();
    }
}

