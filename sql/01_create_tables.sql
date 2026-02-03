-- phpMyAdmin SQL Dump
-- version 5.2.3
-- https://www.phpmyadmin.net/
--
-- Hôte : db:3306
-- Généré le : mar. 03 fév. 2026 à 17:13
-- Version du serveur : 11.8.5-MariaDB-ubu2404
-- Version de PHP : 8.3.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `maintenance`
--

-- --------------------------------------------------------

--
-- Structure de la table `batiment`
--

CREATE TABLE `batiment` (
  `id` int(11) NOT NULL,
  `nom` varchar(100) NOT NULL,
  `localisation` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

--
-- Déchargement des données de la table `batiment`
--

INSERT INTO `batiment` (`id`, `nom`, `localisation`) VALUES
(1, 'Bâtiment A', 'Campus Nord'),
(2, 'Bâtiment B', 'Campus Sud'),
(3, 'Bâtiment C', 'Centre Ville'),
(4, 'Entrepôt', 'Zone Industrielle'),
(5, 'Entrepôt 2', 'Zone Franche');

-- --------------------------------------------------------

--
-- Structure de la table `intervention`
--

CREATE TABLE `intervention` (
  `id` int(11) NOT NULL,
  `technicien_id` int(11) NOT NULL,
  `responsable_id` int(11) NOT NULL,
  `batiment_id` int(11) NOT NULL,
  `date_intervention` datetime NOT NULL,
  `type_intervention` varchar(100) NOT NULL,
  `statut` enum('Planifiée','En cours','Terminée') NOT NULL DEFAULT 'Planifiée',
  `description` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `responsable`
--

CREATE TABLE `responsable` (
  `id` int(11) NOT NULL,
  `nom` varchar(100) NOT NULL,
  `prenom` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `telephone` varchar(20) NOT NULL,
  `mdp_hash` varchar(32) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

--
-- Déchargement des données de la table `responsable`
--

INSERT INTO `responsable` (`id`, `nom`, `prenom`, `email`, `telephone`, `mdp_hash`) VALUES
(1, 'Admin', 'Principal', 'admin@maintenance.local', '0600000001', md5('admin123')),
(2, 'Responsable', 'Secondaire', 'resp@maintenance.local', '0600000002', md5('resp123'));

-- --------------------------------------------------------

--
-- Structure de la table `technicien`
--

CREATE TABLE `technicien` (
  `id` int(11) NOT NULL,
  `nom` varchar(100) NOT NULL,
  `prenom` varchar(100) NOT NULL,
  `telephone` varchar(20) NOT NULL,
  `qualification` varchar(100) NOT NULL,
  `disponible` tinyint(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

--
-- Déchargement des données de la table `technicien`
--

INSERT INTO `technicien` (`id`, `nom`, `prenom`, `telephone`, `qualification`, `disponible`) VALUES
(1, 'Dupont', 'Jean', '0611111111', 'Électricité', 1),
(2, 'Martin', 'Claire', '0622222222', 'Plomberie', 1),
(3, 'Bernard', 'Paul', '0633333333', 'Climatisation', 0),
(4, 'Durand', 'Sophie', '0644444444', 'Maintenance générale', 1),
(5, 'test', '1', '2', 'passe partout', 1);

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `batiment`
--
ALTER TABLE `batiment`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `intervention`
--
ALTER TABLE `intervention`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_intervention_technicien` (`technicien_id`),
  ADD KEY `fk_intervention_responsable` (`responsable_id`),
  ADD KEY `fk_intervention_batiment` (`batiment_id`);

--
-- Index pour la table `responsable`
--
ALTER TABLE `responsable`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `technicien`
--
ALTER TABLE `technicien`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `batiment`
--
ALTER TABLE `batiment`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT pour la table `intervention`
--
ALTER TABLE `intervention`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `responsable`
--
ALTER TABLE `responsable`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT pour la table `technicien`
--
ALTER TABLE `technicien`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `intervention`
--
ALTER TABLE `intervention`
  ADD CONSTRAINT `fk_intervention_batiment` FOREIGN KEY (`batiment_id`) REFERENCES `batiment` (`id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_intervention_responsable` FOREIGN KEY (`responsable_id`) REFERENCES `responsable` (`id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_intervention_technicien` FOREIGN KEY (`technicien_id`) REFERENCES `technicien` (`id`) ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
