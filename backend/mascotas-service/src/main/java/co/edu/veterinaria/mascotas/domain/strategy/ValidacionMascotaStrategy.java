package co.edu.veterinaria.mascotas.domain.strategy;

import co.edu.veterinaria.mascotas.domain.model.Mascota;
import reactor.core.publisher.Mono;

/**
 * Strategy Pattern: Define diferentes estrategias de validación según la especie
 */
public interface ValidacionMascotaStrategy {
    
    /**
     * Valida si la estrategia aplica para la especie dada
     */
    boolean aplicaPara(Long idEspecie);
    
    /**
     * Valida los datos de la mascota según las reglas de la especie
     */
    Mono<Mascota> validar(Mascota mascota);
    
    /**
     * Obtiene el nombre de la especie para la que aplica esta estrategia
     */
    String getNombreEspecie();
}

