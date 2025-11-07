package co.edu.veterinaria.estudiantes.domain.state;

import co.edu.veterinaria.estudiantes.domain.model.EstudiantePractica;
import reactor.core.publisher.Mono;

/**
 * State Pattern: Define el comportamiento según el estado del estudiante
 */
public interface EstadoEstudiante {
    
    /**
     * Obtiene el nombre del estado
     */
    String getNombre();
    
    /**
     * Verifica si el estudiante puede ser asignado a una cita
     */
    Mono<Boolean> puedeSerAsignado(EstudiantePractica estudiante);
    
    /**
     * Cambia el estado del estudiante
     */
    Mono<EstudiantePractica> cambiarEstado(EstudiantePractica estudiante, String nuevoEstado);
    
    /**
     * Obtiene la descripción del estado
     */
    String getDescripcion();
}

