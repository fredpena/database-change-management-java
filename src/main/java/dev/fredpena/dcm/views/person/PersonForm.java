package dev.fredpena.dcm.views.person;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.theme.lumo.Lumo;
import com.vaadin.flow.theme.lumo.LumoUtility;
import dev.fredpena.dcm.data.Person;
import lombok.Getter;

public class PersonForm extends Div {

    private final TextField firstName = new TextField("First Name");
    private final TextField lastName = new TextField("Last Name");
    private final TextField email = new TextField("Email");
    private final TextField address = new TextField("Address");
    private final TextField phoneNumber = new TextField("Phone Number");
    private final DatePicker birthDate = new DatePicker("Birth Date");

    private final Button save = new Button("Save");
    private final Button cancel = new Button("Cancel");

    private final BeanValidationBinder<Person> binder = new BeanValidationBinder<>(Person.class);
    private Person currentPerson;

    public PersonForm() {
        setClassName("editor-layout");
        binder.bindInstanceFields(this);

        // 1. Añadimos el nuevo encabezado en la parte superior
        add(createHeader());

        // 2. Creamos un contenedor para los campos del formulario con su padding
        FormLayout formLayout = new FormLayout();
        formLayout.add(firstName, lastName, email, address, phoneNumber, birthDate);

        Div editorDiv = new Div(formLayout);
        editorDiv.setClassName("editor"); // La clase 'editor' añade el padding desde el CSS
        add(editorDiv);

        // 3. Añadimos el layout de los botones al final
        add(createButtonsLayout());
    }

    /**
     * Crea el componente del encabezado con tema oscuro.
     */
    private Header createHeader() {
        H3 title = new H3("Detalles de la Persona");
        Span subtitle = new Span("Edita la información de la persona seleccionada.");

        title.addClassNames(LumoUtility.Margin.NONE, LumoUtility.FontSize.LARGE);
        subtitle.addClassNames(LumoUtility.TextColor.SECONDARY, LumoUtility.FontSize.SMALL);

        Header header = new Header(title, subtitle);
        header.getElement().getThemeList().add(Lumo.DARK);
        header.addClassNames(LumoUtility.Display.FLEX, LumoUtility.FlexDirection.COLUMN,
                LumoUtility.Padding.MEDIUM, LumoUtility.Gap.Row.MEDIUM);


        return header;
    }

    private HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        cancel.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> validateAndSave());
        cancel.addClickListener(event -> fireEvent(new CancelEvent(this)));

        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));

        HorizontalLayout buttonLayout = new HorizontalLayout(save, cancel);
        buttonLayout.setClassName("button-layout");

        return buttonLayout;
    }

    private void validateAndSave() {
        try {
            binder.writeBean(currentPerson);
            fireEvent(new SaveEvent(this, currentPerson));
        } catch (ValidationException e) {
            // La validación del binder falló, los errores se mostrarán automáticamente.
        }
    }

    /**
     * Rellena el formulario con los datos de una persona.
     *
     * @param person La persona a editar, o null para limpiar el formulario.
     */
    public void setPerson(Person person) {
        this.currentPerson = person;
        binder.readBean(this.currentPerson);
    }

    // --- Sistema de Eventos Personalizados para desacoplar el formulario de la vista ---
    @Getter
    public static abstract class PersonFormEvent extends ComponentEvent<PersonForm> {
        private final Person person;

        protected PersonFormEvent(PersonForm source, Person person) {
            super(source, false);
            this.person = person;
        }

    }

    public static class SaveEvent extends PersonFormEvent {
        SaveEvent(PersonForm source, Person person) {
            super(source, person);
        }
    }

    public static class CancelEvent extends PersonFormEvent {
        CancelEvent(PersonForm source) {
            super(source, null);
        }
    }

    @Override
    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}