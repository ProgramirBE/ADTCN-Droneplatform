FROM users u JOIN launchpads l ON l.name='LP Noord' WHERE u.username='pilot1';

-- Sample flights & checkpoints
INSERT IGNORE INTO flights (user_id, drone_id, start_time, end_time, route)
SELECT u.id, d.id, NOW()-INTERVAL 2 HOUR, NOW()-INTERVAL 1 HOUR, NULL
FROM users u JOIN drones d ON u.username='pilot1' AND d.name='Drone A';

INSERT IGNORE INTO checkpoints (flight_id, latitude, longitude, created_by)
SELECT f.id, 50.851, 4.349, u.id FROM flights f JOIN users u ON u.username='pilot1' LIMIT 1;

-- Sample maintenance log
INSERT IGNORE INTO maintenance_logs (drone_id, mechanic_id, type, description)
SELECT d.id, u.id, 'Reparatie', 'Vervangen propeller' FROM drones d JOIN users u ON u.username='mech1' WHERE d.name='Drone B';

-- End of init.sql
-- init.sql: initialisation automatique pour MySQL (utilisable via /docker-entrypoint-initdb.d)

CREATE DATABASE IF NOT EXISTS `citymesh` CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE `citymesh`;

-- Schema
-- Roles
CREATE TABLE IF NOT EXISTS roles (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(50) NOT NULL UNIQUE
) ENGINE=InnoDB;

-- Users
CREATE TABLE IF NOT EXISTS users (
  id INT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(100) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  fullname VARCHAR(200),
  email VARCHAR(200),
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- User roles
CREATE TABLE IF NOT EXISTS user_roles (
  user_id INT NOT NULL,
  role_id INT NOT NULL,
  PRIMARY KEY (user_id, role_id),
  FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
  FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- Drones
CREATE TABLE IF NOT EXISTS drones (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(150) NOT NULL,
  model VARCHAR(150),
  status ENUM('Vliegklaar','In Onderhoud','In Gebruik','Wacht op Verzending') NOT NULL DEFAULT 'Vliegklaar',
  battery_level TINYINT UNSIGNED DEFAULT 100,
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- Launchpads (startplaatsen)
CREATE TABLE IF NOT EXISTS launchpads (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(150) NOT NULL,
  latitude DECIMAL(10,7) NOT NULL,
  longitude DECIMAL(10,7) NOT NULL,
  is_safe TINYINT(1) NOT NULL DEFAULT 1,
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- No-fly zones
CREATE TABLE IF NOT EXISTS no_fly_zones (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(150) NOT NULL,
  latitude DECIMAL(10,7) NOT NULL,
  longitude DECIMAL(10,7) NOT NULL,
  radius_m INT NOT NULL,
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- Reservations for launchpads
CREATE TABLE IF NOT EXISTS reservations (
  id INT AUTO_INCREMENT PRIMARY KEY,
  user_id INT NOT NULL,
  launchpad_id INT NOT NULL,
  start_time DATETIME NOT NULL,
  end_time DATETIME NULL,
  status VARCHAR(50) NOT NULL DEFAULT 'CONFIRMED',
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
  FOREIGN KEY (launchpad_id) REFERENCES launchpads(id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- Flights
CREATE TABLE IF NOT EXISTS flights (
  id INT AUTO_INCREMENT PRIMARY KEY,
  user_id INT NOT NULL,
  drone_id INT NOT NULL,
  start_time DATETIME NOT NULL,
  end_time DATETIME NULL,
  route TEXT NULL, -- GeoJSON or encoded route
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE SET NULL,
  FOREIGN KEY (drone_id) REFERENCES drones(id) ON DELETE SET NULL
) ENGINE=InnoDB;

-- Checkpoints (manually placed pins)
CREATE TABLE IF NOT EXISTS checkpoints (
  id INT AUTO_INCREMENT PRIMARY KEY,
  flight_id INT NULL,
  latitude DECIMAL(10,7) NOT NULL,
  longitude DECIMAL(10,7) NOT NULL,
  created_by INT NULL,
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (flight_id) REFERENCES flights(id) ON DELETE SET NULL,
  FOREIGN KEY (created_by) REFERENCES users(id) ON DELETE SET NULL
) ENGINE=InnoDB;

-- Maintenance log
CREATE TABLE IF NOT EXISTS maintenance_logs (
  id INT AUTO_INCREMENT PRIMARY KEY,
  drone_id INT NOT NULL,
  mechanic_id INT NULL,
  date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  type VARCHAR(150) NOT NULL,
  description TEXT,
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (drone_id) REFERENCES drones(id) ON DELETE CASCADE,
  FOREIGN KEY (mechanic_id) REFERENCES users(id) ON DELETE SET NULL
) ENGINE=InnoDB;

-- Indexes
CREATE INDEX idx_users_username ON users(username);
CREATE INDEX idx_drones_status ON drones(status);
CREATE INDEX idx_launchpads_safe ON launchpads(is_safe);

-- Sample data (minimal)
INSERT IGNORE INTO roles (name) VALUES ('Piloot'), ('Mechanieker'), ('Administrator');

INSERT IGNORE INTO users (username, password, fullname, email) VALUES
('pilot1', 'pilot1pass', 'Piloot Een', 'pilot1@example.com'),
('mech1', 'mech1pass', 'Mechanieker Een', 'mech1@example.com'),
('admin', 'adminpass', 'Admin User', 'admin@example.com');

-- Assign roles
INSERT IGNORE INTO user_roles (user_id, role_id)
SELECT u.id, r.id FROM users u JOIN roles r ON (
  (u.username='pilot1' AND r.name='Piloot') OR
  (u.username='mech1' AND r.name='Mechanieker') OR
  (u.username='admin' AND r.name='Administrator')
);

-- Sample drones
INSERT IGNORE INTO drones (name, model, status, battery_level) VALUES
('Drone A', 'DJI X1', 'Vliegklaar', 100),
('Drone B', 'Parrot Anafi', 'In Onderhoud', 60),
('Drone C', 'DJI Mavic', 'In Gebruik', 45);

-- Sample no-fly zone
INSERT IGNORE INTO no_fly_zones (name, latitude, longitude, radius_m) VALUES
('Stadscentrum NFZ', 50.85045, 4.34878, 200);

-- Sample launchpads (is_safe calculé manuellement ici)
INSERT IGNORE INTO launchpads (name, latitude, longitude, is_safe) VALUES
('LP Noord', 50.855, 4.35, 1),
('LP Zuid', 50.845, 4.36, 0);

-- Sample reservations
INSERT IGNORE INTO reservations (user_id, launchpad_id, start_time, end_time, status)
SELECT u.id, l.id, NOW(), DATE_ADD(NOW(), INTERVAL 1 HOUR), 'CONFIRMED'

