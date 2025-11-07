package co.edu.veterinaria.mascotas.domain.exception;

/**
 * Excepción específica para cuando no se encuentra una mascota
 */
public class MascotaNotFoundException extends RuntimeException {
    
    public MascotaNotFoundException(String message) {
        super(message);
    }
    
    public MascotaNotFoundException(Long id) {
        super("Mascota no encontrada con ID: " + id);
    }
}

