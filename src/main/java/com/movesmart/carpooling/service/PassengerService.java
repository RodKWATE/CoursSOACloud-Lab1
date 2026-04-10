package com.movesmart.carpooling.service;

import com.movesmart.carpooling.model.Passenger;
import com.movesmart.carpooling.repository.PassengerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * COUCHE SERVICE – Logique métier pour les Passagers.
 *
 * Même architecture que DriverService.
 * La séparation en services distincts (DriverService, PassengerService, TripService)
 * est un premier pas vers une architecture orientée services (SOA) :
 * chaque service a une responsabilité claire et délimitée.
 *
 * Dans une vraie architecture microservices, chaque service pourrait devenir
 * un processus déployé indépendamment.
 */
@Service
public class PassengerService {

    private final PassengerRepository passengerRepository;

    public PassengerService(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

    /**
     * Enregistre un nouveau passager.
     *
     * @param passenger l'objet Passenger à sauvegarder
     * @return le Passenger sauvegardé avec son id
     */
    public Passenger save(Passenger passenger) {
        // TODO: improve validation – vérifier name, email non vides
        // TODO: add error handling – gérer la duplication d'e-mail avec une exception personnalisée
        return passengerRepository.save(passenger);
    }

    /**
     * Retourne tous les passagers enregistrés.
     *
     * @return liste de Passenger
     */
    public List<Passenger> findAll() {
        return passengerRepository.findAll();
    }

    // TODO: refactor to better separation of concerns – ajouter la gestion des réservations
    //       dans un BookingService séparé (un passager réserve une place sur un trajet)
}
