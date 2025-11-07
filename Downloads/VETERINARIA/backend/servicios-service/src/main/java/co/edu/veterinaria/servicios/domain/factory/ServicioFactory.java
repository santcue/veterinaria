package co.edu.veterinaria.servicios.domain.factory;

import co.edu.veterinaria.servicios.domain.model.Servicio;
import reactor.core.publisher.Mono;

/**
 * Factory Method Pattern: Crea diferentes tipos de servicios
 */
public interface ServicioFactory {
    
    /**
     * Crea un servicio del tipo específico
     */
    Mono<Servicio> crearServicio(Servicio servicio);
    
    /**
     * Obtiene el tipo de servicio que maneja esta factory
     */
    String getTipoServicio();
}

