package dev.fredpena.dcm.service;

import dev.fredpena.dcm.data.Tenant;
import dev.fredpena.dcm.data.TenantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author me@fredpena.dev
 * @created 13/07/2025  - 23:47
 */
@Service
@RequiredArgsConstructor
public class TenantService {

    private final TenantRepository repository;

    @Transactional(readOnly = true)
    public List<Tenant> findAll() {
        return repository.findAll();
    }
}