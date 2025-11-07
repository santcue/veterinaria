package co.edu.veterinaria.inventario.domain.repository;

import co.edu.veterinaria.inventario.domain.model.Insumo;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;

@Repository
public interface InsumoRepository extends ReactiveCrudRepository<Insumo, Long> {
    Flux<Insumo> findByActivoTrue();
    
    @Query("SELECT * FROM insumo WHERE id = :id AND activo = true")
    Mono<Insumo> findByIdAndActivoTrue(Long id);
    
    @Query("SELECT * FROM insumo WHERE stock < :stockMinimo AND activo = true")
    Flux<Insumo> findByStockLessThan(BigDecimal stockMinimo);
    
    @Query("SELECT * FROM insumo WHERE fecha_vencimiento < :fecha AND activo = true")
    Flux<Insumo> findByFechaVencimientoBefore(LocalDate fecha);
}

