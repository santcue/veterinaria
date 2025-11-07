package co.edu.veterinaria.auth.infrastructure.web.controller;

import co.edu.veterinaria.auth.domain.service.AuthService;
import co.edu.veterinaria.auth.infrastructure.web.dto.LoginRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final AuthService authService;
    
    @PostMapping("/login")
    public Mono<ResponseEntity<AuthService.AuthResponse>> login(@Valid @RequestBody LoginRequest request) {
        log.debug("POST /api/auth/login - Login para: {}", request.getEmail());
        return authService.login(request.getEmail(), request.getPassword())
            .map(ResponseEntity::ok)
            .onErrorResume(error -> {
                log.error("Error en login: {}", error.getMessage());
                return Mono.just(ResponseEntity.status(401).build());
            });
    }
    
    @GetMapping("/validate")
    public Mono<ResponseEntity<Boolean>> validateToken(@RequestHeader(value = "Authorization", required = false) String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return Mono.just(ResponseEntity.status(401).body(false));
        }
        String jwtToken = authHeader.replace("Bearer ", "");
        return authService.validateToken(jwtToken)
            .map(ResponseEntity::ok)
            .defaultIfEmpty(ResponseEntity.status(401).body(false));
    }
}

