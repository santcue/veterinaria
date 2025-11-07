package co.edu.veterinaria.prescripciones.domain.repository;

import co.edu.veterinaria.prescripciones.domain.model.Prescripcion;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface PrescripcionRepository extends ReactiveCrudRepository<Prescripcion, Long> {
    Flux<Prescripcion> findByIdHistoria(Long idHistoria);
}

