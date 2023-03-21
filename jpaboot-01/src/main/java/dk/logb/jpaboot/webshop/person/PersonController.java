package dk.logb.jpaboot.webshop.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping ("/persons")
public class PersonController {

    @Autowired
    PersonRepository<Person, Long> repository;

    @Autowired
    PersonService service;

    @GetMapping
    Iterable<Person> getAllPersons() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    Person getPersonById(@PathVariable(value = "id") Long id) {
        return repository.findById(id).get();
    }

    @GetMapping("/full/{id}")
    Person getPersonByIdWithOrdersAndOrderLines(@PathVariable("id") Long id) {
        //Person person1 = repository.findWithOrdersAndOrderLines().stream().filter(person -> person.getId() == id).findFirst().orElse(new Person());
        System.out.println("id = " + id);
        Person p = repository.findByIdWithOrdersAndOrderLinesAndProducts(id);
        System.out.println("person1 = " + p);

       return p;
    }

    @GetMapping("/create10")
    Iterable<Person> create10PersonsIn2Batches() {
        return service.create10PersonsIn2Batches();
    }

}
