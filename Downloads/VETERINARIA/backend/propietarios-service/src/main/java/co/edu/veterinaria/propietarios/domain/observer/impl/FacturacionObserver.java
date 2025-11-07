package co.edu.veterinaria.propietarios.domain.observer.impl;

import co.edu.veterinaria.propietarios.domain.model.Propietario;
import co.edu.veterinaria.propietarios.domain.observer.PropietarioObserver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * Observer Pattern: Sincroniza con el módulo de Facturación
 */
@Slf4j
@Component
public class FacturacionObserver implements PropietarioObserver {
    
    @Override
    public Mono<Void> onPropietarioRegistrado(Propietario propietario) {
        log.info("FacturacionObserver: Nuevo propietario registrado - ID: {}, Documento: {}", 
            propietario.getId(), propietario.getDocumento());
        
        // Aquí se podría crear un cliente en el sistema de facturación
        // o configurar métodos de pago preferidos
        
        return Mono.empty();
    }
    
    @Override
    public String getNombre() {
        return "FacturacionObserver";
    }
}

