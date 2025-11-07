package co.edu.veterinaria.historia.domain.composite;

import co.edu.veterinaria.historia.domain.composite.ConsultaLeaf;
import co.edu.veterinaria.historia.domain.composite.HistoriaClinicaComposite;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class HistoriaClinicaCompositeTest {
    
    private HistoriaClinicaComposite composite;
    private ConsultaLeaf consulta1;
    private ConsultaLeaf consulta2;
    
    @BeforeEach
    void setUp() {
        composite = new HistoriaClinicaComposite();
        consulta1 = new ConsultaLeaf(1L, "Consulta general", LocalDateTime.now());
        consulta2 = new ConsultaLeaf(2L, "Consulta de seguimiento", LocalDateTime.now());
    }
    
    @Test
    void testAgregarComponente() {
        composite.agregar(consulta1);
        composite.agregar(consulta2);
        
        assertEquals(2, composite.obtenerComponentes().size());
    }
    
    @Test
    void testEliminarComponente() {
        composite.agregar(consulta1);
        composite.agregar(consulta2);
        composite.eliminar(consulta1);
        
        assertEquals(1, composite.obtenerComponentes().size());
    }
    
    @Test
    void testObtenerInformacion() {
        composite.agregar(consulta1);
        composite.agregar(consulta2);
        
        String info = composite.obtenerInformacion();
        assertNotNull(info);
        assertTrue(info.contains("Consulta general"));
    }
}

