package co.edu.veterinaria.historia.infrastructure.web.controller;

import co.edu.veterinaria.historia.domain.model.HistoriaClinica;
import co.edu.veterinaria.historia.domain.service.HistoriaClinicaService;
import co.edu.veterinaria.historia.infrastructure.web.dto.HistoriaClinicaDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/api/historias")
@RequiredArgsConstructor
public class HistoriaClinicaController {
    
    private final HistoriaClinicaService service;
    
    @PostMapping
    public Mono<ResponseEntity<HistoriaClinicaDTO>> crear(@Valid @RequestBody HistoriaClinicaDTO dto) {
        HistoriaClinica historia = HistoriaClinica.builder()
            .idCita(dto.getIdCita())
            .anamnesis(dto.getAnamnesis())
            .examenFisico(dto.getExamenFisico())
            .signosVitales(dto.getSignosVitales())
            .diagnostico(dto.getDiagnostico())
            .plan(dto.getPlan())
            .recomendaciones(dto.getRecomendaciones())
            .build();
        
        return service.crear(historia)
            .map(h -> {
                HistoriaClinicaDTO response = HistoriaClinicaDTO.builder()
                    .id(h.getId())
                    .idCita(h.getIdCita())
                    .anamnesis(h.getAnamnesis())
                    .examenFisico(h.getExamenFisico())
                    .signosVitales(h.getSignosVitales())
                    .diagnostico(h.getDiagnostico())
                    .plan(h.getPlan())
                    .recomendaciones(h.getRecomendaciones())
                    .build();
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            });
    }
    
    @GetMapping("/{id}")
    public Mono<ResponseEntity<HistoriaClinicaDTO>> obtenerPorId(@PathVariable Long id) {
        return service.obtenerPorId(id)
            .map(h -> {
                HistoriaClinicaDTO dto = HistoriaClinicaDTO.builder()
                    .id(h.getId())
                    .idCita(h.getIdCita())
                    .anamnesis(h.getAnamnesis())
                    .examenFisico(h.getExamenFisico())
                    .signosVitales(h.getSignosVitales())
                    .diagnostico(h.getDiagnostico())
                    .plan(h.getPlan())
                    .recomendaciones(h.getRecomendaciones())
                    .build();
                return ResponseEntity.ok(dto);
            });
    }
    
    @GetMapping("/cita/{idCita}")
    public Mono<ResponseEntity<HistoriaClinicaDTO>> obtenerPorCita(@PathVariable Long idCita) {
        return service.obtenerPorCita(idCita)
            .map(h -> {
                HistoriaClinicaDTO dto = HistoriaClinicaDTO.builder()
                    .id(h.getId())
                    .idCita(h.getIdCita())
                    .anamnesis(h.getAnamnesis())
                    .examenFisico(h.getExamenFisico())
                    .signosVitales(h.getSignosVitales())
                    .diagnostico(h.getDiagnostico())
                    .plan(h.getPlan())
                    .recomendaciones(h.getRecomendaciones())
                    .build();
                return ResponseEntity.ok(dto);
            });
    }
    
    @GetMapping("/{id}/renderizar")
    public Mono<ResponseEntity<String>> renderizar(@PathVariable Long id) {
        return service.renderizarHistoria(id)
            .map(ResponseEntity::ok);
    }
}

