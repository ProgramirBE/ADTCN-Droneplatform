-- data.sql for Spring Boot automatic data load

INSERT IGNORE INTO roles (name) VALUES ('Piloot'), ('Mechanieker'), ('Administrator');

INSERT IGNORE INTO users (username, password, fullname, email) VALUES
('pilot1', 'pilot1pass', 'Piloot Een', 'pilot1@example.com'),
('mech1', 'mech1pass', 'Mechanieker Een', 'mech1@example.com'),
('admin', 'adminpass', 'Admin User', 'admin@example.com');

INSERT IGNORE INTO user_roles (user_id, role_id)
SELECT u.id, r.id FROM users u JOIN roles r ON (
  (u.username='pilot1' AND r.name='Piloot') OR
  (u.username='mech1' AND r.name='Mechanieker') OR
  (u.username='admin' AND r.name='Administrator')
);

INSERT IGNORE INTO drones (name, model, status, battery_level) VALUES
('Drone A', 'DJI X1', 'Vliegklaar', 100),
('Drone B', 'Parrot Anafi', 'In Onderhoud', 60),
('Drone C', 'DJI Mavic', 'In Gebruik', 45);

INSERT IGNORE INTO no_fly_zones (name, latitude, longitude, radius_m) VALUES
('Stadscentrum NFZ', 50.85045, 4.34878, 200);

INSERT IGNORE INTO launchpads (name, latitude, longitude, is_safe) VALUES
('LP Noord', 50.855, 4.35, 1),
('LP Zuid', 50.845, 4.36, 0);

INSERT IGNORE INTO reservations (user_id, launchpad_id, start_time, end_time, status)
SELECT u.id, l.id, NOW(), DATE_ADD(NOW(), INTERVAL 1 HOUR), 'CONFIRMED'
FROM users u JOIN launchpads l ON l.name='LP Noord' WHERE u.username='pilot1';

INSERT IGNORE INTO flights (user_id, drone_id, start_time, end_time, route)
SELECT u.id, d.id, NOW()-INTERVAL 2 HOUR, NOW()-INTERVAL 1 HOUR, NULL
FROM users u JOIN drones d ON u.username='pilot1' AND d.name='Drone A';

INSERT IGNORE INTO checkpoints (flight_id, latitude, longitude, created_by)
SELECT f.id, 50.851, 4.349, u.id FROM flights f JOIN users u ON u.username='pilot1' LIMIT 1;

INSERT IGNORE INTO maintenance_logs (drone_id, mechanic_id, type, description)
SELECT d.id, u.id, 'Reparatie', 'Vervangen propeller' FROM drones d JOIN users u ON u.username='mech1' WHERE d.name='Drone B';

