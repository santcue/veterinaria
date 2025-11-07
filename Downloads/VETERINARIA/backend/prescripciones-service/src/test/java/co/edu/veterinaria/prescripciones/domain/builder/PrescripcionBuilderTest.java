package co.edu.veterinaria.prescripciones.domain.builder;

import co.edu.veterinaria.prescripciones.domain.model.Prescripcion;
import co.edu.veterinaria.prescripciones.domain.model.PrescripcionDetalle;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PrescripcionBuilderTest {
    
    @Test
    void testConstruirPrescripcionValida() {
        PrescripcionDetalle detalle = PrescripcionDetalle.builder()
            .idMedicamento(1L)
            .dosis("10mg")
            .frecuencia("Cada 8 horas")
            .duracion("7 días")
            .build();
        
        List<PrescripcionDetalle> detalles = new ArrayList<>();
        detalles.add(detalle);
        
        PrescripcionBuilder builder = new PrescripcionBuilder()
            .conIdHistoria(1L)
            .conIndicacionesGenerales("Tomar con alimentos")
            .conDetalle(detalle);
        
        StepVerifier.create(builder.construir())
            .expectNextMatches(prescripcion -> 
                prescripcion.getIdHistoria().equals(1L) &&
                prescripcion.getDetalles().size() == 1
            )
            .verifyComplete();
    }
    
    @Test
    void testConstruirSinIdHistoria() {
        PrescripcionDetalle detalle = PrescripcionDetalle.builder()
            .idMedicamento(1L)
            .dosis("10mg")
            .frecuencia("Cada 8 horas")
            .duracion("7 días")
            .build();
        
        PrescripcionBuilder builder = new PrescripcionBuilder()
            .conIndicacionesGenerales("Tomar con alimentos")
            .conDetalle(detalle);
        
        StepVerifier.create(builder.construir())
            .expectError(IllegalArgumentException.class)
            .verify();
    }
    
    @Test
    void testConstruirSinDetalles() {
        PrescripcionBuilder builder = new PrescripcionBuilder()
            .conIdHistoria(1L)
            .conIndicacionesGenerales("Tomar con alimentos");
        
        StepVerifier.create(builder.construir())
            .expectError(IllegalArgumentException.class)
            .verify();
    }
}

