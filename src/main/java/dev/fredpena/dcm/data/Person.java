package dev.fredpena.dcm.data;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author me@fredpena.dev
 * @created 13/07/2025  - 11:55
 */

@Getter
@Setter
@Entity
@Table(name = "person")
public class Person implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 1, max = 100)
    private String firstName;

    @NotNull
    @Size(min = 1, max = 100)
    private String lastName;

    @Email
    @NotNull
    @Size(min = 1, max = 255)
    @Column(unique = true, updatable = false)
    private String email;

    @Size(max = 255)
    private String address;

    @Size(max = 20)
    private String phoneNumber;

    private LocalDate birthDate;
}
