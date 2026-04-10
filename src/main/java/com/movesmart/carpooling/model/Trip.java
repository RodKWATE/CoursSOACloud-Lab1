package com.movesmart.carpooling.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entité JPA représentant un Trajet (Trip).
 *
 * Relation @ManyToOne avec Driver :
 *   - Plusieurs trajets peuvent être proposés par le même conducteur.
 *   - @JoinColumn définit la colonne de clé étrangère dans la table "trips".
 *
 * LocalDateTime est utilisé pour la date/heure de départ.
 * Spring Boot et JPA gèrent automatiquement la conversion vers SQL TIMESTAMP.
 */
@Entity
@Table(name = "trips")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Ville ou adresse de départ */
    private String departure;

    /** Ville ou adresse de destination */
    private String destination;

    /** Date et heure de départ prévues */
    private LocalDateTime departureTime;

    /** Nombre de places encore disponibles sur ce trajet */
    private int availableSeats;

    /**
     * Conducteur qui propose ce trajet.
     * FETCH.LAZY : le conducteur n'est chargé que si on y accède explicitement
     *              (bonne pratique pour éviter les requêtes SQL inutiles).
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id")
    private Driver driver;

    // TODO: improve validation – vérifier que departureTime est dans le futur
    // TODO: add error handling – gérer le cas où availableSeats devient négatif
    // TODO: refactor to better separation of concerns – extraire la logique
    //       de réservation dans un BookingService dédié
}
