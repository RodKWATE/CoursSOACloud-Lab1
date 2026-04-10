package com.movesmart.carpooling.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * COUCHE CONFIG – Configuration de l'API OpenAPI / Swagger.
 *
 * @Configuration : indique à Spring que cette classe contient des définitions de beans.
 * @Bean          : la méthode annotée retourne un objet qui sera géré par Spring.
 *
 * Springdoc OpenAPI génère automatiquement la documentation de l'API en lisant
 * les annotations @RestController, @GetMapping, @PostMapping, etc.
 * Cette classe permet de personnaliser les métadonnées affichées dans Swagger UI.
 *
 * Accès à Swagger UI une fois l'application lancée :
 *   http://localhost:8080/swagger-ui.html
 *
 * La spécification OpenAPI (JSON) est disponible à :
 *   http://localhost:8080/v3/api-docs
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI carpoolingOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("MoveSmart Carpooling API")
                .version("1.0.0")
                .description("""
                    API REST du système de covoiturage interne MoveSmart.

                    TP1 – Évolution architecturale :
                    • Phase 1 : Application monolithique (implémentation actuelle)
                    • Phase 2 : Décomposition SOA (services indépendants)
                    • Phase 3 : API REST avec contrat OpenAPI complet
                    """)
                .contact(new Contact()
                    .name("Équipe Architecture SOA")
                    .email("soa@movesmart.com"))
            );
    }
}
