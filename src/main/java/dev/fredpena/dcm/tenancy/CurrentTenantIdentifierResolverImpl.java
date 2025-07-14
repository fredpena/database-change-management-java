package dev.fredpena.dcm.tenancy;

import com.vaadin.flow.server.VaadinSession;
import dev.fredpena.dcm.data.Tenant;
import org.hibernate.cfg.MultiTenancySettings;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author me@fredpena.dev
 * @created 13/07/2025  - 11:55
 */

@Component
public class CurrentTenantIdentifierResolverImpl implements CurrentTenantIdentifierResolver<Long>, HibernatePropertiesCustomizer {

    private static final long DEFAULT_TENANT = 0L;

    @Override
    public Long resolveCurrentTenantIdentifier() {
        if (VaadinSession.getCurrent() != null) {
            Tenant tenant = VaadinSession.getCurrent().getAttribute(Tenant.class);
            return tenant != null && tenant.getTenantId() != null ? tenant.getTenantId() : DEFAULT_TENANT;
        }
        return DEFAULT_TENANT;
    }


    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }

    @Override
    public void customize(Map<String, Object> hibernateProperties) {
        hibernateProperties.put(MultiTenancySettings.MULTI_TENANT_IDENTIFIER_RESOLVER, this);
    }
}
