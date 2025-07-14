package dev.fredpena.dcm.service;

import dev.fredpena.dcm.data.Person;
import dev.fredpena.dcm.data.PersonRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author me@fredpena.dev
 * @created 13/07/2025  - 13:33
 */
@Service
@RequiredArgsConstructor // Inyección de dependencias vía constructor con Lombok
public class PersonService {

    private final PersonRepository repository;

    public Optional<Person> get(Long id) {
        return repository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Person> findAll() {
        return repository.findAll();
    }

    @Transactional
    public Person save(Person person) {
        return repository.save(person);
    }

    @Transactional
    public Person update(Long id, Person personDetails) {
        Person existingPerson = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Person not found with id: " + id));

        existingPerson.setFirstName(personDetails.getFirstName());
        existingPerson.setLastName(personDetails.getLastName());
        existingPerson.setAddress(personDetails.getAddress());
        existingPerson.setPhoneNumber(personDetails.getPhoneNumber());
        existingPerson.setBirthDate(personDetails.getBirthDate());
        // El email no se actualiza por diseño (@Column(updatable = false))

        return repository.save(existingPerson);
    }

    public Page<Person> list(Pageable pageable) {
        return repository.findAll(pageable);
    }
}