package co.edu.veterinaria.inventario.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("insumo")
public class Insumo {
    @Id
    private Long id;
    private String nombre;
    private String descripcion;
    private String categoria;
    private BigDecimal stock;
    
    @Column("stock_minimo")
    private BigDecimal stockMinimo;
    
    @Column("precio_unitario")
    private BigDecimal precioUnitario;
    
    @Column("fecha_vencimiento")
    private LocalDate fechaVencimiento;
    
    @Column("unidad_medida")
    private String unidadMedida;
    
    private Boolean activo;
    
    @Column("fecha_registro")
    private LocalDateTime fechaRegistro;
}

