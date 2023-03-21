package dk.logb.jpaboot.webshop.order;

import dk.logb.jpaboot.webshop.order.OrderRepository;
import dk.logb.jpaboot.webshop.order.Order;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.transaction.annotation.Propagation.REQUIRED;

@Service
public class OrderDataService {
    @Autowired
    OrderRepository orderRepo;

    @PersistenceContext
    private EntityManager entityManager;


    @Transactional(readOnly = true)
    public void jpaHell() {
        System.out.println("HERE!!!!!");
        List<Order> all = orderRepo.findAll();
        Order o1 = null;
        if (all.size() > 0) {
            o1 = all.get(0);
        }

        Order o2 = (Order) entityManager.createQuery("FROM Order o WHERE o.id = :id")
                .setParameter("id", o1.getId())
                .getSingleResult();
        System.out.println("o1==o2: " + (o1==o2));
        o2.setNumber(o2.getNumber()*-1);
    }

    @Transactional(readOnly = true)
    public Order findById(Long id) {
        return orderRepo.customFindById(id);
    }

    @Transactional(propagation = REQUIRED)
    public Order save(Order order) {
        return orderRepo.save(order);
    }
}
