-- U0.0.3__revert_department_table.sql
-- Este es un script de "Undo" (reversión). Su propósito es deshacer
-- los cambios realizados por la migración V3__add_department_table.sql.
-- Las operaciones se realizan en el orden inverso a la creación.

-- 1. Eliminar la clave foránea (FOREIGN KEY) de la tabla 'person'.
-- Usamos 'IF EXISTS' para que el script no falle si la restricción ya fue eliminada.
ALTER TABLE person
DROP
CONSTRAINT IF EXISTS fk_person_department;

-- 2. Eliminar la columna 'department_id' de la tabla 'person'.
ALTER TABLE person
DROP
COLUMN IF EXISTS department_id;

-- 3. Finalmente, eliminar la tabla 'department'.
DROP TABLE IF EXISTS department;