package co.edu.veterinaria.auth.domain.model;

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
@Table("usuario")
public class Usuario {
    @Id
    private Long id;
    private String nombre;
    private String email;
    private String telefono;
    
    @Column("hash_password")
    private String hashPassword;
    
    private Boolean activo;
    
    @Column("fecha_creacion")
    private LocalDateTime fechaCreacion;
}

