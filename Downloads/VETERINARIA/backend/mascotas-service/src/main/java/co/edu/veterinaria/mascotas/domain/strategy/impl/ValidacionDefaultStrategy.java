package co.edu.veterinaria.mascotas.domain.strategy.impl;

import co.edu.veterinaria.mascotas.domain.model.Mascota;
import co.edu.veterinaria.mascotas.domain.strategy.ValidacionMascotaStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * Strategy Pattern: Validación por defecto para especies no específicas
 */
@Slf4j
@Component
public class ValidacionDefaultStrategy implements ValidacionMascotaStrategy {
    
    @Override
    public boolean aplicaPara(Long idEspecie) {
        return true; // Estrategia por defecto
    }
    
    @Override
    public Mono<Mascota> validar(Mascota mascota) {
        log.debug("Validando mascota con estrategia por defecto: {}", mascota.getNombre());
        
        // Validaciones básicas comunes a todas las especies
        if (mascota.getNombre() == null || mascota.getNombre().trim().isEmpty()) {
            return Mono.error(new IllegalArgumentException("El nombre de la mascota es obligatorio"));
        }
        
        if (mascota.getSexo() == null || (!mascota.getSexo().equals("M") && !mascota.getSexo().equals("H"))) {
            return Mono.error(new IllegalArgumentException("El sexo debe ser M (Macho) o H (Hembra)"));
        }
        
        return Mono.just(mascota);
    }
    
    @Override
    public String getNombreEspecie() {
        return "General";
    }
}

