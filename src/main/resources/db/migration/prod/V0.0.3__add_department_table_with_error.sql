-- V0.0.3__add_department_table_with_error.sql
-- Esta migración contiene un error de sintaxis deliberado para simular un fallo.

CREATE TABLE department
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHARR(100) NOT NULL -- ¡ERROR! 'VARCHARR' no es un tipo de dato válido.
);

-- También añadimos una columna a la tabla 'person' para la clave foránea.
ALTER TABLE person
    ADD COLUMN department_id BIGINT;

ALTER TABLE person
    ADD CONSTRAINT fk_person_department
        FOREIGN KEY (department_id) REFERENCES department (id);
