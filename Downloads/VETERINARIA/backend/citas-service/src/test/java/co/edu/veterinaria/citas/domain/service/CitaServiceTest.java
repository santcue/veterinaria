package co.edu.veterinaria.citas.domain.service;

import co.edu.veterinaria.citas.domain.model.Cita;
import co.edu.veterinaria.citas.domain.repository.CitaRepository;
import co.edu.veterinaria.citas.domain.strategy.AsignacionHorarioStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CitaServiceTest {
    
    @Mock
    private CitaRepository repository;
    
    @Mock
    private List<AsignacionHorarioStrategy> strategies;
    
    @Mock
    private AsignacionHorarioStrategy strategy;
    
    @InjectMocks
    private CitaService service;
    
    private Cita cita;
    
    @BeforeEach
    void setUp() {
        cita = Cita.builder()
            .id(1L)
            .idMascota(1L)
            .idServicio(1L)
            .idVeterinario(1L)
            .fechaHora(LocalDateTime.now().plusDays(1))
            .motivo("Consulta general")
            .prioridad("NORMAL")
            .build();
    }
    
    @Test
    void testCrearCita() {
        when(strategies.stream()).thenReturn(Arrays.asList(strategy).stream());
        when(strategy.aplicaPara(anyString())).thenReturn(true);
        when(strategy.asignarHorario(any(Cita.class))).thenReturn(Mono.just(cita));
        when(repository.save(any(Cita.class))).thenReturn(Mono.just(cita));
        
        StepVerifier.create(service.crear(cita, "PRIMER_DISPONIBLE"))
            .expectNextMatches(c -> c.getId().equals(1L))
            .verifyComplete();
    }
    
    @Test
    void testObtenerPorId() {
        when(repository.findById(1L))
            .thenReturn(Mono.just(cita));
        
        StepVerifier.create(service.obtenerPorId(1L))
            .expectNext(cita)
            .verifyComplete();
    }
}

