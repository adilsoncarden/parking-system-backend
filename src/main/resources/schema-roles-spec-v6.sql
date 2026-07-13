-- Alineación de nombres de rol con la Especificación Técnica V6 del profesor.
-- Idempotente: si ya fueron renombrados, el WHERE no afecta filas.
-- Conserva el mismo id_rol (solo cambia el nombre), así los usuarios existentes
-- mantienen su rol; el nuevo nombre aparece al volver a iniciar sesión.
UPDATE rol SET nombre = 'PROPIETARIO'      WHERE nombre = 'RESIDENTE';
UPDATE rol SET nombre = 'AGENTE_SEGURIDAD' WHERE nombre = 'PORTERO';
