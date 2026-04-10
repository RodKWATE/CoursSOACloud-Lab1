package com.movesmart.carpooling.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entité JPA représentant un Passager (Passenger).
 *
 * Notez la similarité de structure avec Driver – dans une architecture SOA plus avancée,
 * on pourrait extraire un concept commun "Utilisateur" (User) dont Driver et Passenger héritent.
 *
 * TODO: refactor to better separation of concerns – envisager une entité parente "User"
 *       ou un service commun d'authentification pour Driver et Passenger.
 */
@Entity
@Table(name = "passengers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Passenger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Nom complet du passager */
    private String name;

    /** Adresse e-mail */
    private String email;

    /** Numéro de téléphone */
    private String phone;

    // TODO: improve validation – ajouter les contraintes @NotBlank, @Email
    // TODO: add error handling – gérer la duplication d'e-mail
}
