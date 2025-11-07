package co.edu.veterinaria.prescripciones.infrastructure.web.controller;

import co.edu.veterinaria.prescripciones.domain.builder.PrescripcionBuilder;
import co.edu.veterinaria.prescripciones.domain.model.Prescripcion;
import co.edu.veterinaria.prescripciones.domain.service.PrescripcionService;
import co.edu.veterinaria.prescripciones.infrastructure.web.dto.PrescripcionDTO;
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
@RequestMapping("/api/prescripciones")
@RequiredArgsConstructor
public class PrescripcionController {
    
    private final PrescripcionService service;
    
    @PostMapping
    public Mono<ResponseEntity<PrescripcionDTO>> crear(@Valid @RequestBody PrescripcionDTO dto) {
        log.debug("POST /api/prescripciones - Creando prescripción con Builder Pattern");
        
        PrescripcionBuilder builder = new PrescripcionBuilder()
            .conHistoria(dto.getIdHistoria())
            .conIndicaciones(dto.getIndicacionesGenerales());
        
        // Agregar medicamentos usando Builder
        if (dto.getDetalles() != null) {
            for (PrescripcionDTO.PrescripcionDetalleDTO detalle : dto.getDetalles()) {
                builder.agregarMedicamento(
                    detalle.getIdMedicamento(),
                    detalle.getDosis(),
                    detalle.getFrecuencia(),
                    detalle.getDuracion(),
                    detalle.getObservaciones()
                );
            }
        }
        
        return service.crear(builder)
            .map(p -> {
                PrescripcionDTO response = PrescripcionDTO.builder()
                    .id(p.getId())
                    .idHistoria(p.getIdHistoria())
                    .indicacionesGenerales(p.getIndicacionesGenerales())
                    .build();
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            });
    }
    
    @GetMapping("/{id}")
    public Mono<ResponseEntity<PrescripcionDTO>> obtenerPorId(@PathVariable Long id) {
        return service.obtenerPorId(id)
            .map(p -> {
                PrescripcionDTO dto = PrescripcionDTO.builder()
                    .id(p.getId())
                    .idHistoria(p.getIdHistoria())
                    .indicacionesGenerales(p.getIndicacionesGenerales())
                    .build();
                return ResponseEntity.ok(dto);
            });
    }
    
    @GetMapping("/historia/{idHistoria}")
    public Mono<ResponseEntity<Flux<PrescripcionDTO>>> obtenerPorHistoria(@PathVariable Long idHistoria) {
        Flux<PrescripcionDTO> prescripciones = service.obtenerPorHistoria(idHistoria)
            .map(p -> PrescripcionDTO.builder()
                .id(p.getId())
                .idHistoria(p.getIdHistoria())
                .indicacionesGenerales(p.getIndicacionesGenerales())
                .build());
        return Mono.just(ResponseEntity.ok(prescripciones));
    }
}

