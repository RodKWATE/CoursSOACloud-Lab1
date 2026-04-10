package com.movesmart.carpooling.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entité JPA représentant un Conducteur (Driver).
 *
 * @Entity  : indique à JPA que cette classe correspond à une table en base de données.
 * @Table   : précise le nom de la table (optionnel, sinon le nom de la classe est utilisé).
 * @Id     : marque le champ comme clé primaire.
 * @GeneratedValue : la valeur de l'id est générée automatiquement par la BDD.
 *
 * Les annotations Lombok :
 *   @Data           : génère automatiquement getters, setters, toString, equals, hashCode
 *   @NoArgsConstructor : génère un constructeur sans argument (requis par JPA)
 *   @AllArgsConstructor : génère un constructeur avec tous les arguments
 */
@Entity
@Table(name = "drivers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Nom complet du conducteur */
    private String name;

    /** Adresse e-mail (doit être unique dans un système réel) */
    private String email;

    /** Numéro de téléphone */
    private String phone;

    /** Modèle ou immatriculation du véhicule */
    private String car;

    /** Nombre de places disponibles dans le véhicule */
    private int availableSeats;

    // TODO: improve validation – ajouter @NotBlank, @Email, @Min(1) avec Bean Validation (jakarta.validation)
    // TODO: add error handling – gérer le cas où email est déjà utilisé
}
