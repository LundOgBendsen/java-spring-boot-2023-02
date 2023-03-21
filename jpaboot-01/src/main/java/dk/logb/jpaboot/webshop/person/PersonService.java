package dk.logb.jpaboot.webshop.person;

import jakarta.persistence.PersistenceContext;
import jakarta.persistence.EntityManager;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonService {

    @Autowired
    private PlatformTransactionManager transactionManager;

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    PersonRepository personRepo;


    public List<Person> create10PersonsIn2Batches() {
        //add persons to arraylist
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);

        ArrayList<Person> people = new ArrayList<>();

        String execute = transactionTemplate.execute(status -> {
            int population = getNumberOfPersons();
            for (int i = 0; i < 5; i++) {
                Person p = new Person("Name " + population + " - " + i, "Address " + i);
                personRepo.save(p);
                people.add(p);
            }
            return "";
        });
        transactionTemplate.execute(status -> {
            int population = getNumberOfPersons();
            for (int i = 0; i < 5; i++) {
                Person p = new Person("Name " + population + " - " + i, "Address " + i);
                personRepo.save(p);
                people.add(p);
            }
            return null;
        });
        return people;
    }

    int getNumberOfPersons() {
        return entityManager.createQuery("SELECT COUNT(p) FROM Person p", Long.class).getSingleResult().intValue();
    }
}
