-- =====================================================
-- Projet Java ISN – Gestion de Maintenance
-- Base de données : maintenance
-- SGBD : MariaDB
-- =====================================================

-- Suppression des tables si elles existent déjà (mode développement)
DROP TABLE IF EXISTS intervention;
DROP TABLE IF EXISTS batiment;
DROP TABLE IF EXISTS technicien;

-- =====================================================
-- Table : technicien
-- =====================================================
CREATE TABLE technicien (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    qualification VARCHAR(100) NOT NULL,
    disponible BOOLEAN NOT NULL DEFAULT TRUE
);

-- =====================================================
-- Table : batiment
-- =====================================================
CREATE TABLE batiment (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    localisation VARCHAR(255) NOT NULL
);

-- =====================================================
-- Table : intervention
-- =====================================================
CREATE TABLE intervention (
    id INT AUTO_INCREMENT PRIMARY KEY,

    technicien_id INT NOT NULL,
    batiment_id INT NOT NULL,

    date_intervention DATETIME NOT NULL,
    type_intervention VARCHAR(100) NOT NULL,

    statut ENUM('Planifiée', 'En cours', 'Terminée')
           NOT NULL DEFAULT 'Planifiée',

    description TEXT,

    -- Clé étrangère vers technicien
    CONSTRAINT fk_intervention_technicien
        FOREIGN KEY (technicien_id)
        REFERENCES technicien(id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,

    -- Clé étrangère vers batiment
    CONSTRAINT fk_intervention_batiment
        FOREIGN KEY (batiment_id)
        REFERENCES batiment(id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);
