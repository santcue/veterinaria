package co.edu.veterinaria.mascotas.infrastructure.web.controller;

import co.edu.veterinaria.mascotas.domain.service.MascotaService;
import co.edu.veterinaria.mascotas.infrastructure.web.dto.MascotaDTO;
import co.edu.veterinaria.mascotas.infrastructure.web.mapper.MascotaMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Controller REST para el módulo de Mascotas
 * Facade Pattern: Proporciona un punto de acceso único y simplificado
 */
@Slf4j
@RestController
@RequestMapping("/api/mascotas")
@RequiredArgsConstructor
public class MascotaController {
    
    private final MascotaService mascotaService;
    private final MascotaMapper mascotaMapper;
    
    @GetMapping
    public Mono<ResponseEntity<Flux<MascotaDTO>>> obtenerTodas() {
        log.debug("GET /api/mascotas - Obteniendo todas las mascotas");
        Flux<MascotaDTO> mascotas = mascotaService.obtenerTodas()
            .map(mascotaMapper::toDTO);
        return Mono.just(ResponseEntity.ok(mascotas));
    }
    
    @GetMapping("/{id}")
    public Mono<ResponseEntity<MascotaDTO>> obtenerPorId(@PathVariable Long id) {
        log.debug("GET /api/mascotas/{} - Obteniendo mascota por ID", id);
        return mascotaService.obtenerPorId(id)
            .map(mascotaMapper::toDTO)
            .map(ResponseEntity::ok);
    }
    
    @GetMapping("/propietario/{idPropietario}")
    public Mono<ResponseEntity<Flux<MascotaDTO>>> obtenerPorPropietario(@PathVariable Long idPropietario) {
        log.debug("GET /api/mascotas/propietario/{} - Obteniendo mascotas por propietario", idPropietario);
        Flux<MascotaDTO> mascotas = mascotaService.obtenerPorPropietario(idPropietario)
            .map(mascotaMapper::toDTO);
        return Mono.just(ResponseEntity.ok(mascotas));
    }
    
    @GetMapping("/buscar")
    public Mono<ResponseEntity<Flux<MascotaDTO>>> buscarPorNombre(@RequestParam String nombre) {
        log.debug("GET /api/mascotas/buscar?nombre={} - Buscando mascotas por nombre", nombre);
        Flux<MascotaDTO> mascotas = mascotaService.buscarPorNombre(nombre)
            .map(mascotaMapper::toDTO);
        return Mono.just(ResponseEntity.ok(mascotas));
    }
    
    @PostMapping
    public Mono<ResponseEntity<MascotaDTO>> crear(@Valid @RequestBody MascotaDTO mascotaDTO) {
        log.debug("POST /api/mascotas - Creando nueva mascota: {}", mascotaDTO.getNombre());
        return mascotaService.crear(mascotaMapper.toEntity(mascotaDTO))
            .map(mascotaMapper::toDTO)
            .map(dto -> ResponseEntity.status(HttpStatus.CREATED).body(dto));
    }
    
    @PutMapping("/{id}")
    public Mono<ResponseEntity<MascotaDTO>> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody MascotaDTO mascotaDTO) {
        log.debug("PUT /api/mascotas/{} - Actualizando mascota", id);
        return mascotaService.actualizar(id, mascotaMapper.toEntity(mascotaDTO))
            .map(mascotaMapper::toDTO)
            .map(ResponseEntity::ok);
    }
    
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> eliminar(@PathVariable Long id) {
        log.debug("DELETE /api/mascotas/{} - Eliminando mascota", id);
        return mascotaService.eliminar(id)
            .then(Mono.just(ResponseEntity.noContent().build()));
    }
}

