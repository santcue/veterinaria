package co.edu.veterinaria.citas.domain.strategy;

import co.edu.veterinaria.citas.domain.model.Cita;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

/**
 * Strategy Pattern: Diferentes estrategias para asignar horarios de citas
 */
public interface AsignacionHorarioStrategy {
    
    /**
     * Asigna un horario a la cita según la estrategia
     */
    Mono<LocalDateTime> asignarHorario(Cita cita);
    
    /**
     * Obtiene el nombre de la estrategia
     */
    String getNombre();
}

