CREATE TABLE start_place (
                             id VARCHAR(20) PRIMARY KEY,
                             status ENUM('available', 'reserved') NOT NULL DEFAULT 'available',
                             distance_meters INT NOT NULL,
                             created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO start_place (id, status, distance_meters) VALUES
                                                          ('SP-101', 'available', 60),
                                                          ('SP-102', 'reserved', 55),
                                                          ('SP-201', 'available', 50),
                                                          ('SP-202', 'reserved', 70),
                                                          ('SP-301', 'available', 80),
                                                          ('SP-399', 'reserved', 100),
                                                          ('SP-401', 'available', 120),
                                                          ('SP-498', 'reserved', 90),
                                                          ('SP-910', 'available', 30),
                                                          ('SP-911', 'available', 10),
                                                          ('SP-912', 'available', 49);
