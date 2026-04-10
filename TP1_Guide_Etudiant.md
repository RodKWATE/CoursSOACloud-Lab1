# TP1 — De l'Application Monolithique vers SOA et REST API

**Cours :** Architecture Logicielle – SOA & Cloud  
**Durée estimée :** 3 heures  
**Niveau :** Licence 3 / Master 1  
**Outils requis :** IntelliJ IDEA, Postman, navigateur web

---

## Objectifs pédagogiques

À la fin de ce TP, vous serez capable de :

- Expliquer ce qu'est une architecture monolithique et ses limites
- Identifier les couches d'une application Spring Boot (Controller, Service, Repository)
- Tester une API REST avec Postman et Swagger UI
- Argumenter pourquoi et comment décomposer un monolithe en services (SOA)

---

## Présentation du projet

L'application **MoveSmart Carpooling** est un système interne de covoiturage pour les employés d'une entreprise.

**Entités métier :**

| Entité | Rôle |
|--------|------|
| `Driver` (Conducteur) | Employé qui propose un trajet |
| `Passenger` (Passager) | Employé qui cherche un trajet |
| `Trip` (Trajet) | Trajet proposé par un conducteur |

**Ce que l'application fait (pour l'instant) :**
- Enregistrer un conducteur
- Enregistrer un passager
- Consulter les trajets disponibles

---

## Avant de commencer

### Prérequis techniques

Vérifiez que vous avez installé :

- [ ] **Java 17** — vérifier avec `java -version` dans le terminal
- [ ] **IntelliJ IDEA** (Community ou Ultimate)
- [ ] **Postman** — téléchargeable sur postman.com
- [ ] **Navigateur web** (Chrome, Firefox, Edge)

### Structure des fichiers à rendre

À la fin du TP, vous rendrez un **rapport PDF** contenant :
- Les captures d'écran demandées à chaque étape
- Les réponses aux questions de réflexion
- Le code des mini-exercices

---

---

# ÉTAPE 1 — Lancer le projet

## Objectif

Démarrer l'application Spring Boot et vérifier qu'elle fonctionne correctement.

## Ce que vous devez faire

### 1.1 — Ouvrir le projet dans IntelliJ

1. Lancez **IntelliJ IDEA**
2. Choisissez `File → Open`
3. Naviguez jusqu'au dossier `Lab1` et cliquez `OK`
4. IntelliJ détecte automatiquement le fichier `pom.xml` → cliquez **"Trust Project"** si demandé
5. Attendez que la barre de progression Maven en bas disparaisse (~1 minute la première fois)

> **Pourquoi Maven télécharge-t-il des fichiers ?**  
> Maven est le gestionnaire de dépendances du projet. Il télécharge automatiquement toutes les bibliothèques déclarées dans `pom.xml` (Spring Boot, H2, Lombok, Springdoc…). Ces fichiers sont stockés dans `~/.m2/repository`.

### 1.2 — Lancer l'application

1. Dans l'arborescence de gauche, ouvrez :  
   `src/main/java/com/movesmart/carpooling/CarpoolingApplication.java`
2. Cliquez sur la flèche verte ▶ à gauche de la méthode `main`
3. Sélectionnez **"Run CarpoolingApplication"**

Si erreur A Vérifier dans IntelliJ 
Dans IntelliJ, effectuez ces 3 vérifications :

A. SDK du projet → doit être JDK 17
File → Project Structure → Project
    SDK : Java 17  (C:\Program Files\Java\jdk-17.0.1)
    Language level : 17
    
B. Compilation → doit pointer vers JDK 17
File → Settings → Build, Execution, Deployment
    → Compiler → Java Compiler
    → Project bytecode version : 17
    
C. Annotation Processing → doit être activé pour Lombok
File → Settings → Build, Execution, Deployment
    → Compiler → Annotation Processors
    → ✅ Enable annotation processing

### 1.3 — Vérifier le démarrage

