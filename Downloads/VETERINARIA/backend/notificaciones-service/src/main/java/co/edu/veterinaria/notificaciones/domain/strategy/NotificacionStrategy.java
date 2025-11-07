package co.edu.veterinaria.notificaciones.domain.strategy;

import co.edu.veterinaria.notificaciones.domain.model.Notificacion;
import reactor.core.publisher.Mono;

/**
 * Strategy Pattern: Diferentes estrategias para enviar notificaciones
 */
public interface NotificacionStrategy {
    
    /**
     * Envía la notificación usando el canal específico
     */
    Mono<Boolean> enviar(Notificacion notificacion);
    
    /**
     * Obtiene el tipo de canal que maneja esta estrategia
     */
    String getTipoCanal();
    
    /**
     * Verifica si la estrategia puede manejar el tipo de notificación
     */
    boolean puedeManejar(String tipoCanal);
}

