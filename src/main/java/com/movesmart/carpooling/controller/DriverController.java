package com.movesmart.carpooling.controller;

import com.movesmart.carpooling.model.Driver;
import com.movesmart.carpooling.service.DriverService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * COUCHE CONTROLLER – Point d'entrée HTTP pour les Conducteurs.
 *
 * Un Controller REST est responsable de :
 *   1. Recevoir les requêtes HTTP (GET, POST, PUT, DELETE)
 *   2. Extraire les données de la requête (body JSON, paramètres URL)
 *   3. Déléguer le traitement au Service
 *   4. Retourner une réponse HTTP avec le bon code de statut
 *
 * Le Controller ne contient AUCUNE logique métier – il fait uniquement le pont
 * entre HTTP et le Service.
 *
 * @RestController = @Controller + @ResponseBody
 *   → chaque méthode retourne directement du JSON (via Jackson)
 *
 * @RequestMapping("/api/drivers") : préfixe de toutes les routes de ce controller
 */
@RestController
@RequestMapping("/api/drivers")
@Tag(name = "Drivers", description = "Gestion des conducteurs")
public class DriverController {

    private final DriverService driverService;

    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    /**
     * POST /api/drivers
     * Crée un nouveau conducteur à partir du JSON reçu dans le corps de la requête.
     *
     * @RequestBody : Spring désérialise automatiquement le JSON en objet Driver
     * ResponseEntity<Driver> : permet de contrôler le code HTTP de retour
     *   → 201 CREATED est le code sémantiquement correct pour une création de ressource
     *
     * Exemple de corps de requête :
     * {
     *   "name": "Alice Martin",
     *   "email": "alice@example.com",
     *   "phone": "0601020304",
     *   "car": "Peugeot 308",
     *   "availableSeats": 3
     * }
     */
    @PostMapping
    @Operation(summary = "Ajouter un conducteur", description = "Crée un nouveau conducteur dans le système")
    public ResponseEntity<Driver> createDriver(@RequestBody Driver driver) {
        // TODO: improve validation – utiliser @Valid + BindingResult pour valider les champs
        //       et retourner un 400 Bad Request si les données sont invalides
        // TODO: add error handling – capturer les exceptions (email existant, champs vides)
        //       et retourner les bons codes HTTP (409 Conflict, 400 Bad Request)
        Driver saved = driverService.save(driver);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    /**
     * GET /api/drivers
     * Retourne la liste de tous les conducteurs.
     */
    @GetMapping
    @Operation(summary = "Lister les conducteurs", description = "Retourne tous les conducteurs enregistrés")
    public ResponseEntity<List<Driver>> getAllDrivers() {
        return ResponseEntity.ok(driverService.findAll());
    }

    // TODO: refactor to better separation of concerns – ajouter GET /api/drivers/{id},
    //       PUT /api/drivers/{id}, DELETE /api/drivers/{id} pour un CRUD complet
}
