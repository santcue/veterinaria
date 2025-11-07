package co.edu.veterinaria.estudiantes.domain.state.impl;

import co.edu.veterinaria.estudiantes.domain.model.EstudiantePractica;
import co.edu.veterinaria.estudiantes.domain.state.EstadoEstudiante;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * State: Estudiante disponible para asignación
 */
@Slf4j
@Component
public class DisponibleState implements EstadoEstudiante {
    
    @Override
    public String getNombre() {
        return "DISPONIBLE";
    }
    
    @Override
    public Mono<Boolean> puedeSerAsignado(EstudiantePractica estudiante) {
        log.debug("Estudiante {} está disponible para asignación", estudiante.getCodigo());
        return Mono.just(true);
    }
    
    @Override
    public Mono<EstudiantePractica> cambiarEstado(EstudiantePractica estudiante, String nuevoEstado) {
        log.debug("Cambiando estado de {} a {}", estudiante.getCodigo(), nuevoEstado);
        // Validar transiciones permitidas
        if ("OCUPADO".equals(nuevoEstado) || "EN_PRACTICA".equals(nuevoEstado)) {
            return Mono.just(estudiante);
        }
        return Mono.error(new IllegalStateException("Transición no permitida desde DISPONIBLE a " + nuevoEstado));
    }
    
    @Override
    public String getDescripcion() {
        return "Estudiante disponible para ser asignado a citas o prácticas";
    }
}

