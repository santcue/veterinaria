package co.edu.veterinaria.servicios.domain.factory.impl;

import co.edu.veterinaria.servicios.domain.factory.ServicioFactory;
import co.edu.veterinaria.servicios.domain.model.Servicio;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

/**
 * Factory Method: Crea servicios de cirugía
 */
@Slf4j
@Component
public class CirugiaFactory implements ServicioFactory {
    
    @Override
    public Mono<Servicio> crearServicio(Servicio servicio) {
        log.debug("Creando servicio de cirugía: {}", servicio.getNombre());
        
        // Validaciones específicas para cirugía
        if (servicio.getPrecioBase() == null || servicio.getPrecioBase().compareTo(new BigDecimal("100000")) < 0) {
            return Mono.error(new IllegalArgumentException("El precio de cirugía debe ser mayor a $100,000"));
        }
        
        if (servicio.getDuracionMin() == null || servicio.getDuracionMin() < 60) {
            servicio.setDuracionMin(120); // Duración mínima para cirugía
        }
        
        return Mono.just(servicio);
    }
    
    @Override
    public String getTipoServicio() {
        return "CIRUGIA";
    }
}

