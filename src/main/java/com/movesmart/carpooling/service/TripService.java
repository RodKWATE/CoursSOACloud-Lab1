package com.movesmart.carpooling.service;

import com.movesmart.carpooling.model.Trip;
import com.movesmart.carpooling.repository.TripRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * COUCHE SERVICE – Logique métier pour les Trajets.
 *
 * Ce service expose les opérations disponibles sur les trajets.
 * Pour l'instant la logique est volontairement simple.
 *
 * Évolution prévue (phases suivantes du TP) :
 *   - Filtres avancés (ville, date, places disponibles)
 *   - Réservation d'une place (décrémente availableSeats)
 *   - Notification du conducteur
 */
@Service
public class TripService {

    private final TripRepository tripRepository;

    public TripService(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }

    /**
     * Retourne tous les trajets disponibles (au moins 1 place libre).
     *
     * @return liste de Trip avec au moins une place disponible
     */
    public List<Trip> findAvailableTrips() {
        // On ne retourne que les trajets ayant au moins 1 place disponible
        return tripRepository.findByAvailableSeatsGreaterThan(0);
    }

    /**
     * Enregistre un nouveau trajet.
     *
     * @param trip le trajet à sauvegarder
     * @return le Trip sauvegardé avec son id
     */
    public Trip save(Trip trip) {
        // TODO: improve validation – vérifier que departureTime est dans le futur
        // TODO: add error handling – vérifier que le driver existe et est actif
        return tripRepository.save(trip);
    }

    /**
     * Retourne tous les trajets sans filtre.
     *
     * @return liste complète de Trip
     */
    public List<Trip> findAll() {
        return tripRepository.findAll();
    }

    // TODO: refactor to better separation of concerns – ajouter bookSeat(tripId, passengerId)
    //       qui décrémente availableSeats et crée une réservation
}
