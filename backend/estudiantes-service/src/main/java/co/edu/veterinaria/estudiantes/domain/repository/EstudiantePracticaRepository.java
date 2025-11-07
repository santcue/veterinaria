package co.edu.veterinaria.estudiantes.domain.repository;

import co.edu.veterinaria.estudiantes.domain.model.EstudiantePractica;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface EstudiantePracticaRepository extends ReactiveCrudRepository<EstudiantePractica, Long> {
    Mono<EstudiantePractica> findByCodigoAndActivoTrue(String codigo);
    Flux<EstudiantePractica> findByActivoTrue();
    Mono<EstudiantePractica> findByIdAndActivoTrue(Long id);
}

