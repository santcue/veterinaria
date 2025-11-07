package co.edu.veterinaria.pagos.infrastructure.web.controller;

import co.edu.veterinaria.pagos.domain.model.Factura;
import co.edu.veterinaria.pagos.domain.model.Pago;
import co.edu.veterinaria.pagos.domain.service.FacturacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/facturacion")
@RequiredArgsConstructor
public class FacturacionController {
    
    private final FacturacionService service;
    
    @PostMapping("/facturas")
    public Mono<ResponseEntity<Factura>> generarFactura(@RequestBody Factura factura) {
        return service.generarFactura(factura)
            .map(ResponseEntity::ok);
    }
    
    @GetMapping("/facturas/{id}")
    public Mono<ResponseEntity<Factura>> obtenerFactura(@PathVariable Long id) {
        return service.obtenerPorId(id)
            .map(ResponseEntity::ok);
    }
    
    @GetMapping("/facturas/propietario/{idPropietario}")
    public Mono<ResponseEntity<Flux<Factura>>> obtenerFacturasPorPropietario(@PathVariable Long idPropietario) {
        return Mono.just(ResponseEntity.ok(service.obtenerPorPropietario(idPropietario)));
    }
    
    @PostMapping("/pagos")
    public Mono<ResponseEntity<Pago>> registrarPago(@RequestBody Pago pago) {
        return service.registrarPago(pago)
            .map(ResponseEntity::ok);
    }
}