Dans la console IntelliJ (onglet **Run**), vous devez voir :

```
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
...
Started CarpoolingApplication in 2.xxx seconds
```

La ligne clé est : `Started CarpoolingApplication in X seconds`

### 1.4 — Premier test dans le navigateur

Ouvrez votre navigateur et accédez à :

```
http://localhost:8080/api/trips
```

Vous devez voir une réponse JSON similaire à :

```json
[
  {
    "id": 1,
    "departure": "Garoua – Gare Routiere",
    "destination": "Bafoussam – Marche B",
    "departureTime": "2026-04-15T07:30:00",
    "availableSeats": 3,
    "driver": null
  },
  ...
]
```

> **Note :** Le champ `driver` apparaît `null` dans la réponse car la relation est en `FETCH.LAZY`.  
> Nous verrons comment corriger cela dans les étapes suivantes.

## Résultat attendu

- L'application démarre sans erreur
- L'URL `/api/trips` retourne un tableau JSON avec 2 trajets

## Capture d'écran à inclure dans le rapport

> **[SCREENSHOT 1]** Console IntelliJ montrant le message `Started CarpoolingApplication`  
> **[SCREENSHOT 2]** Navigateur affichant la réponse JSON de `/api/trips`

---

---

# ÉTAPE 2 — Comprendre la structure monolithique

## Objectif

Comprendre ce qu'est une architecture monolithique et identifier ses caractéristiques dans le projet.

## Ce que vous devez faire

### 2.1 — Explorer l'arborescence

Dans IntelliJ, développez l'arborescence du projet et observez la structure des packages :

```
com.movesmart.carpooling/
├── model/          ← Entités de données (Driver, Passenger, Trip)
├── repository/     ← Accès à la base de données
├── service/        ← Logique métier
├── controller/     ← Exposition HTTP / REST
└── config/         ← Configuration Spring
```

### 2.2 — Lire le fichier principal

Ouvrez [CarpoolingApplication.java](src/main/java/com/movesmart/carpooling/CarpoolingApplication.java) et lisez les commentaires.

Notez l'annotation `@SpringBootApplication` : elle active à elle seule toute la configuration automatique de Spring Boot.

### 2.3 — Consulter la console H2

La base de données H2 est entièrement en mémoire. Accédez à sa console web :

```
http://localhost:8080/h2-console
```

Renseignez les champs :
- **JDBC URL :** `jdbc:h2:mem:carpoolingdb`
- **User Name :** `sa`
- **Password :** *(laisser vide)*

Cliquez **Connect**, puis exécutez la requête :

```sql
SELECT * FROM TRIPS;
```

Vous voyez les 2 trajets insérés par `data.sql` au démarrage.

Exécutez également :

```sql
SELECT * FROM DRIVERS;
SELECT * FROM PASSENGERS;
```

> **Observation :** La table `PASSENGERS` est vide — aucune donnée initiale n'a été insérée pour les passagers.

## Questions de réflexion — Partie 2

> Répondez à ces questions dans votre rapport (5-8 lignes par question).

**Q1.** Qu'est-ce qu'une architecture monolithique ? Donnez une définition précise et citez 3 caractéristiques.

**Q2.** Dans ce projet, toutes les couches (model, repository, service, controller) sont dans **une seule application**. Quels sont les avantages de cette approche pour un petit projet ?

**Q3.** Quelles seraient les **limites** de cette approche si l'application devait gérer 10 000 conducteurs simultanément ?

## Capture d'écran à inclure dans le rapport

> **[SCREENSHOT 3]** Console H2 avec le résultat de `SELECT * FROM TRIPS`  
> **[SCREENSHOT 4]** Arborescence du projet dans IntelliJ (tous les packages visibles)

---

---

# ÉTAPE 3 — Identifier les couches (Controller, Service, Repository)

## Objectif

Comprendre le rôle précis de chaque couche et tracer le flux d'une requête HTTP du Controller jusqu'à la base de données.

