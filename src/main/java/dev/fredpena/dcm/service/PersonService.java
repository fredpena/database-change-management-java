package dev.fredpena.dcm.service;

import dev.fredpena.dcm.data.Person;
import dev.fredpena.dcm.data.PersonRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author me@fredpena.dev
 * @created 13/07/2025  - 13:33
 */
@Service
@RequiredArgsConstructor // Inyección de dependencias vía constructor con Lombok
public class PersonService {

    private final PersonRepository personRepository;

    @Transactional(readOnly = true)
    public List<Person> findAll() {
        return personRepository.findAll();
    }

    @Transactional
    public Person create(Person person) {
        return personRepository.save(person);
    }

    @Transactional
    public Person update(Long id, Person personDetails) {
        Person existingPerson = personRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Person not found with id: " + id));

        existingPerson.setFirstName(personDetails.getFirstName());
        existingPerson.setLastName(personDetails.getLastName());
        existingPerson.setAddress(personDetails.getAddress());
        existingPerson.setPhoneNumber(personDetails.getPhoneNumber());
        existingPerson.setBirthDate(personDetails.getBirthDate());
        // El email no se actualiza por diseño (@Column(updatable = false))

        return personRepository.save(existingPerson);
    }
}