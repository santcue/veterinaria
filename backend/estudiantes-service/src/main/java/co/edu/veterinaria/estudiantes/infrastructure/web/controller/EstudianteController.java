package co.edu.veterinaria.estudiantes.infrastructure.web.controller;

import co.edu.veterinaria.estudiantes.domain.model.EstudiantePractica;
import co.edu.veterinaria.estudiantes.domain.service.EstudianteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/estudiantes")
@RequiredArgsConstructor
public class EstudianteController {
    
    private final EstudianteService service;
    
    @PostMapping("/{idEstudiante}/asignar-cita/{idCita}")
    public Mono<ResponseEntity<Boolean>> asignarACita(
            @PathVariable Long idEstudiante,
            @PathVariable Long idCita) {
        return service.asignarACita(idEstudiante, idCita)
            .map(ResponseEntity::ok);
    }
    
    @PutMapping("/{id}/estado")
    public Mono<ResponseEntity<EstudiantePractica>> cambiarEstado(
            @PathVariable Long id,
            @RequestParam String nuevoEstado,
            @RequestBody EstudiantePractica estudiante) {
        return service.cambiarEstado(estudiante, nuevoEstado)
            .map(ResponseEntity::ok);
    }
}

