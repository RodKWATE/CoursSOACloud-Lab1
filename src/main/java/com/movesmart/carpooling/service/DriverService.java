package com.movesmart.carpooling.service;

import com.movesmart.carpooling.model.Driver;
import com.movesmart.carpooling.repository.DriverRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * COUCHE SERVICE – Logique métier pour les Conducteurs.
 *
 * Un Service est le coeur de l'application : il contient la logique métier
 * et fait le lien entre le Controller (qui reçoit les requêtes HTTP) et
 * le Repository (qui accède à la base de données).
 *
 * @Service : indique à Spring de créer un bean de ce type et de le gérer
 *            dans son conteneur IoC (Inversion of Control).
 *
 * Principe de responsabilité unique (SRP) :
 *   - Le Controller gère uniquement HTTP (parsing, réponse)
 *   - Le Service gère uniquement la logique métier
 *   - Le Repository gère uniquement l'accès aux données
 */
@Service
public class DriverService {

    // Injection de dépendance par constructeur (meilleure pratique que @Autowired sur le champ)
    private final DriverRepository driverRepository;

    public DriverService(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    /**
     * Enregistre un nouveau conducteur en base de données.
     *
     * @param driver l'objet Driver à sauvegarder
     * @return le Driver sauvegardé (avec son id généré)
     */
    public Driver save(Driver driver) {
        // TODO: improve validation – vérifier que les champs obligatoires ne sont pas null ou vides
        // TODO: add error handling – lever une exception métier si l'e-mail est déjà utilisé
        //       ex: throw new EmailAlreadyExistsException(driver.getEmail());
        return driverRepository.save(driver);
    }

    /**
     * Retourne la liste de tous les conducteurs enregistrés.
     *
     * @return liste de Driver
     */
    public List<Driver> findAll() {
        return driverRepository.findAll();
    }

    // TODO: refactor to better separation of concerns – ajouter findById, update, delete
    //       pour avoir un CRUD complet
}
