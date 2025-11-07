package co.edu.veterinaria.notificaciones.domain.strategy.impl;

import co.edu.veterinaria.notificaciones.domain.model.Notificacion;
import co.edu.veterinaria.notificaciones.domain.strategy.NotificacionStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * Strategy: Notificaciones internas del sistema
 */
@Slf4j
@Component
public class InternaNotificacionStrategy implements NotificacionStrategy {
    
    @Override
    public Mono<Boolean> enviar(Notificacion notificacion) {
        log.info("Guardando notificación INTERNA para usuario: {}", notificacion.getDestinatario());
        log.info("Título: {}", notificacion.getTitulo());
        
        // Las notificaciones internas se guardan en la base de datos
        // y se muestran en el panel del usuario
        return Mono.just(true)
            .doOnSuccess(result -> log.info("Notificación interna guardada exitosamente"));
    }
    
    @Override
    public String getTipoCanal() {
        return "INTERNA";
    }
    
    @Override
    public boolean puedeManejar(String tipoCanal) {
        return "INTERNA".equalsIgnoreCase(tipoCanal);
    }
}

