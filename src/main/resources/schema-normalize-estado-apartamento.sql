UPDATE apartamento
SET estado = UPPER(TRIM(estado))
WHERE estado IS NOT NULL
  AND estado <> UPPER(TRIM(estado));
