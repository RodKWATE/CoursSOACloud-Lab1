package com.movesmart.carpooling.controller;

import com.movesmart.carpooling.model.Trip;
import com.movesmart.carpooling.service.TripService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * COUCHE CONTROLLER – Point d'entrée HTTP pour les Trajets.
 *
 * Ce controller illustre un endpoint de consultation (GET) qui est
 * la base de toute API REST : exposer des ressources en lecture.
 *
 * Dans une architecture SOA, ce type de service serait potentiellement
 * consommé par :
 *   - Une application mobile (iOS/Android)
 *   - Un portail web (React, Angular)
 *   - Un autre service back-end (ex: service de notification)
 *
 * La même API REST peut donc servir de multiples consommateurs —
 * c'est l'un des avantages clés de l'architecture SOA / REST.
 */
@RestController
@RequestMapping("/api/trips")
@Tag(name = "Trips", description = "Consultation des trajets disponibles")
public class TripController {

    private final TripService tripService;

    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    /**
     * GET /api/trips
     * Retourne les trajets ayant au moins une place disponible.
     *
     * C'est l'endpoint principal du TP : les passagers consultent
     * les trajets disponibles avant de réserver.
     */
    @GetMapping
    @Operation(
        summary = "Lister les trajets disponibles",
        description = "Retourne tous les trajets avec au moins une place disponible"
    )
    public ResponseEntity<List<Trip>> getAvailableTrips() {
        // TODO: improve validation – ajouter des paramètres de filtre via @RequestParam
        //       ex: GET /api/trips?departure=Paris&destination=Lyon
        // TODO: refactor to better separation of concerns – paginer les résultats
        //       avec Pageable pour éviter de retourner des milliers de trajets
        return ResponseEntity.ok(tripService.findAvailableTrips());
    }

    /**
     * GET /api/trips/all
     * Retourne tous les trajets (même complets) — utile pour l'administration.
     */
    @GetMapping("/all")
    @Operation(summary = "Lister tous les trajets", description = "Retourne tous les trajets sans filtre")
    public ResponseEntity<List<Trip>> getAllTrips() {
        return ResponseEntity.ok(tripService.findAll());
    }

    // TODO: add error handling – gérer le cas liste vide (retourner 204 No Content ?)
    // TODO: refactor to better separation of concerns – ajouter POST /api/trips/{id}/book
    //       pour qu'un passager réserve une place
}
