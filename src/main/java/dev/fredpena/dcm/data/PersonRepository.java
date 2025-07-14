package dev.fredpena.dcm.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author me@fredpena.dev
 * @created 13/07/2025  - 13:32
 */


public interface PersonRepository extends JpaRepository<Person, Long>, JpaSpecificationExecutor<Person> {
}