## Ce que vous devez faire

### 3.1 — Lire et annoter le Repository

Ouvrez [TripRepository.java](src/main/java/com/movesmart/carpooling/repository/TripRepository.java).

Observez :
- Cette interface **n'a aucune implémentation**
- Elle hérite de `JpaRepository<Trip, Long>`
- Spring génère automatiquement le SQL correspondant

**Testez dans la console H2** ce que fait `findByAvailableSeatsGreaterThan(0)` :

```sql
SELECT * FROM TRIPS WHERE AVAILABLE_SEATS > 0;
```

### 3.2 — Lire et annoter le Service

Ouvrez [TripService.java](src/main/java/com/movesmart/carpooling/service/TripService.java).

Repérez :
- L'injection de dépendance par constructeur (`final` + constructeur)
- La méthode `findAvailableTrips()` qui délègue au repository
- Les commentaires `TODO` — c'est là que vous travaillerez dans les exercices

### 3.3 — Lire et annoter le Controller

Ouvrez [TripController.java](src/main/java/com/movesmart/carpooling/controller/TripController.java).

Identifiez :
- L'annotation `@RestController` et `@RequestMapping`
- La différence entre `@GetMapping` et `@PostMapping`
- Le type de retour `ResponseEntity<List<Trip>>`

### 3.4 — Tracer le flux d'une requête

Lorsqu'un client fait `GET /api/trips`, voici le flux exact :

```
Client (Postman/navigateur)
        │
        │  HTTP GET /api/trips
        ▼
TripController.getAvailableTrips()
        │
        │  appelle tripService.findAvailableTrips()
        ▼
TripService.findAvailableTrips()
        │
        │  appelle tripRepository.findByAvailableSeatsGreaterThan(0)
        ▼
TripRepository  (généré par Spring Data JPA)
        │
        │  SELECT * FROM trips WHERE available_seats > 0
        ▼
Base de données H2
        │
        │  ResultSet → List<Trip>
        ▼
(remonte dans l'ordre inverse)
        │
        ▼
Client reçoit JSON [{ "id": 1, ... }, { "id": 2, ... }]
```

> **Exercice de traçage :** Refaites ce schéma pour la requête `POST /api/drivers` dans votre rapport.

## Questions de réflexion — Partie 3

**Q4.** Pourquoi est-il important de séparer Controller, Service et Repository en couches distinctes ? Que se passerait-il si on mettait tout dans le Controller ?

**Q5.** Qu'est-ce que l'injection de dépendance (Dependency Injection) ? Pourquoi utilise-t-on un constructeur plutôt que `@Autowired` sur le champ ?

**Q6.** `JpaRepository` vous offre `save()`, `findAll()`, `findById()`, `delete()` sans écrire une seule ligne de SQL. Quel design pattern reconnaissez-vous ici ?

## Capture d'écran à inclure dans le rapport

> **[SCREENSHOT 5]** Code de `TripController.java` dans IntelliJ  
> **[SCREENSHOT 6]** Code de `TripService.java` dans IntelliJ  
> **[SCREENSHOT 7]** Votre schéma de flux pour `POST /api/drivers` (dessiné ou réalisé avec un outil)

---

---

# ÉTAPE 4 — Tester les endpoints avec Postman

## Objectif

Utiliser Postman pour tester chacun des endpoints REST de l'application et comprendre les codes de statut HTTP.

## Ce que vous devez faire

### 4.1 — Créer une collection Postman

1. Lancez **Postman**
2. Cliquez **"New Collection"** → nommez-la `MoveSmart Carpooling – TP1`
3. Vous allez y ajouter les requêtes suivantes

---

### TEST 1 — Lister les trajets disponibles

| Champ | Valeur |
|-------|--------|
| Méthode | `GET` |
| URL | `http://localhost:8080/api/trips` |
| Body | *(aucun)* |

