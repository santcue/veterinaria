package co.edu.veterinaria.mascotas.domain.exception;

/**
 * Excepción específica para cuando no se encuentra una estrategia de validación
 */
public class EstrategiaValidacionNotFoundException extends RuntimeException {
    
    public EstrategiaValidacionNotFoundException(String message) {
        super(message);
    }
    
    public EstrategiaValidacionNotFoundException(Long idEspecie) {
        super("No se encontró estrategia de validación para la especie: " + idEspecie);
    }
}

