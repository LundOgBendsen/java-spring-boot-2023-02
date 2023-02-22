package dk.logb.jpaboot.webshop.order;

import dk.logb.jpaboot.webshop.document.Document;
import dk.logb.jpaboot.webshop.document.DocumentService;
import dk.logb.jpaboot.webshop.order.OrderDataService;
import dk.logb.jpaboot.webshop.order.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.transaction.annotation.Propagation.REQUIRED;

@Service
public class OrderServiceFacade {
    @Autowired
    OrderDataService orderDataService;

    @Autowired
    DocumentService documentService;



    /*
    This method is called by the OrderController when a new order is submitted. Submitting an order means that the
    order is formally accepted by the customer and the shop. The order is then saved to the database and an order
    confirmation is document is generated and stored in the database.
    This is an example of transaction propagation.
     */
    @Transactional(propagation = REQUIRED)
    public Order submitOrder(Order order) {
        orderDataService.save(order);
        //get document from document builder service
        Document document = documentService.buildDocumentFromOrder(order);
        documentService.save(document);
        return order;
    }
}
