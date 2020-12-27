package com.example.testcontainerstests.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "/api/v1/person")
@RequestMapping(path = "/api/v1/person")
public class PersonController {

    private PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public Person searchPersonByFirstName(@RequestParam(name = "firstName") String firstName) {
        return this.personService.getPersonByFirstName(firstName);
    }

    @PostMapping
    public Person createPerson(@RequestBody Person person) {
        
        personService.createPerson(person);
        
        return person;
    }

}