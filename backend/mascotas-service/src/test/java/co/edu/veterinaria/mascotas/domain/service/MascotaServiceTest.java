package co.edu.veterinaria.mascotas.domain.service;

import co.edu.veterinaria.mascotas.domain.model.Mascota;
import co.edu.veterinaria.mascotas.domain.repository.MascotaRepository;
import co.edu.veterinaria.mascotas.domain.strategy.ValidacionMascotaStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MascotaServiceTest {
    
    @Mock
    private MascotaRepository mascotaRepository;
    
    @Mock
    private ValidacionMascotaStrategy validacionStrategy;
    
    @InjectMocks
    private MascotaService mascotaService;
    
    private Mascota mascota;
    private List<ValidacionMascotaStrategy> strategies;
    
    @BeforeEach
    void setUp() {
        mascota = Mascota.builder()
            .id(1L)
            .idPropietario(1L)
            .idEspecie(1L)
            .nombre("Max")
            .fechaNacimiento(LocalDate.of(2020, 1, 1))
            .sexo("M")
            .pesoKg(new BigDecimal("15.5"))
            .activo(true)
            .build();
        
        strategies = Arrays.asList(validacionStrategy);
    }
    
    @Test
    void testCrearMascota() {
        when(validacionStrategy.aplicaPara(1L)).thenReturn(true);
        when(validacionStrategy.validar(any(Mascota.class))).thenReturn(Mono.just(mascota));
        when(mascotaRepository.save(any(Mascota.class))).thenReturn(Mono.just(mascota));
        
        StepVerifier.create(mascotaService.crear(mascota))
            .expectNextMatches(m -> m.getId().equals(1L))
            .verifyComplete();
    }
}

