package co.edu.veterinaria.servicios.domain.factory.impl;

import co.edu.veterinaria.servicios.domain.factory.ServicioFactory;
import co.edu.veterinaria.servicios.domain.model.Servicio;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

/**
 * Factory Method: Crea servicios de vacunación
 */
@Slf4j
@Component
public class VacunacionFactory implements ServicioFactory {
    
    @Override
    public Mono<Servicio> crearServicio(Servicio servicio) {
        log.debug("Creando servicio de vacunación: {}", servicio.getNombre());
        
        // Validaciones específicas para vacunación
        if (servicio.getDuracionMin() == null) {
            servicio.setDuracionMin(15); // Vacunación es rápida
        }
        
        return Mono.just(servicio);
    }
    
    @Override
    public String getTipoServicio() {
        return "VACUNACION";
    }
}

