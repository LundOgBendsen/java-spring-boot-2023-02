package dk.logb.jpaboot.webshop.person;

import org.springframework.data.jpa.repository.JpaRepository;
import dk.logb.jpaboot.webshop.person.Person;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository
public interface PersonRepository<T extends Person , Long extends Serializable>
        extends JpaRepository<T , Long> {
    List<Person> findByName(String name);

    //Person findByIdWithOrdersWithOrderLineWithProductWithSupplier(long id);
}
