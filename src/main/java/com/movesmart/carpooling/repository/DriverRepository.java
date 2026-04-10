package com.movesmart.carpooling.repository;

import com.movesmart.carpooling.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * COUCHE REPOSITORY – Accès aux données (DAO pattern).
 *
 * Un Repository est l'interface entre la logique métier et la base de données.
 * En étendant JpaRepository<Driver, Long>, Spring génère automatiquement les
 * opérations CRUD standard (save, findById, findAll, delete, etc.) SANS écrire
 * de code SQL.
 *
 * Paramètres génériques :
 *   - Driver : l'entité gérée
 *   - Long   : le type de la clé primaire
 *
 * Spring Data JPA suit une convention de nommage pour créer des requêtes :
 *   findByEmail → SELECT * FROM drivers WHERE email = ?
 */
@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {

    /**
     * Recherche un conducteur par son adresse e-mail.
     * La requête SQL est générée automatiquement par Spring Data JPA
     * grâce à la convention "findBy + NomDuChamp".
     *
     * @param email l'adresse e-mail à rechercher
     * @return un Optional contenant le conducteur, ou vide si non trouvé
     */
    Optional<Driver> findByEmail(String email);

    // TODO: add error handling – utiliser findByEmail pour vérifier les doublons avant sauvegarde
    // TODO: improve validation – ajouter une contrainte @Column(unique=true) sur email dans Driver
}
