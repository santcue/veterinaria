package co.edu.veterinaria.propietarios.infrastructure.web.controller;

import co.edu.veterinaria.propietarios.domain.model.Propietario;
import co.edu.veterinaria.propietarios.domain.service.PropietarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/api/propietarios")
@RequiredArgsConstructor
public class PropietarioController {
    
    private final PropietarioService propietarioService;
    
    @GetMapping
    public Mono<ResponseEntity<Flux<Propietario>>> obtenerTodos() {
        return Mono.just(ResponseEntity.ok(propietarioService.obtenerTodos()));
    }
    
    @GetMapping("/{id}")
    public Mono<ResponseEntity<Propietario>> obtenerPorId(@PathVariable Long id) {
        return propietarioService.obtenerPorId(id)
            .map(ResponseEntity::ok)
            .onErrorResume(e -> Mono.just(ResponseEntity.notFound().build()));
    }
    
    @PostMapping
    public Mono<ResponseEntity<Propietario>> crear(
            @RequestParam(defaultValue = "WEB") String origen,
            @Valid @RequestBody Propietario propietario) {
        return propietarioService.crear(origen, propietario)
            .map(p -> ResponseEntity.status(HttpStatus.CREATED).body(p))
            .onErrorResume(e -> Mono.just(ResponseEntity.badRequest().build()));
    }
    
    @PutMapping("/{id}")
    public Mono<ResponseEntity<Propietario>> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody Propietario propietario) {
        return propietarioService.actualizar(id, propietario)
            .map(ResponseEntity::ok)
            .onErrorResume(e -> Mono.just(ResponseEntity.badRequest().build()));
    }
    
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> eliminar(@PathVariable Long id) {
        return propietarioService.eliminar(id)
            .then(Mono.just(ResponseEntity.noContent().build()))
            .onErrorResume(e -> Mono.just(ResponseEntity.notFound().build()));
    }
}

