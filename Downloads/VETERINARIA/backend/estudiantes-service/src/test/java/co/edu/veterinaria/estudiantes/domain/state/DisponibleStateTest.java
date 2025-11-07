package co.edu.veterinaria.estudiantes.domain.state;

import co.edu.veterinaria.estudiantes.domain.model.EstudiantePractica;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class DisponibleStateTest {
    
    private DisponibleState estado;
    private EstudiantePractica estudiante;
    
    @BeforeEach
    void setUp() {
        estado = new DisponibleState();
        estudiante = EstudiantePractica.builder()
            .id(1L)
            .codigo("EST001")
            .estado("DISPONIBLE")
            .activo(true)
            .build();
    }
    
    @Test
    void testPuedeSerAsignado() {
        StepVerifier.create(estado.puedeSerAsignado(estudiante))
            .expectNext(true)
            .verifyComplete();
    }
    
    @Test
    void testCambiarEstadoAOcupado() {
        StepVerifier.create(estado.cambiarEstado(estudiante, "OCUPADO"))
            .expectNextMatches(e -> e.getEstado().equals("OCUPADO"))
            .verifyComplete();
    }
    
    @Test
    void testGetNombre() {
        assertEquals("DISPONIBLE", estado.getNombre());
    }
}

