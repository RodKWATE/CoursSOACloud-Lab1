-- ============================================================
-- DONNÉES INITIALES – MoveSmart Carpooling TP1
-- Ce fichier est exécuté automatiquement au démarrage de l'application.
-- Les tables sont d'abord créées par Hibernate (via ddl-auto=create-drop),
-- puis ces INSERTs sont exécutés.
-- ============================================================

-- ------------------------------------------------------------
-- 1 Conducteur
-- ------------------------------------------------------------
INSERT INTO drivers (name, email, phone, car, available_seats)
VALUES ('Douala Manga Bell', 'doualamanga@movesmart.com', '0601020304', 'BYD Atto 3 – AB-123-CD', 3);

-- ------------------------------------------------------------
-- 2 Trajets proposés par Alice (driver_id = 1)
-- ------------------------------------------------------------
INSERT INTO trips (departure, destination, departure_time, available_seats, driver_id)
VALUES ('Garoua – Gare Routiere', 'Bafoussam – Marche B', '2026-04-15 07:30:00', 3, 1);

INSERT INTO trips (departure, destination, departure_time, available_seats, driver_id)
VALUES ('Abidjan – Cocody', 'Abuja – Maitama', '2026-04-16 08:00:00', 2, 1);

-- ============================================================
-- TODO: improve validation – dans une vraie application, ces données
--       seraient chargées via un script de migration Flyway ou Liquibase
--       avec versioning et rollback possible.
-- ============================================================
