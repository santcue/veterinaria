package co.edu.veterinaria.servicios.domain.factory.impl;

import co.edu.veterinaria.servicios.domain.factory.ServicioFactory;
import co.edu.veterinaria.servicios.domain.model.Servicio;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

/**
 * Factory Method: Crea servicios de consulta
 */
@Slf4j
@Component
public class ConsultaFactory implements ServicioFactory {
    
    @Override
    public Mono<Servicio> crearServicio(Servicio servicio) {
        log.debug("Creando servicio de consulta: {}", servicio.getNombre());
        
        // Validaciones y configuraciones específicas para consulta
        if (servicio.getPrecioBase() == null || servicio.getPrecioBase().compareTo(BigDecimal.ZERO) <= 0) {
            return Mono.error(new IllegalArgumentException("El precio de consulta debe ser mayor a cero"));
        }
        
        if (servicio.getDuracionMin() == null || servicio.getDuracionMin() < 15) {
            servicio.setDuracionMin(30); // Duración mínima por defecto
        }
        
        return Mono.just(servicio);
    }
    
    @Override
    public String getTipoServicio() {
        return "CONSULTA";
    }
}

