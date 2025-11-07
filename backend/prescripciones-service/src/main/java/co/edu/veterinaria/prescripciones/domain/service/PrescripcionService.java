package co.edu.veterinaria.prescripciones.domain.service;

import co.edu.veterinaria.prescripciones.domain.builder.PrescripcionBuilder;
import co.edu.veterinaria.prescripciones.domain.model.Prescripcion;
import co.edu.veterinaria.prescripciones.domain.repository.PrescripcionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Pattern: Lógica de negocio para Prescripciones
 * Builder Pattern: Usa PrescripcionBuilder para construcción paso a paso
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PrescripcionService {
    
    private final PrescripcionRepository repository;
    
    public Mono<Prescripcion> crear(PrescripcionBuilder builder) {
        log.debug("Creando prescripción usando Builder Pattern");
        return builder.construir()
            .flatMap(repository::save)
            .doOnSuccess(p -> log.info("Prescripción creada: {}", p.getId()));
    }
    
    public Mono<Prescripcion> obtenerPorId(Long id) {
        return repository.findById(id)
            .switchIfEmpty(Mono.error(new RuntimeException("Prescripción no encontrada")));
    }
    
    public Flux<Prescripcion> obtenerPorHistoria(Long idHistoria) {
        return repository.findByIdHistoria(idHistoria);
    }
}