**Résultat attendu :** `200 OK` + tableau JSON de 2 trajets

---

### TEST 2 — Créer un conducteur

| Champ | Valeur |
|-------|--------|
| Méthode | `POST` |
| URL | `http://localhost:8080/api/drivers` |
| Header | `Content-Type: application/json` |
| Body (raw JSON) | voir ci-dessous |

```json
{
  "name": "Thomas Sankara",
  "email": "thomas.sankara@movesmart.com",
  "phone": "0612345678",
  "car": "Faso 101 – EL-456-CT",
  "availableSeats": 4
}
```

**Résultat attendu :** `201 Created` + objet JSON avec un `"id"` généré (ex: `2`)

---

### TEST 3 — Créer un passager

| Champ | Valeur |
|-------|--------|
| Méthode | `POST` |
| URL | `http://localhost:8080/api/passengers` |
| Header | `Content-Type: application/json` |
| Body (raw JSON) | voir ci-dessous |

```json
{
  "name": "Votre_Nom Votre_prenom",
  "email": "votre_Nom.votre_prenom@movesmart.com",
  "phone": "0698765432"
}
```

**Résultat attendu :** `201 Created` + objet JSON avec `"id": 1`

---

### TEST 4 — Vérifier la persistance

Après avoir créé le conducteur (TEST 2), exécutez :

| Champ | Valeur |
|-------|--------|
| Méthode | `GET` |
| URL | `http://localhost:8080/api/drivers` |

**Résultat attendu :** Tableau contenant **Douala Manga Bell** (données initiales) + **Thomas Sankara** (que vous venez de créer)

---

### TEST 5 — Simuler une erreur

Envoyez une requête `POST /api/drivers` avec un body **invalide** (JSON malformé) :

```
Body: { "name": "Test"    ← JSON incomplet, pas de } de fermeture
```

**Résultat attendu :** `400 Bad Request`

> **Observation :** Spring Boot retourne automatiquement une erreur 400 pour un JSON malformé. En revanche, un body `{}` (JSON valide mais champs vides) est accepté sans erreur — c'est un `TODO` dans le code.

---

### Tableau de synthèse des codes HTTP

Complétez ce tableau dans votre rapport :

| Code | Signification | Dans notre API |
|------|--------------|----------------|
| `200 OK` | Requête réussie | `GET /api/trips` réussit |
| `201 Created` | Ressource créée | `POST /api/drivers` réussit |
| `400 Bad Request` | ??? | ??? |
| `404 Not Found` | ??? | ??? |
| `500 Internal Server Error` | ??? | ??? |

## Questions de réflexion — Partie 4

**Q7.** Quelle est la différence sémantique entre `200 OK` et `201 Created` ? Pourquoi est-il important d'utiliser le bon code de statut ?

**Q8.** Que se passe-t-il si vous redémarrez l'application après avoir créé Thomas Sankara ? Retrouvez-vous le conducteur dans la liste ? Pourquoi ?

## Capture d'écran à inclure dans le rapport

> **[SCREENSHOT 8]** Postman — `POST /api/drivers` avec réponse `201 Created`  
> **[SCREENSHOT 9]** Postman — `GET /api/drivers` montrant les 2 conducteurs  
> **[SCREENSHOT 10]** Postman — requête avec JSON invalide et réponse `400`

---

---

# ÉTAPE 5 — Explorer Swagger UI

## Objectif

Utiliser Swagger UI pour documenter et tester l'API sans Postman, et comprendre l'intérêt de l'OpenAPI Specification.

## Ce que vous devez faire

### 5.1 — Ouvrir Swagger UI

Dans votre navigateur :

```
http://localhost:8080/swagger-ui.html
```

Vous voyez une interface avec 3 sections :
- **Drivers** — endpoints `/api/drivers`
- **Passengers** — endpoints `/api/passengers`
- **Trips** — endpoints `/api/trips`

