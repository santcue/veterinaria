package co.edu.veterinaria.historia.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("historia_clinica")
public class HistoriaClinica {
    @Id
    private Long id;
    
    @Column("id_cita")
    private Long idCita;
    
    private String anamnesis;
    
    @Column("examen_fisico")
    private String examenFisico;
    
    @Column("signos_vitales")
    private String signosVitales; // JSON
    
    private String diagnostico;
    private String plan;
    private String recomendaciones;
    
    @Column("fecha_cierre")
    private LocalDateTime fechaCierre;
}

