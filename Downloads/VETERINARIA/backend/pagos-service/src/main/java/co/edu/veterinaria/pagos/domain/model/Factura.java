package co.edu.veterinaria.pagos.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("factura")
public class Factura {
    @Id
    private Long id;
    
    @Column("id_cita")
    private Long idCita;
    
    @Column("id_propietario")
    private Long idPropietario;
    
    @Column("numero_factura")
    private String numeroFactura;
    
    private BigDecimal total;
    private String estado; // PENDIENTE, PAGADA, CANCELADA
    
    @Column("fecha_emision")
    private LocalDateTime fechaEmision;
    
    @Column("fecha_vencimiento")
    private LocalDateTime fechaVencimiento;
    
    @Builder.Default
    private List<FacturaItem> items = new ArrayList<>();
}

