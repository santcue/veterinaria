package co.edu.veterinaria.auth.domain.service;

import co.edu.veterinaria.auth.domain.model.Usuario;
import co.edu.veterinaria.auth.domain.repository.UsuarioRepository;
import co.edu.veterinaria.auth.infrastructure.security.JwtTokenProvider;
import co.edu.veterinaria.auth.infrastructure.security.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final DatabaseClient databaseClient;
    
    public Mono<AuthResponse> login(String email, String password) {
        log.debug("Intento de login para: {}", email);
        
        return usuarioRepository.findByEmailAndActivoTrue(email)
            .switchIfEmpty(Mono.error(new RuntimeException("Credenciales inválidas")))
            .flatMap(usuario -> {
                if (!passwordEncoder.matches(password, usuario.getHashPassword())) {
                    return Mono.error(new RuntimeException("Credenciales inválidas"));
                }
                
                return obtenerRoles(usuario.getId())
                    .map(roles -> {
                        String token = jwtTokenProvider.generateToken(usuario.getEmail(), roles);
                        return AuthResponse.builder()
                            .token(token)
                            .email(usuario.getEmail())
                            .nombre(usuario.getNombre())
                            .roles(roles)
                            .build();
                    });
            })
            .doOnSuccess(response -> log.info("Login exitoso para: {}", email))
            .doOnError(error -> log.warn("Error en login para {}: {}", email, error.getMessage()));
    }
    
    public Mono<Boolean> validateToken(String token) {
        return Mono.just(jwtTokenProvider.validateToken(token));
    }
    
    private Mono<List<String>> obtenerRoles(Long usuarioId) {
        return databaseClient.sql(
                "SELECT r.nombre FROM rol r " +
                "INNER JOIN usuario_rol ur ON r.id = ur.id_rol " +
                "WHERE ur.id_usuario = :usuarioId AND r.activo = true"
            )
            .bind("usuarioId", usuarioId)
            .map((row, metadata) -> row.get("nombre", String.class))
            .all()
            .collectList()
            .defaultIfEmpty(List.of("PROPIETARIO"));
    }
    
    @lombok.Data
    @lombok.Builder
    public static class AuthResponse {
        private String token;
        private String email;
        private String nombre;
        private List<String> roles;
    }
}

