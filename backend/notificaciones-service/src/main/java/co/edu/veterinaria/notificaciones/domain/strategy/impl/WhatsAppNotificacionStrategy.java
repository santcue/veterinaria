package co.edu.veterinaria.notificaciones.domain.strategy.impl;

import co.edu.veterinaria.notificaciones.domain.model.Notificacion;
import co.edu.veterinaria.notificaciones.domain.strategy.NotificacionStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * Strategy: Envío de notificaciones por WhatsApp
 */
@Slf4j
@Component
public class WhatsAppNotificacionStrategy implements NotificacionStrategy {
    
    @Override
    public Mono<Boolean> enviar(Notificacion notificacion) {
        log.info("Enviando notificación por WHATSAPP a: {}", notificacion.getDestinatario());
        log.info("Mensaje: {}", notificacion.getMensaje());
        
        // Aquí se integraría con WhatsApp Business API o Twilio
        return Mono.just(true)
            .doOnSuccess(result -> log.info("WhatsApp enviado exitosamente"))
            .doOnError(error -> log.error("Error al enviar WhatsApp: {}", error.getMessage()));
    }
    
    @Override
    public String getTipoCanal() {
        return "WHATSAPP";
    }
    
    @Override
    public boolean puedeManejar(String tipoCanal) {
        return "WHATSAPP".equalsIgnoreCase(tipoCanal);
    }
}

