package co.edu.veterinaria.propietarios.domain.factory;

import co.edu.veterinaria.propietarios.domain.model.Propietario;
import reactor.core.publisher.Mono;

/**
 * Factory Method Pattern: Crea propietarios desde distintos orígenes
 */
public interface PropietarioFactory {
    
    /**
     * Crea un propietario desde el origen específico
     */
    Mono<Propietario> crearPropietario(Propietario propietario);
    
    /**
     * Obtiene el tipo de origen que maneja esta factory
     */
    String getTipoOrigen();
}

