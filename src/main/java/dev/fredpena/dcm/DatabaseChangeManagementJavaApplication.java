package dev.fredpena.dcm;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.theme.Theme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@PageTitle("My App")
@Theme(value = "my-app")
@SpringBootApplication
public class DatabaseChangeManagementJavaApplication implements AppShellConfigurator {

    public static void main(String[] args) {
        SpringApplication.run(DatabaseChangeManagementJavaApplication.class, args);
    }

}
