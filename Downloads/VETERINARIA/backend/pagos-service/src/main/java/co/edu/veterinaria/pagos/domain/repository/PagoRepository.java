package co.edu.veterinaria.pagos.domain.repository;

import co.edu.veterinaria.pagos.domain.model.Pago;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface PagoRepository extends ReactiveCrudRepository<Pago, Long> {
    Flux<Pago> findByIdFactura(Long idFactura);
}

