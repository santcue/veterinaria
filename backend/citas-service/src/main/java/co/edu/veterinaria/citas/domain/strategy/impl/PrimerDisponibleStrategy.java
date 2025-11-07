package co.edu.veterinaria.citas.domain.strategy.impl;

import co.edu.veterinaria.citas.domain.model.Cita;
import co.edu.veterinaria.citas.domain.strategy.AsignacionHorarioStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Strategy: Asigna el primer horario disponible
 */
@Slf4j
@Component
public class PrimerDisponibleStrategy implements AsignacionHorarioStrategy {
    
    @Override
    public Mono<LocalDateTime> asignarHorario(Cita cita) {
        log.debug("Asignando primer horario disponible");
        
        // Lógica simplificada: asigna el próximo horario laboral disponible
        LocalDateTime ahora = LocalDateTime.now();
        LocalDateTime horarioAsignado = ahora.plusDays(1)
            .withHour(9)
            .withMinute(0)
            .withSecond(0);
        
        return Mono.just(horarioAsignado);
    }
    
    @Override
    public String getNombre() {
        return "PRIMER_DISPONIBLE";
    }
}

