package co.edu.veterinaria.servicios.domain.repository;

import co.edu.veterinaria.servicios.domain.model.Servicio;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ServicioRepository extends ReactiveCrudRepository<Servicio, Long> {
    Flux<Servicio> findByActivoTrue();
}

