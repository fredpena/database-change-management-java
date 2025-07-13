-- Evolucionamos la tabla 'person' añadiendo campos para dirección y contacto.
-- Usamos ALTER TABLE para modificar una tabla existente sin borrarla.

ALTER TABLE person
    ADD COLUMN address VARCHAR(255),
    ADD COLUMN phone_number VARCHAR(20),
    ADD COLUMN birth_date DATE;