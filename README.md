# TP1 — MoveSmart Carpooling
### Architecture Logicielle : Monolithique → SOA → REST API

> Projet de démarrage pour le TP1 du cours **Architecture Logicielle – SOA & Cloud**.  
> Les étudiants utilisent ce projet comme base pour comprendre et faire évoluer une architecture monolithique.

---

## Contexte

**MoveSmart Carpooling** est un système interne de covoiturage pour les employés d'une entreprise.  
L'application est volontairement simple : elle constitue le point de départ d'une évolution architecturale progressive.

| Phase | Description | Statut |
|-------|-------------|--------|
| **Phase 1** | Application monolithique (ce dépôt) | ✅ TP1 |
| **Phase 2** | Décomposition en services (SOA) | 🔜 TP2 |
| **Phase 3** | API Gateway + contrats OpenAPI | 🔜 TP3 |

---

## Stack technique

| Technologie | Version | Rôle |
|-------------|---------|------|
| Java | 17 | Langage |
| Spring Boot | 3.2.5 | Framework applicatif |
| Spring Data JPA | (inclus) | Accès aux données |
| H2 Database | (inclus) | Base de données en mémoire |
| Springdoc OpenAPI | 2.5.0 | Documentation Swagger UI |
| Lombok | (inclus) | Réduction du code boilerplate |
| Maven | 3.x | Gestion des dépendances |

---

## Structure du projet

```
src/
└── main/
    ├── java/com/movesmart/carpooling/
    │   ├── CarpoolingApplication.java   ← Point d'entrée Spring Boot
    │   ├── model/
    │   │   ├── Driver.java              ← Entité Conducteur
    │   │   ├── Passenger.java           ← Entité Passager
    │   │   └── Trip.java                ← Entité Trajet
    │   ├── repository/
    │   │   ├── DriverRepository.java
    │   │   ├── PassengerRepository.java
    │   │   └── TripRepository.java
    │   ├── service/
    │   │   ├── DriverService.java
    │   │   ├── PassengerService.java
    │   │   └── TripService.java
    │   ├── controller/
    │   │   ├── DriverController.java
    │   │   ├── PassengerController.java
    │   │   └── TripController.java
    │   └── config/
    │       └── OpenApiConfig.java
    └── resources/
        ├── application.properties       ← Configuration H2, JPA, Swagger
        └── data.sql                     ← Données initiales (1 conducteur, 2 trajets)
```

---

## Prérequis

- [ ] **Java 17** — vérifier avec `java -version`
- [ ] **Maven 3.x** — vérifier avec `mvn -version`  
  *(ou utiliser le wrapper Maven inclus dans IntelliJ)*
- [ ] **IntelliJ IDEA** (Community ou Ultimate)
- [ ] **Postman** — pour tester les endpoints

---

## Lancer le projet

### Option A — IntelliJ IDEA

```
1. File → Open → sélectionner le dossier Lab1
2. Laisser Maven télécharger les dépendances (~1 min)
3. Ouvrir CarpoolingApplication.java
4. Cliquer sur ▶ Run
```

### Option B — Terminal

```bash
git clone https://github.com/RodKWATE/CoursSOACloud-Lab1.git
cd CoursSOACloud-Lab1
mvn spring-boot:run
```

L'application démarre sur le port **8080**.

---

## Endpoints disponibles

| Méthode | URL | Description | Code retour |
|---------|-----|-------------|-------------|
| `POST` | `/api/drivers` | Créer un conducteur | `201 Created` |
| `GET` | `/api/drivers` | Lister les conducteurs | `200 OK` |
| `POST` | `/api/passengers` | Créer un passager | `201 Created` |
| `GET` | `/api/passengers` | Lister les passagers | `200 OK` |
| `GET` | `/api/trips` | Trajets disponibles (places > 0) | `200 OK` |
| `GET` | `/api/trips/all` | Tous les trajets | `200 OK` |

### Exemple — Créer un conducteur

```bash
curl -X POST http://localhost:8080/api/drivers \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Douala Manga Bell",
    "email": "doualamanga@movesmart.com",
    "phone": "0601020304",
    "car": "BYD Atto 3",
    "availableSeats": 3
  }'
```

---

## Interfaces web

Une fois l'application lancée :

| Interface | URL | Description |
|-----------|-----|-------------|
| **Swagger UI** | http://localhost:8080/swagger-ui.html | Documentation interactive de l'API |
| **OpenAPI JSON** | http://localhost:8080/v3/api-docs | Spécification OpenAPI brute |
| **Console H2** | http://localhost:8080/h2-console | Interface SQL de la base de données |

> **Connexion H2 :**  
> JDBC URL : `jdbc:h2:mem:carpoolingdb` · User : `sa` · Password : *(vide)*

---

## Données initiales

Au démarrage, `data.sql` insère automatiquement :

- **1 conducteur** — Douala Manga Bell
- **2 trajets** — Garoua→Bafoussam et Abidjan→Abuja

---

## Guide du TP

Le fichier [TP1_Guide_Etudiant.md](TP1_Guide_Etudiant.md) contient :

- Les 6 étapes du TP avec objectifs et résultats attendus
- Les 13 questions de réflexion
- Les 3 mini-exercices (facile → difficile)
- La grille d'évaluation (barème /20)

---

## TODOs pour les étudiants

Le code contient des commentaires `TODO` aux endroits où les étudiants doivent intervenir :

```
TODO: improve validation       → ajouter @NotBlank, @Email, @Min
TODO: add error handling       → gérer les codes HTTP 404, 409
TODO: refactor to better separation of concerns → extraire BookingService
```

Recherchez tous les TODOs dans IntelliJ : `Edit → Find → Find in Files → "TODO"`

---

## Licence

Projet pédagogique — libre d'utilisation à des fins d'enseignement.
