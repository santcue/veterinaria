package co.edu.veterinaria.citas.infrastructure.web.controller;

import co.edu.veterinaria.citas.domain.model.Cita;
import co.edu.veterinaria.citas.domain.service.CitaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/citas")
@RequiredArgsConstructor
public class CitaController {
    
    private final CitaService citaService;
    
    @GetMapping
    public Mono<ResponseEntity<Flux<Cita>>> obtenerTodas() {
        return Mono.just(ResponseEntity.ok(citaService.obtenerTodas()));
    }
    
    @PostMapping
    public Mono<ResponseEntity<Cita>> crear(
            @RequestParam(defaultValue = "PRIMER_DISPONIBLE") String estrategia,
            @RequestBody Cita cita) {
        return citaService.crear(estrategia, cita)
            .map(ResponseEntity::ok);
    }
    
    @PatchMapping("/{id}/confirmar")
    public Mono<ResponseEntity<Cita>> confirmar(@PathVariable Long id) {
        return citaService.confirmar(id)
            .map(ResponseEntity::ok);
    }
}

