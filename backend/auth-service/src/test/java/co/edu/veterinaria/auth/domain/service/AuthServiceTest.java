package co.edu.veterinaria.auth.domain.service;

import co.edu.veterinaria.auth.domain.model.Usuario;
import co.edu.veterinaria.auth.domain.repository.UsuarioRepository;
import co.edu.veterinaria.auth.infrastructure.security.JwtTokenProvider;
import co.edu.veterinaria.auth.infrastructure.security.PasswordEncoder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {
    
    @Mock
    private UsuarioRepository usuarioRepository;
    
    @Mock
    private PasswordEncoder passwordEncoder;
    
    @Mock
    private JwtTokenProvider jwtTokenProvider;
    
    @Mock
    private DatabaseClient databaseClient;
    
    @Mock
    private DatabaseClient.GenericExecuteSpec executeSpec;
    
    @InjectMocks
    private AuthService authService;
    
    private Usuario usuario;
    
    @BeforeEach
    void setUp() {
        usuario = Usuario.builder()
            .id(1L)
            .nombre("Admin")
            .email("admin@veterinaria.edu.co")
            .hashPassword("$2a$10$encodedPassword")
            .activo(true)
            .fechaCreacion(LocalDateTime.now())
            .build();
    }
    
    @Test
    void testLoginExitoso() {
        when(usuarioRepository.findByEmailAndActivoTrue(anyString()))
            .thenReturn(Mono.just(usuario));
        when(passwordEncoder.matches(anyString(), anyString()))
            .thenReturn(true);
        when(jwtTokenProvider.generateToken(anyString(), any()))
            .thenReturn("mock-jwt-token");
        when(databaseClient.sql(anyString())).thenReturn(executeSpec);
        when(executeSpec.bind(anyString(), any())).thenReturn(executeSpec);
        when(executeSpec.map(any())).thenReturn(executeSpec);
        when(executeSpec.all()).thenReturn(reactor.core.publisher.Flux.just("ADMIN"));
        when(executeSpec.collectList()).thenReturn(Mono.just(List.of("ADMIN")));
        
        StepVerifier.create(authService.login("admin@veterinaria.edu.co", "password"))
            .expectNextMatches(response -> 
                response.getToken() != null && 
                response.getEmail().equals("admin@veterinaria.edu.co")
            )
            .verifyComplete();
    }
    
    @Test
    void testLoginCredencialesInvalidas() {
        when(usuarioRepository.findByEmailAndActivoTrue(anyString()))
            .thenReturn(Mono.empty());
        
        StepVerifier.create(authService.login("invalid@email.com", "password"))
            .expectError(RuntimeException.class)
            .verify();
    }
    
    @Test
    void testValidateToken() {
        when(jwtTokenProvider.validateToken(anyString()))
            .thenReturn(true);
        
        StepVerifier.create(authService.validateToken("valid-token"))
            .expectNext(true)
            .verifyComplete();
    }
}

