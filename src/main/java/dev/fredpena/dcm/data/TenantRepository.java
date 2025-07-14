package dev.fredpena.dcm.data;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author me@fredpena.dev
 * @created 13/07/2025  - 23:47
 */
public interface TenantRepository extends JpaRepository<Tenant, Long> {
}