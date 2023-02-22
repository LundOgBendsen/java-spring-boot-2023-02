package dk.logb.jpaboot.webshop.order;

import dk.logb.jpaboot.webshop.order.OrderRepository;
import dk.logb.jpaboot.webshop.order.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.transaction.annotation.Propagation.REQUIRED;

@Service
public class OrderDataService {
    @Autowired
    OrderRepository orderRepo;

    @Transactional(readOnly = true)
    public Order findById(Long id) {
        return orderRepo.customFindById(id);
    }

    @Transactional(propagation = REQUIRED)
    public Order save(Order order) {
        return orderRepo.save(order);
    }
}
