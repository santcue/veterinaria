package co.edu.veterinaria.notificaciones.domain.service;

import co.edu.veterinaria.notificaciones.domain.model.Notificacion;
import co.edu.veterinaria.notificaciones.domain.strategy.NotificacionStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * Service Pattern: Lógica de negocio para Notificaciones
 * Strategy Pattern: Usa NotificacionStrategy para diferentes canales
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class NotificacionService {
    
    private final List<NotificacionStrategy> estrategias;
    
    public Mono<Boolean> enviarNotificacion(Notificacion notificacion) {
        log.debug("Enviando notificación por canal: {}", notificacion.getTipoCanal());
        
        NotificacionStrategy estrategia = encontrarEstrategia(notificacion.getTipoCanal());
        
        return estrategia.enviar(notificacion)
            .doOnSuccess(result -> {
                if (result) {
                    log.info("Notificación enviada exitosamente por {}", notificacion.getTipoCanal());
                }
            })
            .doOnError(error -> log.error("Error al enviar notificación: {}", error.getMessage()));
    }
    
    private NotificacionStrategy encontrarEstrategia(String tipoCanal) {
        return estrategias.stream()
            .filter(strategy -> strategy.puedeManejar(tipoCanal))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("No se encontró estrategia para el canal: " + tipoCanal));
    }
}

