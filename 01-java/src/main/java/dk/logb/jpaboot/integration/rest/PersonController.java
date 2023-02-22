package dk.logb.jpaboot.integration.rest;

import dk.logb.jpaboot.dao.PersonRepository;
import dk.logb.jpaboot.model.person.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/persons")
public class PersonController {

    @Autowired
    PersonRepository<Person, Long> repository;

    @GetMapping
    Iterable<Person> getAllPersons() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    Person getPersonById(@PathVariable(value = "id") Long id) {
        return repository.findById(id).get();
    }





}
