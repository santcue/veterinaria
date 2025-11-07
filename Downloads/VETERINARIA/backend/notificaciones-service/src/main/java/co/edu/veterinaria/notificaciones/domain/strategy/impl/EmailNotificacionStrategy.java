package co.edu.veterinaria.notificaciones.domain.strategy.impl;

import co.edu.veterinaria.notificaciones.domain.model.Notificacion;
import co.edu.veterinaria.notificaciones.domain.strategy.NotificacionStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * Strategy: Envío de notificaciones por Email
 */
@Slf4j
@Component
public class EmailNotificacionStrategy implements NotificacionStrategy {
    
    @Override
    public Mono<Boolean> enviar(Notificacion notificacion) {
        log.info("Enviando notificación por EMAIL a: {}", notificacion.getDestinatario());
        log.info("Título: {}", notificacion.getTitulo());
        log.info("Mensaje: {}", notificacion.getMensaje());
        
        // Aquí se integraría con un servicio de email real (SendGrid, AWS SES, etc.)
        // Por ahora simulamos el envío
        
        return Mono.just(true)
            .doOnSuccess(result -> log.info("Email enviado exitosamente"))
            .doOnError(error -> log.error("Error al enviar email: {}", error.getMessage()));
    }
    
    @Override
    public String getTipoCanal() {
        return "EMAIL";
    }
    
    @Override
    public boolean puedeManejar(String tipoCanal) {
        return "EMAIL".equalsIgnoreCase(tipoCanal);
    }
}

