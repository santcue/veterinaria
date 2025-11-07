package co.edu.veterinaria.estudiantes.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("estudiante_practica")
public class EstudiantePractica {
    @Id
    private Long id;
    
    @Column("id_usuario")
    private Long idUsuario;
    
    private String codigo;
    private Integer semestre;
    private String programa;
    
    @Column("tutor_academico")
    private String tutorAcademico;
    
    @Column("fecha_inicio")
    private LocalDate fechaInicio;
    
    @Column("fecha_fin")
    private LocalDate fechaFin;
    
    private Boolean activo;
    
    @Column("fecha_registro")
    private LocalDateTime fechaRegistro;
    
    private String estado; // DISPONIBLE, OCUPADO, EN_PRACTICA, AUSENTE
}

