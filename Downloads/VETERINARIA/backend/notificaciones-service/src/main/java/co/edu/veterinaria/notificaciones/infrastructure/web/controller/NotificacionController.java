package co.edu.veterinaria.notificaciones.infrastructure.web.controller;

import co.edu.veterinaria.notificaciones.domain.model.Notificacion;
import co.edu.veterinaria.notificaciones.domain.service.NotificacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/notificaciones")
@RequiredArgsConstructor
public class NotificacionController {
    
    private final NotificacionService notificacionService;
    
    @PostMapping("/enviar")
    public Mono<ResponseEntity<Boolean>> enviar(@RequestBody Notificacion notificacion) {
        return notificacionService.enviarNotificacion(notificacion)
            .map(ResponseEntity::ok)
            .onErrorResume(e -> Mono.just(ResponseEntity.badRequest().body(false)));
    }
}

