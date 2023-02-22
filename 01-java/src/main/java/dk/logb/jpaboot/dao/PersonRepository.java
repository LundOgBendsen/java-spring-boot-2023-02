package dk.logb.jpaboot.dao;

import org.springframework.data.repository.CrudRepository;
import dk.logb.jpaboot.model.person.Person;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository
public interface PersonRepository<T extends Person , Long extends Serializable>
        extends CrudRepository<T , Long> {
    List<Person> findByName(String name);
    //Person findByIdWithOrdersWithOrderLineWithProductWithSupplier(long id);
}
