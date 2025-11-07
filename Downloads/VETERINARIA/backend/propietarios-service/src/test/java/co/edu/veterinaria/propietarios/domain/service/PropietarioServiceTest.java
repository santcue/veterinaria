package co.edu.veterinaria.propietarios.domain.service;

import co.edu.veterinaria.propietarios.domain.factory.PropietarioFactory;
import co.edu.veterinaria.propietarios.domain.model.Propietario;
import co.edu.veterinaria.propietarios.domain.observer.PropietarioObserver;
import co.edu.veterinaria.propietarios.domain.repository.PropietarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PropietarioServiceTest {
    
    @Mock
    private PropietarioRepository repository;
    
    @Mock
    private List<PropietarioFactory> factories;
    
    @Mock
    private List<PropietarioObserver> observers;
    
    @Mock
    private PropietarioFactory factory;
    
    @Mock
    private PropietarioObserver observer;
    
    @InjectMocks
    private PropietarioService service;
    
    private Propietario propietario;
    
    @BeforeEach
    void setUp() {
        propietario = Propietario.builder()
            .id(1L)
            .documento("12345678")
            .nombres("Juan")
            .apellidos("Pérez")
            .email("juan@example.com")
            .telefono("3001234567")
            .activo(true)
            .fechaRegistro(LocalDateTime.now())
            .build();
    }
    
    @Test
    void testCrearPropietario() {
        when(factories.stream()).thenReturn(Arrays.asList(factory).stream());
        when(factory.puedeCrear(anyString())).thenReturn(true);
        when(factory.crear(any(Propietario.class))).thenReturn(Mono.just(propietario));
        when(repository.save(any(Propietario.class))).thenReturn(Mono.just(propietario));
        when(observers.stream()).thenReturn(Arrays.asList(observer).stream());
        when(observer.onPropietarioCreado(any(Propietario.class))).thenReturn(Mono.empty());
        
        StepVerifier.create(service.crear(propietario, "WEB"))
            .expectNextMatches(p -> p.getId().equals(1L))
            .verifyComplete();
    }
    
    @Test
    void testObtenerTodos() {
        when(repository.findByActivoTrue())
            .thenReturn(Flux.just(propietario));
        
        StepVerifier.create(service.obtenerTodos())
            .expectNext(propietario)
            .verifyComplete();
    }
    
    @Test
    void testObtenerPorId() {
        when(repository.findByIdAndActivoTrue(1L))
            .thenReturn(Mono.just(propietario));
        
        StepVerifier.create(service.obtenerPorId(1L))
            .expectNext(propietario)
            .verifyComplete();
    }
}

