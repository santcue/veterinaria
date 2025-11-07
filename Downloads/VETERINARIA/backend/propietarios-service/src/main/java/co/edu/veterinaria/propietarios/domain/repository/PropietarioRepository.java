package co.edu.veterinaria.propietarios.domain.repository;

import co.edu.veterinaria.propietarios.domain.model.Propietario;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface PropietarioRepository extends ReactiveCrudRepository<Propietario, Long> {
    Mono<Propietario> findByDocumentoAndActivoTrue(String documento);
    Mono<Propietario> findByEmailAndActivoTrue(String email);
    Flux<Propietario> findByActivoTrue();
    Mono<Propietario> findByIdAndActivoTrue(Long id);
}

