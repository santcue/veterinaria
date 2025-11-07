package co.edu.veterinaria.propietarios.domain.observer;

import co.edu.veterinaria.propietarios.domain.model.Propietario;
import reactor.core.publisher.Mono;

/**
 * Observer Pattern: Interfaz para observadores de eventos de propietarios
 */
public interface PropietarioObserver {
    
    /**
     * Notifica cuando se registra un nuevo propietario
     */
    Mono<Void> onPropietarioRegistrado(Propietario propietario);
    
    /**
     * Obtiene el nombre del observador
     */
    String getNombre();
}

