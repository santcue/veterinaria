package co.edu.veterinaria.pagos.domain.repository;

import co.edu.veterinaria.pagos.domain.model.Factura;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface FacturaRepository extends ReactiveCrudRepository<Factura, Long> {
    
    @Query("SELECT * FROM factura WHERE numero_factura = :numeroFactura")
    Mono<Factura> findByNumeroFactura(String numeroFactura);
    
    @Query("SELECT * FROM factura WHERE id_propietario = :idPropietario")
    Flux<Factura> findByIdPropietario(Long idPropietario);
    
    @Query("SELECT * FROM factura WHERE estado = :estado")
    Flux<Factura> findByEstado(String estado);
}

