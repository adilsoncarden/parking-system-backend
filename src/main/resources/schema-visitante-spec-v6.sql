-- Soporte de vehículos de VISITANTE (spec V6): un carro de visitante no tiene dueño
-- residente, así que id_usuario deja de ser obligatorio. Idempotente (DROP NOT NULL
-- es no-op si ya está nullable).
ALTER TABLE vehiculo ALTER COLUMN id_usuario DROP NOT NULL;
