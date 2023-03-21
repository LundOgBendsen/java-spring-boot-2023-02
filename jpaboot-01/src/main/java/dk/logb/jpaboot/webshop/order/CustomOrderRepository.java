package dk.logb.jpaboot.webshop.order;


import dk.logb.jpaboot.webshop.order.Order;


public interface CustomOrderRepository {
    Order customFindById(Long id);



    void flush();
}
