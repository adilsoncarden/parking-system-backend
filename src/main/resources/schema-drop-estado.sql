-- Elimina columna estado de Condominio, Torre y Piso
ALTER TABLE condominio DROP COLUMN IF EXISTS estado;
ALTER TABLE torre DROP COLUMN IF EXISTS estado;
ALTER TABLE piso DROP COLUMN IF EXISTS estado;
