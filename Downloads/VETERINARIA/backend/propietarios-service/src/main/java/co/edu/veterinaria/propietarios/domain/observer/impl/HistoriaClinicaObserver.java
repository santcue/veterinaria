package co.edu.veterinaria.propietarios.domain.observer.impl;

import co.edu.veterinaria.propietarios.domain.model.Propietario;
import co.edu.veterinaria.propietarios.domain.observer.PropietarioObserver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * Observer Pattern: Sincroniza con el módulo de Historia Clínica
 */
@Slf4j
@Component
public class HistoriaClinicaObserver implements PropietarioObserver {
    
    @Override
    public Mono<Void> onPropietarioRegistrado(Propietario propietario) {
        log.info("HistoriaClinicaObserver: Nuevo propietario registrado - ID: {}, Email: {}", 
            propietario.getId(), propietario.getEmail());
        
        // Aquí se podría hacer una llamada al servicio de Historia Clínica
        // para crear registros iniciales o configuraciones
        
        // Por ahora solo logueamos
        return Mono.empty();
    }
    
    @Override
    public String getNombre() {
        return "HistoriaClinicaObserver";
    }
}

