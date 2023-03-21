package dk.logb.jpaboot.webshop.order;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

//this class demonstrates how to inject the EntityManager into a custom repository
//and how to use it to execute custom queries
public class CustomOrderRepositoryImpl implements  CustomOrderRepository {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public Order customFindById(Long id) {
        return (Order) entityManager.createQuery("FROM Order o WHERE o.id = :id")
                .setParameter("id", id)
                .getSingleResult();
    }


    @Override
    public void flush() {
        entityManager.flush();
    }
}
