package co.edu.veterinaria.citas.domain.strategy.impl;

import co.edu.veterinaria.citas.domain.model.Cita;
import co.edu.veterinaria.citas.domain.strategy.AsignacionHorarioStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

/**
 * Strategy: Asigna horario con el mismo veterinario si está disponible
 */
@Slf4j
@Component
public class MismoVeterinarioStrategy implements AsignacionHorarioStrategy {
    
    @Override
    public Mono<LocalDateTime> asignarHorario(Cita cita) {
        log.debug("Asignando horario con mismo veterinario: {}", cita.getIdVeterinario());
        
        // Lógica: buscar próximo horario disponible del mismo veterinario
        LocalDateTime ahora = LocalDateTime.now();
        LocalDateTime horarioAsignado = ahora.plusDays(2)
            .withHour(10)
            .withMinute(0)
            .withSecond(0);
        
        return Mono.just(horarioAsignado);
    }
    
    @Override
    public String getNombre() {
        return "MISMO_VETERINARIO";
    }
}

