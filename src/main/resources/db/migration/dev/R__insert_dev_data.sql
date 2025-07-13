-- Este es un script de migración REPETIBLE (comienza con R__)
-- Se ejecutará cada vez que su contenido (checksum) cambie.
-- Es ideal para gestionar datos de prueba en desarrollo.

-- Borramos los datos existentes para asegurar un estado limpio en cada ejecución.
DELETE
FROM person;

-- Insertamos datos de prueba.
INSERT INTO person (first_name, last_name, email, address, phone_number, birth_date)
VALUES ('John', 'Doe', 'john.doe@example.com', '123 Main St', '555-0101', '1990-05-15'),
       ('Jane', 'Smith', 'jane.smith@example.com', '456 Oak Ave', '555-0102', '1988-11-22'),
       ('Peter', 'Jones', 'peter.jones@example.com', '789 Pine Ln', '555-0103', '1995-02-10');
