package co.edu.veterinaria.prescripciones.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("prescripcion_detalle")
public class PrescripcionDetalle {
    @Id
    private Long id;
    
    @Column("id_prescripcion")
    private Long idPrescripcion;
    
    @Column("id_medicamento")
    private Long idMedicamento;
    
    private String dosis;
    private String frecuencia;
    private String duracion;
    private String observaciones;
}

