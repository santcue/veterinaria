package co.edu.veterinaria.citas.domain.model;

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
@Table("cita")
public class Cita {
    @Id
    private Long id;
    
    @Column("id_mascota")
    private Long idMascota;
    
    @Column("id_servicio")
    private Long idServicio;
    
    @Column("id_estado")
    private Long idEstado;
    
    @Column("id_veterinario")
    private Long idVeterinario;
    
    @Column("id_creada_por")
    private Long idCreadaPor;
    
    @Column("fecha_hora")
    private LocalDateTime fechaHora;
    
    private String motivo;
    private String prioridad;
    private String observaciones;
    
    @Column("fecha_creacion")
    private LocalDateTime fechaCreacion;
}