### 5.2 — Tester un endpoint depuis Swagger

1. Cliquez sur la section **Trips**
2. Cliquez sur `GET /api/trips`
3. Cliquez **"Try it out"**
4. Cliquez **"Execute"**

Swagger affiche :
- La **requête curl** équivalente
- L'URL appelée
- Le **code de réponse** et le **body JSON**

### 5.3 — Explorer le contrat OpenAPI

Accédez à la spécification brute au format JSON :

```
http://localhost:8080/v3/api-docs
```

Observez la structure :
- `info` : métadonnées de l'API (nom, version, description)
- `paths` : liste de tous les endpoints avec leurs paramètres
- `components/schemas` : définition des objets JSON (Driver, Passenger, Trip)

> **Question d'observation :** Le schéma de `Trip` dans `/v3/api-docs` inclut-il le champ `driver` ? Sous quelle forme ?

### 5.4 — Lire le code OpenApiConfig

Ouvrez [OpenApiConfig.java](src/main/java/com/movesmart/carpooling/config/OpenApiConfig.java) dans IntelliJ.

Repérez comment les métadonnées (titre, version, description, contact) sont définies programmatiquement via les classes `OpenAPI` et `Info`.

## Questions de réflexion — Partie 5

**Q9.** Qu'est-ce qu'une spécification OpenAPI ? Quel est son intérêt dans une architecture SOA où plusieurs équipes consomment la même API ?

**Q10.** Comment Springdoc génère-t-il automatiquement la documentation Swagger sans que vous écriviez de fichier YAML ? Quel mécanisme Java est utilisé ?

## Capture d'écran à inclure dans le rapport

> **[SCREENSHOT 11]** Swagger UI — page principale avec les 3 sections  
> **[SCREENSHOT 12]** Swagger UI — résultat d'un `GET /api/trips` exécuté depuis l'interface  
> **[SCREENSHOT 13]** Navigateur — extrait de `/v3/api-docs` (section `paths`)

---

---

# ÉTAPE 6 — Discussion : Penser en termes de SOA

## Objectif

Analyser les limites de l'architecture actuelle et identifier comment la faire évoluer vers une architecture orientée services (SOA).

## Ce que vous devez faire

### 6.1 — Analyser les TODOs du code

Recherchez tous les commentaires `TODO` dans le projet. Dans IntelliJ, utilisez :

```
Edit → Find → Find in Files → recherchez "TODO"
```

Vous trouverez des TODOs de 3 types :

| Type de TODO | Exemple | Couche |
|---|---|---|
| Validation | `TODO: improve validation` | Model, Service |
| Gestion d'erreurs | `TODO: add error handling` | Controller, Service |
| Refactoring SOA | `TODO: refactor to better separation of concerns` | Service, Controller |

### 6.2 — Analyser les limites actuelles

Réfléchissez aux scénarios suivants et notez vos observations dans le rapport :

**Scénario A — Montée en charge**  
L'entreprise passe de 100 à 100 000 employés. La liste des trajets prend 5 secondes à charger.  
→ Que feriez-vous avec l'architecture actuelle (monolithe) ? Et avec des microservices ?

**Scénario B — Équipes multiples**  
Une équipe développe les fonctionnalités "Conducteur", une autre les fonctionnalités "Trajet".  
→ Quels problèmes surgissent dans un monolithe ? Comment SOA résoudrait-il cela ?

**Scénario C — Nouvelles applications**  
L'entreprise veut une app mobile iOS + Android en plus du site web.  
→ L'architecture REST actuelle est-elle adaptée ? Pourquoi ?

### 6.3 — Schéma d'évolution architecturale

Réalisez dans votre rapport un schéma montrant les 3 phases d'évolution :

