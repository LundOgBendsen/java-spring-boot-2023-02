package dk.logb.jpaboot.webshop.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
