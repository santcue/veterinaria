package co.edu.veterinaria.historia.infrastructure.web.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HistoriaClinicaDTO {
    private Long id;
    
    @NotNull(message = "El ID de la cita es obligatorio")
    private Long idCita;
    
    private String anamnesis;
    private String examenFisico;
    private String signosVitales;
    private String diagnostico;
    private String plan;
    private String recomendaciones;
}

