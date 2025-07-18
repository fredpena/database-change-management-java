package dev.fredpena.dcm.web;

import dev.fredpena.dcm.data.Person;
import dev.fredpena.dcm.service.PersonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author me@fredpena.dev
 * @created 13/07/2025  - 13:35
 */
@RestController
@RequestMapping("/api/persons") // Ruta base para todos los endpoints de este controlador
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    @GetMapping
    public List<Person> getAllPersons() {
        return personService.findAll();
    }

    @PostMapping
    public ResponseEntity<Person> createPerson(@Valid @RequestBody Person person) {
        Person createdPerson = personService.create(person);


        return new ResponseEntity<>(createdPerson, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable Long id, @RequestBody Person personDetails) {
        Person updatedPerson = personService.update(id, personDetails);
        return ResponseEntity.ok(updatedPerson);
    }
}