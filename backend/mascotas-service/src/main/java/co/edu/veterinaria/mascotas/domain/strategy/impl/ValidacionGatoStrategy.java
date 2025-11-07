package co.edu.veterinaria.mascotas.domain.strategy.impl;

import co.edu.veterinaria.mascotas.domain.model.Mascota;
import co.edu.veterinaria.mascotas.domain.strategy.ValidacionMascotaStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

/**
 * Strategy Pattern: Validación específica para gatos
 */
@Slf4j
@Component
public class ValidacionGatoStrategy implements ValidacionMascotaStrategy {
    
    private static final Long ID_ESPECIE_GATO = 2L;
    private static final BigDecimal PESO_MINIMO_GATO = new BigDecimal("0.3");
    private static final BigDecimal PESO_MAXIMO_GATO = new BigDecimal("15");
    
    @Override
    public boolean aplicaPara(Long idEspecie) {
        return ID_ESPECIE_GATO.equals(idEspecie);
    }
    
    @Override
    public Mono<Mascota> validar(Mascota mascota) {
        log.debug("Validando mascota gato: {}", mascota.getNombre());
        
        // Validar peso
        if (mascota.getPesoKg() != null) {
            if (mascota.getPesoKg().compareTo(PESO_MINIMO_GATO) < 0) {
                return Mono.error(new IllegalArgumentException(
                    "El peso de un gato no puede ser menor a " + PESO_MINIMO_GATO + " kg"));
            }
            if (mascota.getPesoKg().compareTo(PESO_MAXIMO_GATO) > 0) {
                return Mono.error(new IllegalArgumentException(
                    "El peso de un gato no puede ser mayor a " + PESO_MAXIMO_GATO + " kg"));
            }
        }
        
        // Validar que tenga nombre
        if (mascota.getNombre() == null || mascota.getNombre().trim().isEmpty()) {
            return Mono.error(new IllegalArgumentException("El nombre de la mascota es obligatorio"));
        }
        
        // Validar sexo
        if (mascota.getSexo() == null || (!mascota.getSexo().equals("M") && !mascota.getSexo().equals("H"))) {
            return Mono.error(new IllegalArgumentException("El sexo debe ser M (Macho) o H (Hembra)"));
        }
        
        return Mono.just(mascota);
    }
    
    @Override
    public String getNombreEspecie() {
        return "Gato";
    }
}

