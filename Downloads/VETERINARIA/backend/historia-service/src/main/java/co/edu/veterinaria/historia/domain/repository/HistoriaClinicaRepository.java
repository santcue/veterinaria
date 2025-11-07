package co.edu.veterinaria.historia.domain.repository;

import co.edu.veterinaria.historia.domain.model.HistoriaClinica;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface HistoriaClinicaRepository extends ReactiveCrudRepository<HistoriaClinica, Long> {
    Mono<HistoriaClinica> findByIdCita(Long idCita);
}

