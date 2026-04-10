package com.movesmart.carpooling;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Point d'entrée de l'application Spring Boot.
 *
 * @SpringBootApplication est un raccourci qui active :
 *   - @Configuration        : cette classe peut définir des beans Spring
 *   - @EnableAutoConfiguration : Spring Boot configure automatiquement les composants
 *   - @ComponentScan        : Spring scanne le package courant pour trouver les composants
 *
 * TP1 – Architecture évolutive :
 *   Phase 1 : Application monolithique (ce que vous voyez ici)
 *   Phase 2 : Décomposition en services (SOA)
 *   Phase 3 : Exposition REST pure avec contrats OpenAPI
 */
@SpringBootApplication
public class CarpoolingApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarpoolingApplication.class, args);
    }
}
