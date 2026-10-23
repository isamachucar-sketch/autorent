-- =====================================================================
-- DATOS PREDETERMINADOS DE AUTORENT
-- H2 ejecuta este script automáticamente cada vez que arranca la app,
-- justo después de que Hibernate crea las tablas (ver application.properties).
-- No hace falta Postman: al abrir /h2-console los datos ya están ahí.
-- Son los mismos datos de la colección de Postman.
--
-- El id NO se escribe: lo genera la columna IDENTITY (quedan con id 1).
-- Así los POST que se hagan después reciben id 2, 3, ... sin chocar.
-- =====================================================================

-- Auto 1: Mazda CX-30. Queda ALQUILADO porque tiene el alquiler 1 activo (regla 2).
INSERT INTO autos (placa, marca, modelo, anio, precio_por_dia, deposito, estado)
VALUES ('KLM456', 'Mazda', 'CX-30', 2024, 180000, 600000, 'ALQUILADO');

-- Cliente 1: Laura Gómez
INSERT INTO clientes (nombre, documento, telefono, email)
VALUES ('Laura Gómez', '1043987654', '3157654321', 'laura.gomez@email.com');

-- Alquiler 1: Laura alquila el Mazda por 5 días.
--   valor_total              = 180000 x 5      = 900000   (regla 6)
--   valor_total_con_deposito = 900000 + 600000 = 1500000
--   fecha_inicio = hoy, fecha_fin = hoy + 5 días
INSERT INTO alquileres (cliente_id, auto_id, fecha_inicio, fecha_fin, cantidad_dias,
                        valor_total, valor_total_con_deposito, estado)
VALUES (1, 1, CURRENT_DATE, DATEADD('DAY', 5, CURRENT_DATE), 5,
        900000, 1500000, 'ACTIVO');
