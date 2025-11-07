package co.edu.veterinaria.servicios.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("servicio")
public class Servicio {
    @Id
    private Long id;
    private String nombre;
    private String descripcion;
    
    @Column("precio_base")
    private BigDecimal precioBase;
    
    @Column("duracion_min")
    private Integer duracionMin;
    
    @Column("tipo_servicio")
    private String tipoServicio; // CONSULTA, CIRUGIA, VACUNACION, etc.
    
    private Boolean activo;
    
    @Column("fecha_creacion")
    private LocalDateTime fechaCreacion;
}

