package co.edu.veterinaria.servicios.domain.factory;

import co.edu.veterinaria.servicios.domain.factory.impl.ConsultaFactory;
import co.edu.veterinaria.servicios.domain.model.Servicio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ConsultaFactoryTest {
    
    private ConsultaFactory factory;
    
    @BeforeEach
    void setUp() {
        factory = new ConsultaFactory();
    }
    
    @Test
    void testCrearServicioConsulta() {
        Servicio servicio = Servicio.builder()
            .nombre("Consulta General")
            .descripcion("Consulta veterinaria general")
            .precioBase(new BigDecimal("50000"))
            .duracionMin(30)
            .tipoServicio("CONSULTA")
            .activo(true)
            .fechaCreacion(LocalDateTime.now())
            .build();
        
        StepVerifier.create(factory.crear(servicio))
            .expectNextMatches(s -> 
                s.getTipoServicio().equals("CONSULTA") &&
                s.getPrecioBase().equals(new BigDecimal("50000"))
            )
            .verifyComplete();
    }
    
    @Test
    void testAplicaPara() {
        assertTrue(factory.aplicaPara("CONSULTA"));
        assertFalse(factory.aplicaPara("CIRUGIA"));
    }
}

