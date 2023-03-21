package dk.logb.jpaboot.webshop.person;

import org.springframework.data.jpa.repository.JpaRepository;
import dk.logb.jpaboot.webshop.person.Person;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Repository
public interface PersonRepository<T extends Person , Long extends Serializable>
        extends JpaRepository<T , Long> {
    List<Person> findByName(String name);
    Person findByIdWithOrdersAndOrderLinesAndProducts(Long id);

}
