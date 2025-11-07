package co.edu.veterinaria.notificaciones.domain.service;

import co.edu.veterinaria.notificaciones.domain.model.Notificacion;
import co.edu.veterinaria.notificaciones.domain.strategy.NotificacionStrategy;
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
class NotificacionServiceTest {
    
    @Mock
    private List<NotificacionStrategy> strategies;
    
    @Mock
    private NotificacionStrategy strategy;
    
    @InjectMocks
    private NotificacionService service;
    
    private Notificacion notificacion;
    
    @BeforeEach
    void setUp() {
        notificacion = Notificacion.builder()
            .id(1L)
            .idUsuario(1L)
            .titulo("Nueva cita")
            .mensaje("Tienes una cita programada")
            .tipo("CITA")
            .canal("EMAIL")
            .fechaCreacion(LocalDateTime.now())
            .build();
    }
    
    @Test
    void testEnviarNotificacion() {
        when(strategies.stream()).thenReturn(Arrays.asList(strategy).stream());
        when(strategy.aplicaPara(anyString())).thenReturn(true);
        when(strategy.enviar(any(Notificacion.class))).thenReturn(Mono.just(notificacion));
        
        StepVerifier.create(service.enviar(notificacion))
            .expectNextMatches(n -> n.getId().equals(1L))
            .verifyComplete();
    }
}

