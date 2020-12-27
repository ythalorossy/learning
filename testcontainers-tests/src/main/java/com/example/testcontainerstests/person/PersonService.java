package com.example.testcontainerstests.person;

import org.springframework.stereotype.Service;

@Service
public class PersonService {

    private PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;

    }

    public Person createPerson(Person person) {
        return this.personRepository.save(person);
    }

    public Person getPersonByFirstName(String firstName) {
        return this.personRepository.findByFirstNameStartingWith(firstName).orElse(new Person());
    }

}
