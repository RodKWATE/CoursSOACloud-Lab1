package com.movesmart.carpooling.repository;

import com.movesmart.carpooling.model.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * COUCHE REPOSITORY – Accès aux données pour les Trajets.
 *
 * Exemple de requête dérivée par convention de nommage :
 *   findByAvailableSeatsGreaterThan(0)
 *   → SELECT * FROM trips WHERE available_seats > 0
 *
 * Spring Data interprète :
 *   "findBy"             → SELECT WHERE
 *   "AvailableSeats"     → colonne available_seats
 *   "GreaterThan"        → opérateur >
 */
@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {

    /**
     * Retourne uniquement les trajets ayant au moins une place disponible.
     *
     * @param seats seuil minimum de places (typiquement 0)
     * @return liste des trajets disponibles
     */
    List<Trip> findByAvailableSeatsGreaterThan(int seats);

    // TODO: improve validation – filtrer aussi par departureTime > now()
    // TODO: refactor to better separation of concerns – envisager une query @Query JPQL
    //       pour des filtres plus complexes (ville de départ, date, etc.)
}
