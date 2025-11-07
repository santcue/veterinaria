package co.edu.veterinaria.inventario.domain.service;

import co.edu.veterinaria.inventario.domain.model.Insumo;
import co.edu.veterinaria.inventario.domain.repository.InsumoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * Service Pattern: Lógica de negocio para Inventario
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class InventarioService {
    
    private final InsumoRepository repository;
    
    public Flux<Insumo> obtenerTodos() {
        return repository.findByActivoTrue();
    }
    
    public Mono<Insumo> obtenerPorId(Long id) {
        return repository.findByIdAndActivoTrue(id)
            .switchIfEmpty(Mono.error(new RuntimeException("Insumo no encontrado")));
    }
    
    public Mono<Insumo> crear(Insumo insumo) {
        insumo.setActivo(true);
        insumo.setFechaRegistro(LocalDateTime.now());
        return repository.save(insumo)
            .doOnSuccess(i -> log.info("Insumo creado: {}", i.getId()));
    }
    
    public Mono<Insumo> actualizar(Long id, Insumo insumo) {
        return repository.findByIdAndActivoTrue(id)
            .switchIfEmpty(Mono.error(new RuntimeException("Insumo no encontrado")))
            .flatMap(existente -> {
                existente.setNombre(insumo.getNombre());
                existente.setDescripcion(insumo.getDescripcion());
                existente.setCategoria(insumo.getCategoria());
                existente.setStockMinimo(insumo.getStockMinimo());
                existente.setPrecioUnitario(insumo.getPrecioUnitario());
                existente.setFechaVencimiento(insumo.getFechaVencimiento());
                existente.setUnidadMedida(insumo.getUnidadMedida());
                return repository.save(existente);
            });
    }
    
    public Mono<Insumo> descontarStock(Long id, BigDecimal cantidad) {
        return repository.findByIdAndActivoTrue(id)
            .switchIfEmpty(Mono.error(new RuntimeException("Insumo no encontrado")))
            .flatMap(insumo -> {
                BigDecimal nuevoStock = insumo.getStock().subtract(cantidad);
                if (nuevoStock.compareTo(BigDecimal.ZERO) < 0) {
                    return Mono.error(new RuntimeException("Stock insuficiente"));
                }
                insumo.setStock(nuevoStock);
                return repository.save(insumo);
            })
            .doOnSuccess(i -> log.info("Stock descontado para insumo {}: {}", i.getId(), cantidad));
    }
    
    public Flux<Insumo> obtenerAlertasStock() {
        return repository.findByActivoTrue()
            .filter(insumo -> insumo.getStock().compareTo(insumo.getStockMinimo()) <= 0);
    }
    
    public Flux<Insumo> obtenerVencimientosProximos(int dias) {
        LocalDate fechaLimite = LocalDate.now().plusDays(dias);
        return repository.findByFechaVencimientoBefore(fechaLimite)
            .filter(insumo -> insumo.getFechaVencimiento() != null);
    }
}

