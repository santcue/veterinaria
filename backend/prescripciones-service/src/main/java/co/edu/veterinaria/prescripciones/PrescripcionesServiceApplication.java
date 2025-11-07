package co.edu.veterinaria.prescripciones;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@SpringBootApplication
@EnableR2dbcRepositories
public class PrescripcionesServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(PrescripcionesServiceApplication.class, args);
    }
}

