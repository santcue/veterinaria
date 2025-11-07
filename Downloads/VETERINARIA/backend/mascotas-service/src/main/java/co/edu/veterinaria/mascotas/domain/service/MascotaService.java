package co.edu.veterinaria.mascotas.domain.service;

import co.edu.veterinaria.mascotas.domain.exception.EstrategiaValidacionNotFoundException;
import co.edu.veterinaria.mascotas.domain.exception.MascotaNotFoundException;
import co.edu.veterinaria.mascotas.domain.model.Mascota;
import co.edu.veterinaria.mascotas.domain.repository.MascotaRepository;
import co.edu.veterinaria.mascotas.domain.strategy.ValidacionMascotaStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service Pattern: Maneja la lógica de negocio para Mascotas
 * Repository Pattern: Usa MascotaRepository para acceso a datos
 * Strategy Pattern: Usa ValidacionMascotaStrategy para validaciones específicas por especie
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MascotaService {
    
    private final MascotaRepository mascotaRepository;
    private final List<ValidacionMascotaStrategy> validacionStrategies;
    
    /**
     * Obtiene todas las mascotas activas
     */
    public Flux<Mascota> obtenerTodas() {
        log.debug("Obteniendo todas las mascotas activas");
        return mascotaRepository.findByActivoTrue();
    }
    
    /**
     * Obtiene una mascota por ID
     */
    public Mono<Mascota> obtenerPorId(Long id) {
        log.debug("Obteniendo mascota con ID: {}", id);
        return mascotaRepository.findByIdAndActivoTrue(id)
            .switchIfEmpty(Mono.error(new MascotaNotFoundException(id)));
    }
    
    /**
     * Obtiene mascotas por propietario
     */
    public Flux<Mascota> obtenerPorPropietario(Long idPropietario) {
        log.debug("Obteniendo mascotas del propietario: {}", idPropietario);
        return mascotaRepository.findByIdPropietarioAndActivoTrue(idPropietario);
    }
    
    /**
     * Busca mascotas por nombre
     */
    public Flux<Mascota> buscarPorNombre(String nombre) {
        log.debug("Buscando mascotas con nombre: {}", nombre);
        return mascotaRepository.findByNombreContainingIgnoreCase("%" + nombre + "%");
    }
    
    /**
     * Crea una nueva mascota con validación según especie
     */
    public Mono<Mascota> crear(Mascota mascota) {
        log.debug("Creando nueva mascota: {}", mascota.getNombre());
        
        // Buscar estrategia de validación apropiada
        ValidacionMascotaStrategy estrategia = encontrarEstrategia(mascota.getIdEspecie());
        
        // Validar usando la estrategia
        return estrategia.validar(mascota)
            .flatMap(m -> {
                m.setActivo(true);
                m.setFechaRegistro(LocalDateTime.now());
                return mascotaRepository.save(m);
            })
            .doOnSuccess(m -> log.info("Mascota creada exitosamente: {}", m.getId()))
            .doOnError(error -> log.error("Error al crear mascota: {}", error.getMessage()));
    }
    
    /**
     * Actualiza una mascota existente
     */
    public Mono<Mascota> actualizar(Long id, Mascota mascotaActualizada) {
        log.debug("Actualizando mascota con ID: {}", id);
        
        return mascotaRepository.findByIdAndActivoTrue(id)
            .switchIfEmpty(Mono.error(new MascotaNotFoundException(id)))
            .flatMap(mascotaExistente -> {
                // Buscar estrategia de validación
                ValidacionMascotaStrategy estrategia = encontrarEstrategia(mascotaActualizada.getIdEspecie());
                
                // Actualizar campos
                mascotaExistente.setNombre(mascotaActualizada.getNombre());
                mascotaExistente.setFechaNacimiento(mascotaActualizada.getFechaNacimiento());
                mascotaExistente.setSexo(mascotaActualizada.getSexo());
                mascotaExistente.setColor(mascotaActualizada.getColor());
                mascotaExistente.setMicrochip(mascotaActualizada.getMicrochip());
                mascotaExistente.setPesoKg(mascotaActualizada.getPesoKg());
                mascotaExistente.setEsterilizado(mascotaActualizada.getEsterilizado());
                mascotaExistente.setIdRaza(mascotaActualizada.getIdRaza());
                
                // Validar usando la estrategia
                return estrategia.validar(mascotaExistente)
                    .flatMap(mascotaRepository::save);
            })
            .doOnSuccess(m -> log.info("Mascota actualizada exitosamente: {}", m.getId()))
            .doOnError(error -> log.error("Error al actualizar mascota: {}", error.getMessage()));
    }
    
    /**
     * Elimina (desactiva) una mascota
     */
    public Mono<Void> eliminar(Long id) {
        log.debug("Eliminando (desactivando) mascota con ID: {}", id);
        
        return mascotaRepository.findByIdAndActivoTrue(id)
            .switchIfEmpty(Mono.error(new MascotaNotFoundException(id)))
            .flatMap(mascota -> {
                mascota.setActivo(false);
                return mascotaRepository.save(mascota)
                    .then();
            })
            .doOnSuccess(v -> log.info("Mascota eliminada exitosamente: {}", id))
            .doOnError(error -> log.error("Error al eliminar mascota: {}", error.getMessage()));
    }
    
    /**
     * Encuentra la estrategia de validación apropiada para la especie
     */
    private ValidacionMascotaStrategy encontrarEstrategia(Long idEspecie) {
        return validacionStrategies.stream()
            .filter(strategy -> strategy.aplicaPara(idEspecie))
            .findFirst()
            .orElseThrow(() -> new EstrategiaValidacionNotFoundException(idEspecie));
    }
}

