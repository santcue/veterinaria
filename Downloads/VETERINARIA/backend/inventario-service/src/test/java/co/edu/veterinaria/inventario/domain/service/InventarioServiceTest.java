package co.edu.veterinaria.inventario.domain.service;

import co.edu.veterinaria.inventario.domain.model.Insumo;
import co.edu.veterinaria.inventario.domain.repository.InsumoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InventarioServiceTest {
    
    @Mock
    private InsumoRepository repository;
    
    @InjectMocks
    private InventarioService service;
    
    private Insumo insumo;
    
    @BeforeEach
    void setUp() {
        insumo = Insumo.builder()
            .id(1L)
            .nombre("Vacuna Antirrábica")
            .descripcion("Vacuna para perros")
            .categoria("Vacunas")
            .stock(new BigDecimal("50"))
            .stockMinimo(new BigDecimal("10"))
            .precioUnitario(new BigDecimal("25000"))
            .fechaVencimiento(LocalDate.now().plusMonths(6))
            .unidadMedida("Dosis")
            .activo(true)
            .fechaRegistro(LocalDateTime.now())
            .build();
    }
    
    @Test
    void testCrearInsumo() {
        when(repository.save(any(Insumo.class)))
            .thenReturn(Mono.just(insumo));
        
        StepVerifier.create(service.crear(insumo))
            .expectNextMatches(i -> i.getId().equals(1L))
            .verifyComplete();
    }
    
    @Test
    void testDescontarStock() {
        when(repository.findByIdAndActivoTrue(1L))
            .thenReturn(Mono.just(insumo));
        when(repository.save(any(Insumo.class)))
            .thenReturn(Mono.just(insumo));
        
        StepVerifier.create(service.descontarStock(1L, new BigDecimal("5")))
            .expectNextMatches(i -> i.getStock().equals(new BigDecimal("45")))
            .verifyComplete();
    }
    
    @Test
    void testDescontarStockInsuficiente() {
        when(repository.findByIdAndActivoTrue(1L))
            .thenReturn(Mono.just(insumo));
        
        StepVerifier.create(service.descontarStock(1L, new BigDecimal("100")))
            .expectError(RuntimeException.class)
            .verify();
    }
    
    @Test
    void testObtenerAlertasStock() {
        Insumo insumoBajoStock = Insumo.builder()
            .id(2L)
            .stock(new BigDecimal("5"))
            .stockMinimo(new BigDecimal("10"))
            .activo(true)
            .build();
        
        when(repository.findByActivoTrue())
            .thenReturn(Flux.just(insumo, insumoBajoStock));
        
        StepVerifier.create(service.obtenerAlertasStock())
            .expectNext(insumoBajoStock)
            .verifyComplete();
    }
}