```
┌─────────────────────────────────────────────┐
│  PHASE 1 — Monolithe (TP1 actuel)           │
│  ┌──────────────────────────────────────┐   │
│  │  Controller + Service + Repository   │   │
│  │  + BDD H2 (tout dans 1 processus)    │   │
│  └──────────────────────────────────────┘   │
└─────────────────────────────────────────────┘

         ↓ Décomposition (TP2 / TP3)

┌───────────────┐  ┌────────────────┐  ┌──────────────┐
│ Driver Service│  │Passenger Service│  │ Trip Service │
│  REST API     │  │  REST API       │  │  REST API    │
│  BDD propre   │  │  BDD propre     │  │  BDD propre  │
└───────────────┘  └────────────────┘  └──────────────┘
        │                  │                   │
        └──────────────────┴───────────────────┘
                           │
                    ┌──────────────┐
                    │ API Gateway  │
                    │  (point      │
                    │  d'entrée    │
                    │  unique)     │
                    └──────────────┘
```

## Questions de réflexion — Partie 6

**Q11.** Définissez SOA (Service-Oriented Architecture) en vos propres mots. Quelle est la différence avec les microservices ?

**Q12.** Dans l'architecture SOA proposée ci-dessus, si le `Trip Service` doit connaître le nom du conducteur, comment récupère-t-il cette information ? Quels problèmes cela pose-t-il ?

**Q13.** Citez 2 avantages et 2 inconvénients d'une architecture microservices par rapport au monolithe actuel, dans le contexte de cette application de covoiturage.

## Capture d'écran à inclure dans le rapport

> **[SCREENSHOT 14]** Résultat de la recherche "TODO" dans IntelliJ (Find in Files)  
> **[SCREENSHOT 15]** Votre schéma d'évolution architecturale (dessiné ou outil de diagramme)

---

---

# MINI-EXERCICES

> Ces exercices sont à réaliser directement dans le code. Incluez dans votre rapport le **code modifié** et une **capture d'écran** du test Postman ou du résultat.

---

## Mini-Exercice 1 — Modifier un endpoint *(difficulté : facile)*

**Contexte :** L'endpoint `GET /api/trips` retourne tous les trajets disponibles. Vous devez ajouter un endpoint qui retourne **un trajet spécifique** par son identifiant.

**À faire dans** [TripController.java](src/main/java/com/movesmart/carpooling/controller/TripController.java) :

Ajoutez la méthode suivante :

```java
@GetMapping("/{id}")
@Operation(summary = "Récupérer un trajet par ID")
public ResponseEntity<Trip> getTripById(@PathVariable Long id) {
    // TODO: compléter cette méthode
    // Indice : utilisez tripService.findAll() pour l'instant,
    // puis cherchez le bon élément dans la liste
    // (ou mieux : ajoutez findById dans TripService)
}
```

**Test attendu :**
```
GET http://localhost:8080/api/trips/1   → 200 OK avec le trajet 1
GET http://localhost:8080/api/trips/99  → que se passe-t-il ? est-ce le bon comportement ?
```

**Bonus :** Retournez `404 Not Found` si le trajet n'existe pas, en utilisant `ResponseEntity.notFound().build()`.

---

## Mini-Exercice 2 — Ajouter un champ *(difficulté : moyen)*

**Contexte :** L'entité `Trip` n'a pas de champ `description` (ex: "Pas d'animaux", "Trajet non-fumeur").

**À faire :**

1. Dans [Trip.java](src/main/java/com/movesmart/carpooling/model/Trip.java), ajoutez le champ :
   ```java
   private String description;
   ```

2. Redémarrez l'application (Hibernate recrée automatiquement la table)

3. Dans [data.sql](src/main/resources/data.sql), ajoutez la description dans les INSERT :
   ```sql
   INSERT INTO trips (departure, destination, departure_time, available_seats, driver_id, description)
   VALUES ('Garoua – Gare Routiere', 'Bafoussam – Marche B', '2026-04-15 07:30:00', 3, 1, 'Trajet non-fumeur');
   ```

4. Testez avec `GET /api/trips` → le champ `description` doit apparaître dans le JSON

