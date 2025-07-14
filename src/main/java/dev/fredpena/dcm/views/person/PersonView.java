package dev.fredpena.dcm.views.person;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import dev.fredpena.dcm.data.Person;
import dev.fredpena.dcm.service.PersonService;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

import java.util.Optional;

@PageTitle("Persona")
@Route("person/:personID?/:action?(edit)")
@AnonymousAllowed
@Menu(order = 1, icon = LineAwesomeIconUrl.USER_SOLID)
public class PersonView extends Div implements BeforeEnterObserver {

    private static final String PERSON_ID = "personID";
    private static final String PERSON_EDIT_ROUTE_TEMPLATE = "person/%s/edit";

    private final Grid<Person> grid = new Grid<>(Person.class, false);
    private final PersonForm form;
    private final PersonService personService;

    public PersonView(PersonService personService) {
        this.personService = personService;
        addClassNames("person-view");

        // El formulario ahora es un componente separado
        form = new PersonForm();
        form.setVisible(false); // Oculto por defecto
        configureFormListeners();

        // Creamos una barra de herramientas para los botones de acción (ej. "New Person")
        HorizontalLayout toolbar = createToolbar();

        // Creamos el layout principal
        SplitLayout splitLayout = createSplitLayout();
        createGridLayout(splitLayout, toolbar);
        splitLayout.addToSecondary(form); // Añadimos el formulario al panel secundario

        add(splitLayout);

        configureGrid();
    }

    private HorizontalLayout createToolbar() {
        Button newPersonButton = new Button("New Person");
        newPersonButton.addClickListener(click -> createNewPerson());
        return new HorizontalLayout(newPersonButton);
    }

    private void createNewPerson() {
        // Limpiamos cualquier selección en la tabla para evitar confusiones
        grid.asSingleSelect().clear();
        // Abrimos el formulario con un objeto Person nuevo y vacío
        populateForm(new Person());
        // Navegamos a la URL base para limpiar cualquier ID de la ruta
        UI.getCurrent().navigate(PersonView.class);
    }

    private void configureFormListeners() {
        form.addListener(PersonForm.SaveEvent.class, this::savePerson);
        form.addListener(PersonForm.CancelEvent.class, e -> closeEditor());
    }

    private void configureGrid() {
        grid.addColumn("firstName").setAutoWidth(true);
        grid.addColumn("lastName").setAutoWidth(true);
        grid.addColumn("email").setAutoWidth(true);
        grid.addColumn("address").setAutoWidth(true);
        grid.addColumn("phoneNumber").setAutoWidth(true);
        grid.addColumn("birthDate").setAutoWidth(true);

        grid.setItems(query -> personService.list(VaadinSpringDataHelpers.toSpringPageRequest(query)).stream());
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);

        // Navega a la URL de edición cuando se selecciona una fila
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                UI.getCurrent().navigate(String.format(PERSON_EDIT_ROUTE_TEMPLATE, event.getValue().getId()));
            } else {
                closeEditor();
            }
        });
    }

    private SplitLayout createSplitLayout() {
        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();
        return splitLayout;
    }

    private void createGridLayout(SplitLayout splitLayout, HorizontalLayout toolbar) {
        // Usamos un VerticalLayout para apilar la barra de herramientas y la tabla
        VerticalLayout gridLayout = new VerticalLayout(toolbar, grid);
        gridLayout.setPadding(false);
        gridLayout.setSpacing(false);
        gridLayout.setSizeFull();

        Div wrapper = new Div(gridLayout);
        wrapper.setClassName("grid-wrapper");
        wrapper.setSizeFull();
        splitLayout.addToPrimary(wrapper);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<Long> personId = event.getRouteParameters().get(PERSON_ID).map(Long::parseLong);
        if (personId.isPresent()) {
            Optional<Person> personFromBackend = personService.get(personId.get());
            if (personFromBackend.isPresent()) {
                populateForm(personFromBackend.get());
            } else {
                Notification.show(String.format("The requested person was not found, ID = %s", personId.get()), 3000,
                        Position.BOTTOM_START);
                refreshGrid();
                event.forwardTo(PersonView.class);
            }
        }
    }

    private void savePerson(PersonForm.SaveEvent event) {
        try {
            personService.save(event.getPerson());
            // Cerramos el editor y refrescamos la tabla después de guardar
            closeEditor();
            refreshGrid();
            Notification.show("Person details stored.", 2000, Position.BOTTOM_START);
            UI.getCurrent().navigate(PersonView.class);
        } catch (ObjectOptimisticLockingFailureException exception) {
            Notification n = Notification.show(
                    "Error updating the data. Somebody else has updated the record while you were making changes.");
            n.setPosition(Position.MIDDLE);
            n.addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
    }

    private void closeEditor() {
        form.setPerson(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void populateForm(Person person) {
        // Este método ahora maneja tanto la edición de una persona existente
        // como la creación de una nueva (cuando 'person' es un objeto nuevo).
        form.setPerson(person);
        form.setVisible(true);
        addClassName("editing");
    }

    private void refreshGrid() {
        grid.select(null);
        grid.getDataProvider().refreshAll();
    }
}