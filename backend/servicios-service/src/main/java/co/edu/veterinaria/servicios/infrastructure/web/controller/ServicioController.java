package co.edu.veterinaria.servicios.infrastructure.web.controller;

import co.edu.veterinaria.servicios.domain.model.Servicio;
import co.edu.veterinaria.servicios.domain.service.ServicioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/servicios")
@RequiredArgsConstructor
public class ServicioController {
    
    private final ServicioService service;
    
    @GetMapping
    public Mono<ResponseEntity<Flux<Servicio>>> obtenerTodos() {
        return Mono.just(ResponseEntity.ok(service.obtenerTodos()));
    }
    
    @GetMapping("/{id}")
    public Mono<ResponseEntity<Servicio>> obtenerPorId(@PathVariable Long id) {
        return service.obtenerPorId(id)
            .map(ResponseEntity::ok);
    }
    
    @PostMapping
    public Mono<ResponseEntity<Servicio>> crear(
            @RequestParam String tipoServicio,
            @RequestBody Servicio servicio) {
        return service.crear(tipoServicio, servicio)
            .map(ResponseEntity::ok);
    }
}