**Observation :** Vous n'avez modifié **que l'entité** et **data.sql**. Le controller, le service et le repository n'ont pas changé. Pourquoi ?

---

## Mini-Exercice 3 — Casser et réparer *(difficulté : difficile)*

**Objectif :** Provoquer intentionnellement des erreurs pour mieux comprendre les mécanismes de Spring.

### Cassure 1 — Supprimer `@RestController`

Dans `DriverController.java`, supprimez l'annotation `@RestController` (gardez juste `@RequestMapping`).

- Redémarrez l'application
- Testez `POST /api/drivers` avec Postman
- Que se passe-t-il ? Quel code HTTP obtenez-vous ? Pourquoi ?
- **Réparez** en remettant `@RestController`

### Cassure 2 — Créer une dépendance circulaire

Dans `DriverService.java`, ajoutez une dépendance vers `PassengerService` :

```java
// Ajoutez ce champ et modifiez le constructeur
private final PassengerService passengerService;

public DriverService(DriverRepository driverRepository, PassengerService passengerService) {
    this.driverRepository = driverRepository;
    this.passengerService = passengerService;
}
```

Dans `PassengerService.java`, ajoutez de même une dépendance vers `DriverService`.

- Redémarrez et observez l'erreur dans la console
- Qu'est-ce qu'une dépendance circulaire ?
- Comment Spring Boot détecte-t-il ce problème ?
- **Réparez** en supprimant les dépendances ajoutées

### Cassure 3 — Erreur de configuration H2

Dans `application.properties`, changez la ligne :

```properties
# Avant :
spring.datasource.url=jdbc:h2:mem:carpoolingdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE

# Après (URL volontairement incorrecte) :
spring.datasource.url=jdbc:h2:mem:MAUVAISE_URL;MODE=INEXISTANT
```

- Redémarrez et observez l'erreur
- Lisez le stack trace : quelle est la cause racine (`Caused by:`) ?
- **Réparez** en restaurant la bonne URL

---

---

# RÉCAPITULATIF — Ce que vous avez appris

| Concept | Acquis ? |
|---------|----------|
| Démarrer une application Spring Boot | ☐ |
| Identifier les 3 couches (Controller / Service / Repository) | ☐ |
| Tracer le flux d'une requête HTTP | ☐ |
| Tester une API REST avec Postman | ☐ |
| Lire et utiliser Swagger UI | ☐ |
| Expliquer les limites d'un monolithe | ☐ |
| Identifier les évolutions vers SOA | ☐ |

---

# GRILLE D'ÉVALUATION DU RAPPORT

| Critère | Points |
|---------|--------|
| Captures d'écran complètes et lisibles (15 screenshots) | /4 |
| Réponses aux questions de réflexion (Q1–Q13) | /6 |
| Mini-exercice 1 (endpoint `GET /{id}`) fonctionnel + code | /3 |
| Mini-exercice 2 (champ `description`) fonctionnel + explication | /3 |
| Mini-exercice 3 (3 cassures analysées + réparées) | /3 |
| Schéma d'évolution architecturale (Étape 6.3) | /1 |
| **TOTAL** | **/20** |

---

# RESSOURCES COMPLÉMENTAIRES

| Ressource | URL |
|-----------|-----|
| Documentation Spring Boot | https://docs.spring.io/spring-boot/docs/current/reference/html/ |
| Spring Data JPA | https://docs.spring.io/spring-data/jpa/docs/current/reference/html/ |
| Springdoc OpenAPI | https://springdoc.org/ |
| Codes de statut HTTP | https://developer.mozilla.org/fr/docs/Web/HTTP/Status |
| Richardson Maturity Model (REST) | https://martinfowler.com/articles/richardsonMaturityModel.html |

---

*Document préparé pour le cours Architecture Logicielle – SOA & Cloud*  
*Toute reproduction à des fins pédagogiques est autorisée.*
