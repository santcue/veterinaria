package co.edu.veterinaria.estudiantes.domain.state.impl;

import co.edu.veterinaria.estudiantes.domain.model.EstudiantePractica;
import co.edu.veterinaria.estudiantes.domain.state.EstadoEstudiante;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * State: Estudiante ocupado
 */
@Slf4j
@Component
public class OcupadoState implements EstadoEstudiante {
    
    @Override
    public String getNombre() {
        return "OCUPADO";
    }
    
    @Override
    public Mono<Boolean> puedeSerAsignado(EstudiantePractica estudiante) {
        log.debug("Estudiante {} está ocupado, no puede ser asignado", estudiante.getCodigo());
        return Mono.just(false);
    }
    
    @Override
    public Mono<EstudiantePractica> cambiarEstado(EstudiantePractica estudiante, String nuevoEstado) {
        if ("DISPONIBLE".equals(nuevoEstado) || "EN_PRACTICA".equals(nuevoEstado)) {
            return Mono.just(estudiante);
        }
        return Mono.error(new IllegalStateException("Transición no permitida desde OCUPADO"));
    }
    
    @Override
    public String getDescripcion() {
        return "Estudiante ocupado en otra actividad";
    }
}

