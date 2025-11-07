package co.edu.veterinaria.inventario.infrastructure.web.controller;

import co.edu.veterinaria.inventario.domain.model.Insumo;
import co.edu.veterinaria.inventario.domain.service.InventarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/inventario")
@RequiredArgsConstructor
public class InventarioController {
    
    private final InventarioService service;
    
    @GetMapping
    public Mono<ResponseEntity<Flux<Insumo>>> obtenerTodos() {
        return Mono.just(ResponseEntity.ok(service.obtenerTodos()));
    }
    
    @GetMapping("/{id}")
    public Mono<ResponseEntity<Insumo>> obtenerPorId(@PathVariable Long id) {
        return service.obtenerPorId(id)
            .map(ResponseEntity::ok);
    }
    
    @PostMapping
    public Mono<ResponseEntity<Insumo>> crear(@RequestBody Insumo insumo) {
        return service.crear(insumo)
            .map(ResponseEntity::ok);
    }
    
    @PutMapping("/{id}")
    public Mono<ResponseEntity<Insumo>> actualizar(@PathVariable Long id, @RequestBody Insumo insumo) {
        return service.actualizar(id, insumo)
            .map(ResponseEntity::ok);
    }
    
    @PostMapping("/{id}/descontar")
    public Mono<ResponseEntity<Insumo>> descontarStock(
            @PathVariable Long id,
            @RequestParam BigDecimal cantidad) {
        return service.descontarStock(id, cantidad)
            .map(ResponseEntity::ok);
    }
    
    @GetMapping("/alertas/stock")
    public Mono<ResponseEntity<Flux<Insumo>>> obtenerAlertasStock() {
        return Mono.just(ResponseEntity.ok(service.obtenerAlertasStock()));
    }
    
    @GetMapping("/alertas/vencimientos")
    public Mono<ResponseEntity<Flux<Insumo>>> obtenerVencimientosProximos(
            @RequestParam(defaultValue = "30") int dias) {
        return Mono.just(ResponseEntity.ok(service.obtenerVencimientosProximos(dias)));
    }
}

