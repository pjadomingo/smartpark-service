-- Parking Lots
INSERT INTO parking_lot (id, lot_id, location, capacity, occupied_spaces, cost_per_minute)
VALUES (1, 'LOT-A1', 'Makati CBD', 50, 0, 5);

INSERT INTO parking_lot (id, lot_id, location, capacity, occupied_spaces, cost_per_minute)
VALUES (2, 'LOT-A2', 'BGC Taguig', 80, 0, 6);

INSERT INTO parking_lot (id, lot_id, location, capacity, occupied_spaces, cost_per_minute)
VALUES (3, 'LOT-B1', 'Ortigas Center', 100, 0, 4);

INSERT INTO parking_lot (id, lot_id, location, capacity, occupied_spaces, cost_per_minute)
VALUES (4, 'LOT-B2', 'Quezon City', 60, 0, 3);

INSERT INTO parking_lot (id, lot_id, location, capacity, occupied_spaces, cost_per_minute)
VALUES (5, 'LOT-C1', 'Manila Bay Area', 120, 0, 7);

INSERT INTO parking_lot (id, lot_id, location, capacity, occupied_spaces, cost_per_minute)
VALUES (6, 'LOT-C2', 'Pasig Riverside', 40, 0, 2);

INSERT INTO parking_lot (id, lot_id, location, capacity, occupied_spaces, cost_per_minute)
VALUES (7, 'LOT-D1', 'Alabang Town Center', 75, 0, 5);

INSERT INTO parking_lot (id, lot_id, location, capacity, occupied_spaces, cost_per_minute)
VALUES (8, 'LOT-D2', 'Greenhills San Juan', 90, 0, 4);

INSERT INTO parking_lot (id, lot_id, location, capacity, occupied_spaces, cost_per_minute)
VALUES (9, 'LOT-E1', 'Eastwood Libis', 65, 0, 6);

INSERT INTO parking_lot (id, lot_id, location, capacity, occupied_spaces, cost_per_minute)
VALUES (10, 'LOT-E2', 'SM Mall of Asia', 150, 0, 8);

-- Vehicles
INSERT INTO vehicle (id, license_plate, owner_name, type)
VALUES (1, 'ABC-1234', 'Juan Dela Cruz', 'Car');

INSERT INTO vehicle (id, license_plate, owner_name, type)
VALUES (2, 'XYZ-5678', 'Maria Santos', 'Car');

INSERT INTO vehicle (id, license_plate, owner_name, type)
VALUES (3, 'JKL-9012', 'Pedro Reyes', 'Motorcycle');

INSERT INTO vehicle (id, license_plate, owner_name, type)
VALUES (4, 'DEF-3456', 'Ana Lopez', 'Car');

INSERT INTO vehicle (id, license_plate, owner_name, type)
VALUES (5, 'GHI-7890', 'Carlos Garcia', 'Truck');

INSERT INTO vehicle (id, license_plate, owner_name, type)
VALUES (6, 'MNO-1122', 'Jose Ramos', 'Car');

INSERT INTO vehicle (id, license_plate, owner_name, type)
VALUES (7, 'PQR-3344', 'Liza Cruz', 'Motorcycle');

INSERT INTO vehicle (id, license_plate, owner_name, type)
VALUES (8, 'STU-5566', 'Mark Villanueva', 'Car');

INSERT INTO vehicle (id, license_plate, owner_name, type)
VALUES (9, 'VWX-7788', 'Grace Bautista', 'Car');

INSERT INTO vehicle (id, license_plate, owner_name, type)
VALUES (10, 'YZA-9900', 'Rico Mendoza', 'Truck');
