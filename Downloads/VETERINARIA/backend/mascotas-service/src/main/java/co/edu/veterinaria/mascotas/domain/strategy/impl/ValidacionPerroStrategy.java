package co.edu.veterinaria.mascotas.domain.strategy.impl;

import co.edu.veterinaria.mascotas.domain.model.Mascota;
import co.edu.veterinaria.mascotas.domain.strategy.ValidacionMascotaStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

/**
 * Strategy Pattern: Validación específica para perros
 */
@Slf4j
@Component
public class ValidacionPerroStrategy implements ValidacionMascotaStrategy {
    
    private static final Long ID_ESPECIE_PERRO = 1L;
    private static final BigDecimal PESO_MINIMO_PERRO = new BigDecimal("0.5");
    private static final BigDecimal PESO_MAXIMO_PERRO = new BigDecimal("100");
    
    @Override
    public boolean aplicaPara(Long idEspecie) {
        return ID_ESPECIE_PERRO.equals(idEspecie);
    }
    
    @Override
    public Mono<Mascota> validar(Mascota mascota) {
        log.debug("Validando mascota perro: {}", mascota.getNombre());
        
        // Validar peso
        if (mascota.getPesoKg() != null) {
            if (mascota.getPesoKg().compareTo(PESO_MINIMO_PERRO) < 0) {
                return Mono.error(new IllegalArgumentException(
                    "El peso de un perro no puede ser menor a " + PESO_MINIMO_PERRO + " kg"));
            }
            if (mascota.getPesoKg().compareTo(PESO_MAXIMO_PERRO) > 0) {
                return Mono.error(new IllegalArgumentException(
                    "El peso de un perro no puede ser mayor a " + PESO_MAXIMO_PERRO + " kg"));
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
        return "Perro";
    }
}

