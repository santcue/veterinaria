package co.edu.veterinaria.propietarios.domain.factory.impl;

import co.edu.veterinaria.propietarios.domain.factory.PropietarioFactory;
import co.edu.veterinaria.propietarios.domain.model.Propietario;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

/**
 * Factory Method: Crea propietarios desde el portal web
 */
@Slf4j
@Component
public class PropietarioWebFactory implements PropietarioFactory {
    
    @Override
    public Mono<Propietario> crearPropietario(Propietario propietario) {
        log.debug("Creando propietario desde portal web: {}", propietario.getEmail());
        
        // Validaciones específicas para registro web
        if (propietario.getEmail() == null || propietario.getEmail().trim().isEmpty()) {
            return Mono.error(new IllegalArgumentException("El email es obligatorio para registro web"));
        }
        
        // Configurar datos específicos del origen web
        Propietario propietarioWeb = Propietario.builder()
            .documento(propietario.getDocumento())
            .nombres(propietario.getNombres())
            .apellidos(propietario.getApellidos())
            .email(propietario.getEmail().toLowerCase().trim())
            .telefono(propietario.getTelefono())
            .direccion(propietario.getDireccion())
            .activo(true)
            .fechaRegistro(LocalDateTime.now())
            .build();
        
        return Mono.just(propietarioWeb);
    }
    
    @Override
    public String getTipoOrigen() {
        return "WEB";
    }
}

