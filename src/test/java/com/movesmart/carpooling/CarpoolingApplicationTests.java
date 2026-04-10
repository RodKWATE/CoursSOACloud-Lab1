package com.movesmart.carpooling;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Test de démarrage du contexte Spring.
 *
 * Ce test vérifie que l'application démarre correctement :
 * tous les beans sont instanciés, les configurations sont valides,
 * et la base de données est accessible.
 *
 * C'est le test minimal à faire passer avant tout développement.
 *
 * TODO: improve validation – ajouter des tests unitaires pour chaque Service
 *       (DriverServiceTest, PassengerServiceTest, TripServiceTest)
 * TODO: add error handling – ajouter des tests d'intégration pour les Controllers
 *       avec MockMvc pour simuler des requêtes HTTP
 */
@SpringBootTest
class CarpoolingApplicationTests {

    @Test
    void contextLoads() {
        // Ce test vérifie simplement que le contexte Spring démarre sans erreur.
        // S'il échoue, vérifier application.properties et les dépendances dans pom.xml.
    }
}
