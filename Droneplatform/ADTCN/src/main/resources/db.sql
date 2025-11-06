-- phpMyAdmin SQL Dump
-- version 5.2.3
-- https://www.phpmyadmin.net/
--
-- Hôte : db:3306
-- Généré le : mer. 05 nov. 2025 à 17:23
-- Version du serveur : 9.5.0
-- Version de PHP : 8.3.27

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `citymesh`
--
CREATE DATABASE IF NOT EXISTS `citymesh` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE `citymesh`;

-- --------------------------------------------------------

--
-- Structure de la table `checkpoints`
--

CREATE TABLE `checkpoints` (
  `id` int NOT NULL,
  `flight_id` int DEFAULT NULL,
  `latitude` decimal(10,7) NOT NULL,
  `longitude` decimal(10,7) NOT NULL,
  `created_by` int DEFAULT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `checkpoints`
--

INSERT INTO `checkpoints` (`id`, `flight_id`, `latitude`, `longitude`, `created_by`, `created_at`) VALUES
(1, 1, 50.8510000, 4.3490000, 1, '2025-11-05 14:14:26');

-- --------------------------------------------------------

--
-- Structure de la table `drones`
--

CREATE TABLE `drones` (
  `id` int NOT NULL,
  `name` varchar(150) NOT NULL,
  `model` varchar(150) DEFAULT NULL,
  `status` enum('Vliegklaar','In Onderhoud','In Gebruik','Wacht op Verzending') NOT NULL DEFAULT 'Vliegklaar',
  `battery_level` tinyint UNSIGNED DEFAULT '100',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `drones`
--

INSERT INTO `drones` (`id`, `name`, `model`, `status`, `battery_level`, `created_at`) VALUES
(1, 'Drone A', 'DJI X1', 'Vliegklaar', 100, '2025-11-05 14:14:26'),
(2, 'Drone B', 'Parrot Anafi', 'In Onderhoud', 60, '2025-11-05 14:14:26'),
(3, 'Drone C', 'DJI Mavic', 'In Gebruik', 45, '2025-11-05 14:14:26');

-- --------------------------------------------------------

--
-- Structure de la table `flights`
--

CREATE TABLE `flights` (
  `id` int NOT NULL,
  `user_id` int DEFAULT NULL,
  `drone_id` int DEFAULT NULL,
  `start_time` datetime NOT NULL,
  `end_time` datetime DEFAULT NULL,
  `route` text,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `flights`
--

INSERT INTO `flights` (`id`, `user_id`, `drone_id`, `start_time`, `end_time`, `route`, `created_at`) VALUES
(1, 1, 1, '2025-11-05 12:14:26', '2025-11-05 13:14:26', NULL, '2025-11-05 14:14:26');

-- --------------------------------------------------------

--
-- Structure de la table `launchpads`
--

CREATE TABLE `launchpads` (
  `id` int NOT NULL,
  `name` varchar(150) NOT NULL,
  `latitude` decimal(10,7) NOT NULL,
  `longitude` decimal(10,7) NOT NULL,
  `is_safe` tinyint(1) NOT NULL DEFAULT '1',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `launchpads`
--

INSERT INTO `launchpads` (`id`, `name`, `latitude`, `longitude`, `is_safe`, `created_at`) VALUES
(1, 'LP Noord', 50.8550000, 4.3500000, 1, '2025-11-05 14:14:26'),
(2, 'LP Zuid', 50.8450000, 4.3600000, 0, '2025-11-05 14:14:26');

-- --------------------------------------------------------

--
-- Structure de la table `maintenance_logs`
--

CREATE TABLE `maintenance_logs` (
  `id` int NOT NULL,
  `drone_id` int NOT NULL,
  `mechanic_id` int DEFAULT NULL,
  `date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `type` varchar(150) NOT NULL,
  `description` text,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `maintenance_logs`
--

INSERT INTO `maintenance_logs` (`id`, `drone_id`, `mechanic_id`, `date`, `type`, `description`, `created_at`) VALUES
(1, 2, 2, '2025-11-05 14:14:26', 'Reparatie', 'Vervangen propeller', '2025-11-05 14:14:26');

-- --------------------------------------------------------

--
-- Structure de la table `no_fly_zones`
--

CREATE TABLE `no_fly_zones` (
  `id` int NOT NULL,
  `name` varchar(150) NOT NULL,
  `latitude` decimal(10,7) NOT NULL,
  `longitude` decimal(10,7) NOT NULL,
  `radius_m` int NOT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `no_fly_zones`
--

INSERT INTO `no_fly_zones` (`id`, `name`, `latitude`, `longitude`, `radius_m`, `created_at`) VALUES
(1, 'Stadscentrum NFZ', 50.8504500, 4.3487800, 200, '2025-11-05 14:14:26');

-- --------------------------------------------------------

--
-- Structure de la table `reservations`
--

CREATE TABLE `reservations` (
  `id` int NOT NULL,
  `user_id` int NOT NULL,
  `launchpad_id` int NOT NULL,
  `start_time` datetime NOT NULL,
  `end_time` datetime DEFAULT NULL,
  `status` varchar(50) NOT NULL DEFAULT 'CONFIRMED',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `reservations`
--

INSERT INTO `reservations` (`id`, `user_id`, `launchpad_id`, `start_time`, `end_time`, `status`, `created_at`) VALUES
(1, 1, 1, '2025-11-05 14:14:26', '2025-11-05 15:14:26', 'CONFIRMED', '2025-11-05 14:14:26');

-- --------------------------------------------------------

--
-- Structure de la table `roles`
--

CREATE TABLE `roles` (
  `id` int NOT NULL,
  `name` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `roles`
--

INSERT INTO `roles` (`id`, `name`) VALUES
(3, 'Administrator'),
(2, 'Mechanieker'),
(1, 'Piloot');

-- --------------------------------------------------------

--
-- Structure de la table `users`
--

CREATE TABLE `users` (
  `id` int NOT NULL,
  `username` varchar(100) NOT NULL,
  `password` varchar(255) NOT NULL,
  `fullname` varchar(200) DEFAULT NULL,
  `email` varchar(200) DEFAULT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `users`
--

INSERT INTO `users` (`id`, `username`, `password`, `fullname`, `email`, `created_at`) VALUES
(1, 'pilot1', 'pilot1pass', 'Piloot Een', 'pilot1@example.com', '2025-11-05 14:14:26'),
(2, 'mech1', 'mech1pass', 'Mechanieker Een', 'mech1@example.com', '2025-11-05 14:14:26'),
(3, 'admin', 'adminpass', 'Admin User', 'admin@example.com', '2025-11-05 14:14:26');

-- --------------------------------------------------------

--
-- Structure de la table `user_roles`
--

CREATE TABLE `user_roles` (
  `user_id` int NOT NULL,
  `role_id` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `user_roles`
--

INSERT INTO `user_roles` (`user_id`, `role_id`) VALUES
(1, 1),
(2, 2),
(3, 3);

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `checkpoints`
--
ALTER TABLE `checkpoints`
  ADD PRIMARY KEY (`id`),
  ADD KEY `flight_id` (`flight_id`),
  ADD KEY `created_by` (`created_by`);

--
-- Index pour la table `drones`
--
ALTER TABLE `drones`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idx_drones_status` (`status`);

--
-- Index pour la table `flights`
--
ALTER TABLE `flights`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `drone_id` (`drone_id`);

--
-- Index pour la table `launchpads`
--
ALTER TABLE `launchpads`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idx_launchpads_safe` (`is_safe`);

--
-- Index pour la table `maintenance_logs`
--
ALTER TABLE `maintenance_logs`
  ADD PRIMARY KEY (`id`),
  ADD KEY `drone_id` (`drone_id`),
  ADD KEY `mechanic_id` (`mechanic_id`);

--
-- Index pour la table `no_fly_zones`
--
ALTER TABLE `no_fly_zones`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `reservations`
--
ALTER TABLE `reservations`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `launchpad_id` (`launchpad_id`);

--
-- Index pour la table `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `name` (`name`);

--
-- Index pour la table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`),
  ADD KEY `idx_users_username` (`username`);

--
-- Index pour la table `user_roles`
--
ALTER TABLE `user_roles`
  ADD PRIMARY KEY (`user_id`,`role_id`),
  ADD KEY `role_id` (`role_id`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `checkpoints`
--
ALTER TABLE `checkpoints`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT pour la table `drones`
--
ALTER TABLE `drones`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT pour la table `flights`
--
ALTER TABLE `flights`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT pour la table `launchpads`
--
ALTER TABLE `launchpads`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT pour la table `maintenance_logs`
--
ALTER TABLE `maintenance_logs`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT pour la table `no_fly_zones`
--
ALTER TABLE `no_fly_zones`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT pour la table `reservations`
--
ALTER TABLE `reservations`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT pour la table `roles`
--
ALTER TABLE `roles`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT pour la table `users`
--
ALTER TABLE `users`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `checkpoints`
--
ALTER TABLE `checkpoints`
  ADD CONSTRAINT `checkpoints_ibfk_1` FOREIGN KEY (`flight_id`) REFERENCES `flights` (`id`) ON DELETE SET NULL,
  ADD CONSTRAINT `checkpoints_ibfk_2` FOREIGN KEY (`created_by`) REFERENCES `users` (`id`) ON DELETE SET NULL;

--
-- Contraintes pour la table `flights`
--
ALTER TABLE `flights`
  ADD CONSTRAINT `flights_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE SET NULL,
  ADD CONSTRAINT `flights_ibfk_2` FOREIGN KEY (`drone_id`) REFERENCES `drones` (`id`) ON DELETE SET NULL;

--
-- Contraintes pour la table `maintenance_logs`
--
ALTER TABLE `maintenance_logs`
  ADD CONSTRAINT `maintenance_logs_ibfk_1` FOREIGN KEY (`drone_id`) REFERENCES `drones` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `maintenance_logs_ibfk_2` FOREIGN KEY (`mechanic_id`) REFERENCES `users` (`id`) ON DELETE SET NULL;

--
-- Contraintes pour la table `reservations`
--
ALTER TABLE `reservations`
  ADD CONSTRAINT `reservations_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `reservations_ibfk_2` FOREIGN KEY (`launchpad_id`) REFERENCES `launchpads` (`id`) ON DELETE CASCADE;

--
-- Contraintes pour la table `user_roles`
--
ALTER TABLE `user_roles`
  ADD CONSTRAINT `user_roles_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `user_roles_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

