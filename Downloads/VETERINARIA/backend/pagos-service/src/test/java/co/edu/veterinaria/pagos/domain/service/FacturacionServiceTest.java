package co.edu.veterinaria.pagos.domain.service;

import co.edu.veterinaria.pagos.domain.model.Factura;
import co.edu.veterinaria.pagos.domain.model.FacturaItem;
import co.edu.veterinaria.pagos.domain.model.Pago;
import co.edu.veterinaria.pagos.domain.repository.FacturaRepository;
import co.edu.veterinaria.pagos.domain.repository.PagoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FacturacionServiceTest {
    
    @Mock
    private FacturaRepository facturaRepository;
    
    @Mock
    private PagoRepository pagoRepository;
    
    @InjectMocks
    private FacturacionService service;
    
    private Factura factura;
    private Pago pago;
    
    @BeforeEach
    void setUp() {
        FacturaItem item = FacturaItem.builder()
            .descripcion("Consulta veterinaria")
            .cantidad(1)
            .precioUnitario(new BigDecimal("50000"))
            .build();
        
        List<FacturaItem> items = new ArrayList<>();
        items.add(item);
        
        factura = Factura.builder()
            .id(1L)
            .idCita(1L)
            .idPropietario(1L)
            .estado("PENDIENTE")
            .items(items)
            .build();
        
        pago = Pago.builder()
            .id(1L)
            .idFactura(1L)
            .monto(new BigDecimal("50000"))
            .medioPago("EFECTIVO")
            .build();
    }
    
    @Test
    void testGenerarFactura() {
        when(facturaRepository.save(any(Factura.class)))
            .thenReturn(Mono.just(factura));
        
        StepVerifier.create(service.generarFactura(factura))
            .expectNextMatches(f -> 
                f.getNumeroFactura() != null &&
                f.getTotal().equals(new BigDecimal("50000"))
            )
            .verifyComplete();
    }
    
    @Test
    void testRegistrarPago() {
        when(facturaRepository.findById(1L))
            .thenReturn(Mono.just(factura));
        when(facturaRepository.save(any(Factura.class)))
            .thenReturn(Mono.just(factura));
        when(pagoRepository.save(any(Pago.class)))
            .thenReturn(Mono.just(pago));
        
        StepVerifier.create(service.registrarPago(pago))
            .expectNextMatches(p -> p.getEstado().equals("APROBADO"))
            .verifyComplete();
    }
    
    @Test
    void testRegistrarPagoFacturaYaPagada() {
        factura.setEstado("PAGADA");
        when(facturaRepository.findById(1L))
            .thenReturn(Mono.just(factura));
        
        StepVerifier.create(service.registrarPago(pago))
            .expectError(RuntimeException.class)
            .verify();
    }
}

