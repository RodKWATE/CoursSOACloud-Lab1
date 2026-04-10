package com.movesmart.carpooling.repository;

import com.movesmart.carpooling.model.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * COUCHE REPOSITORY – Accès aux données pour les Passagers.
 *
 * Même principe que DriverRepository : Spring Data JPA génère toutes les requêtes
 * de base. Il suffit de déclarer l'interface — aucune implémentation à écrire.
 *
 * Rappel des méthodes héritées de JpaRepository :
 *   - save(passenger)       → INSERT ou UPDATE
 *   - findById(id)          → SELECT WHERE id = ?
 *   - findAll()             → SELECT * FROM passengers
 *   - deleteById(id)        → DELETE WHERE id = ?
 *   - count()               → SELECT COUNT(*)
 */
@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Long> {

    /**
     * Recherche un passager par son adresse e-mail.
     *
     * @param email l'adresse e-mail à rechercher
     * @return un Optional contenant le passager, ou vide si non trouvé
     */
    Optional<Passenger> findByEmail(String email);

    // TODO: improve validation – vérifier l'unicité de l'e-mail avant d'enregistrer
}
