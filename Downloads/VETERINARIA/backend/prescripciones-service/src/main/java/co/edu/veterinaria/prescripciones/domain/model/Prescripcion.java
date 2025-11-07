package co.edu.veterinaria.prescripciones.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("prescripcion")
public class Prescripcion {
    @Id
    private Long id;
    
    @Column("id_historia")
    private Long idHistoria;
    
    @Column("fecha_emision")
    private LocalDateTime fechaEmision;
    
    @Column("indicaciones_generales")
    private String indicacionesGenerales;
    
    @Builder.Default
    private List<PrescripcionDetalle> detalles = new ArrayList<>();
}

