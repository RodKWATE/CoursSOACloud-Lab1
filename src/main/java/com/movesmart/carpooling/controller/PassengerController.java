package com.movesmart.carpooling.controller;

import com.movesmart.carpooling.model.Passenger;
import com.movesmart.carpooling.service.PassengerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * COUCHE CONTROLLER – Point d'entrée HTTP pour les Passagers.
 *
 * Architecture REST – Rappel des conventions :
 *   POST   /api/passengers        → créer un passager        (201 Created)
 *   GET    /api/passengers        → lister tous              (200 OK)
 *   GET    /api/passengers/{id}   → récupérer un passager    (200 OK / 404 Not Found)
 *   PUT    /api/passengers/{id}   → modifier un passager     (200 OK)
 *   DELETE /api/passengers/{id}   → supprimer un passager    (204 No Content)
 *
 * Pour l'instant seuls POST et GET sont implémentés.
 * Les étudiants complèteront les autres opérations.
 */
@RestController
@RequestMapping("/api/passengers")
@Tag(name = "Passengers", description = "Gestion des passagers")
public class PassengerController {

    private final PassengerService passengerService;

    public PassengerController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    /**
     * POST /api/passengers
     * Enregistre un nouveau passager.
     *
     * Exemple de corps de requête :
     * {
     *   "name": "Bob Dupont",
     *   "email": "bob@example.com",
     *   "phone": "0611223344"
     * }
     */
    @PostMapping
    @Operation(summary = "Ajouter un passager", description = "Enregistre un nouveau passager dans le système")
    public ResponseEntity<Passenger> createPassenger(@RequestBody Passenger passenger) {
        // TODO: improve validation – valider les champs avec @Valid avant de sauvegarder
        // TODO: add error handling – retourner 409 si l'e-mail est déjà enregistré
        Passenger saved = passengerService.save(passenger);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    /**
     * GET /api/passengers
     * Retourne tous les passagers enregistrés.
     */
    @GetMapping
    @Operation(summary = "Lister les passagers", description = "Retourne tous les passagers enregistrés")
    public ResponseEntity<List<Passenger>> getAllPassengers() {
        return ResponseEntity.ok(passengerService.findAll());
    }

    // TODO: refactor to better separation of concerns – ajouter les endpoints manquants
    //       GET /{id}, PUT /{id}, DELETE /{id}
}
