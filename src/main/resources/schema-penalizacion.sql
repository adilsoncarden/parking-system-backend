-- Columnas de penalización y control (idempotente PostgreSQL)
ALTER TABLE log_prestamo_carrito ADD COLUMN IF NOT EXISTS fecha_inicio TIMESTAMP;
ALTER TABLE log_prestamo_carrito ADD COLUMN IF NOT EXISTS fecha_fin TIMESTAMP;
ALTER TABLE log_prestamo_carrito ADD COLUMN IF NOT EXISTS tiempo_limite_minutos INTEGER DEFAULT 15;
ALTER TABLE log_prestamo_carrito ADD COLUMN IF NOT EXISTS tiempo_excedido_minutos INTEGER DEFAULT 0;
ALTER TABLE log_prestamo_carrito ADD COLUMN IF NOT EXISTS monto_penalizacion NUMERIC(12, 2) DEFAULT 0;
ALTER TABLE log_prestamo_carrito ADD COLUMN IF NOT EXISTS penalizado BOOLEAN DEFAULT FALSE;
ALTER TABLE log_prestamo_carrito ADD COLUMN IF NOT EXISTS pagado BOOLEAN DEFAULT TRUE;

UPDATE log_prestamo_carrito SET fecha_inicio = fecha_prestamo WHERE fecha_inicio IS NULL;
UPDATE log_prestamo_carrito SET tiempo_limite_minutos = 15 WHERE tiempo_limite_minutos IS NULL;
UPDATE log_prestamo_carrito SET tiempo_excedido_minutos = 0 WHERE tiempo_excedido_minutos IS NULL;
UPDATE log_prestamo_carrito SET monto_penalizacion = 0 WHERE monto_penalizacion IS NULL;
UPDATE log_prestamo_carrito SET penalizado = FALSE WHERE penalizado IS NULL;
UPDATE log_prestamo_carrito SET pagado = TRUE WHERE pagado IS NULL;
