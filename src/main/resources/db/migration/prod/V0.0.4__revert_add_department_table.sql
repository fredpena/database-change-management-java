-- V0.0.4__revert_add_department_table.sql
-- Este script revierte los cambios hechos en la migración V3,
-- siguiendo la estrategia de "rollback simulado".

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