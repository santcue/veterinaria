package co.edu.veterinaria.prescripciones.domain.builder;

import co.edu.veterinaria.prescripciones.domain.model.Prescripcion;
import co.edu.veterinaria.prescripciones.domain.model.PrescripcionDetalle;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Builder Pattern: Construye prescripciones paso a paso
 */
public class PrescripcionBuilder {
    
    private Long idHistoria;
    private String indicacionesGenerales;
    private List<PrescripcionDetalle> detalles = new ArrayList<>();
    
    public PrescripcionBuilder conHistoria(Long idHistoria) {
        this.idHistoria = idHistoria;
        return this;
    }
    
    public PrescripcionBuilder conIndicaciones(String indicaciones) {
        this.indicacionesGenerales = indicaciones;
        return this;
    }
    
    public PrescripcionBuilder agregarMedicamento(Long idMedicamento, String dosis, String frecuencia, String duracion) {
        PrescripcionDetalle detalle = PrescripcionDetalle.builder()
            .idMedicamento(idMedicamento)
            .dosis(dosis)
            .frecuencia(frecuencia)
            .duracion(duracion)
            .build();
        detalles.add(detalle);
        return this;
    }
    
    public PrescripcionBuilder agregarMedicamento(Long idMedicamento, String dosis, String frecuencia, String duracion, String observaciones) {
        PrescripcionDetalle detalle = PrescripcionDetalle.builder()
            .idMedicamento(idMedicamento)
            .dosis(dosis)
            .frecuencia(frecuencia)
            .duracion(duracion)
            .observaciones(observaciones)
            .build();
        detalles.add(detalle);
        return this;
    }
    
    public Mono<Prescripcion> construir() {
        // Validar antes de construir
        if (idHistoria == null) {
            return Mono.error(new IllegalArgumentException("ID de historia es obligatorio"));
        }
        
        if (detalles.isEmpty()) {
            return Mono.error(new IllegalArgumentException("La prescripción debe tener al menos un medicamento"));
        }
        
        // Validar cada detalle
        for (PrescripcionDetalle detalle : detalles) {
            if (detalle.getDosis() == null || detalle.getDosis().trim().isEmpty()) {
                return Mono.error(new IllegalArgumentException("La dosis es obligatoria para todos los medicamentos"));
            }
            if (detalle.getFrecuencia() == null || detalle.getFrecuencia().trim().isEmpty()) {
                return Mono.error(new IllegalArgumentException("La frecuencia es obligatoria para todos los medicamentos"));
            }
            if (detalle.getDuracion() == null || detalle.getDuracion().trim().isEmpty()) {
                return Mono.error(new IllegalArgumentException("La duración es obligatoria para todos los medicamentos"));
            }
        }
        
        Prescripcion prescripcion = Prescripcion.builder()
            .idHistoria(idHistoria)
            .indicacionesGenerales(indicacionesGenerales)
            .fechaEmision(LocalDateTime.now())
            .detalles(detalles)
            .build();
        
        return Mono.just(prescripcion);
    }
}

