package co.edu.veterinaria.prescripciones.infrastructure.web.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrescripcionDTO {
    private Long id;
    
    @NotNull(message = "El ID de la historia es obligatorio")
    private Long idHistoria;
    
    private String indicacionesGenerales;
    private List<PrescripcionDetalleDTO> detalles;
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PrescripcionDetalleDTO {
        private Long idMedicamento;
        
        @NotNull(message = "La dosis es obligatoria")
        private String dosis;
        
        @NotNull(message = "La frecuencia es obligatoria")
        private String frecuencia;
        
        @NotNull(message = "La duración es obligatoria")
        private String duracion;
        
        private String observaciones;
    }
}

