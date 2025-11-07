package co.edu.veterinaria.propietarios.domain.factory.impl;

import co.edu.veterinaria.propietarios.domain.factory.PropietarioFactory;
import co.edu.veterinaria.propietarios.domain.model.Propietario;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

/**
 * Factory Method: Crea propietarios desde recepción
 */
@Slf4j
@Component
public class PropietarioRecepcionFactory implements PropietarioFactory {
    
    @Override
    public Mono<Propietario> crearPropietario(Propietario propietario) {
        log.debug("Creando propietario desde recepción: {}", propietario.getDocumento());
        
        // Validaciones específicas para registro en recepción
        if (propietario.getDocumento() == null || propietario.getDocumento().trim().isEmpty()) {
            return Mono.error(new IllegalArgumentException("El documento es obligatorio para registro en recepción"));
        }
        
        // Configurar datos específicos del origen recepción
        Propietario propietarioRecepcion = Propietario.builder()
            .documento(propietario.getDocumento().trim())
            .nombres(propietario.getNombres())
            .apellidos(propietario.getApellidos())
            .email(propietario.getEmail() != null ? propietario.getEmail().toLowerCase().trim() : null)
            .telefono(propietario.getTelefono())
            .direccion(propietario.getDireccion())
            .activo(true)
            .fechaRegistro(LocalDateTime.now())
            .build();
        
        return Mono.just(propietarioRecepcion);
    }
    
    @Override
    public String getTipoOrigen() {
        return "RECEPCION";
    }
}

