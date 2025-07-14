package dev.fredpena.dcm.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import dev.fredpena.dcm.data.Tenant;
import dev.fredpena.dcm.service.TenantService;
import dev.fredpena.dcm.views.person.PersonView;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

/**
 * @author me@fredpena.dev
 * @created 13/07/2025  - 23:48
 */
@PageTitle("Tenant")
@Route("select-tenant")
@RouteAlias("")
@AnonymousAllowed
@Menu(order = 0, icon = LineAwesomeIconUrl.HOME_SOLID)
public class TenantSelectionView extends VerticalLayout {

    public TenantSelectionView(TenantService tenantService) {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        H2 title = new H2("Seleccione su Tenant");
        Span subtitle = new Span("Elija el contexto en el que desea trabajar.");

        ComboBox<Tenant> tenantComboBox = new ComboBox<>("Tenants Disponibles");
        tenantComboBox.setItems(tenantService.findAll());
        tenantComboBox.setItemLabelGenerator(Tenant::getTenantName);
        tenantComboBox.setWidth("300px");

        Button continueButton = new Button("Continuar", event -> {
            Tenant selectedTenant = tenantComboBox.getValue();
            if (selectedTenant != null) {
                // Guardamos el tenant seleccionado en la sesión de Vaadin
                VaadinSession.getCurrent().setAttribute(Tenant.class, selectedTenant);
                // Redirigimos a la vista principal de la aplicación
                UI.getCurrent().navigate(PersonView.class);
            }
        });
        continueButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        continueButton.setEnabled(false); // Deshabilitado hasta que se elija un tenant

        // Habilitar el botón solo cuando se selecciona un tenant
        tenantComboBox.addValueChangeListener(event -> {
            continueButton.setEnabled(event.getValue() != null);
        });

        add(title, subtitle, tenantComboBox, continueButton);
    }
}
