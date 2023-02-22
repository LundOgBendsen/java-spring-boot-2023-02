package dk.logb.jpaboot.webshop.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductRepository repo;

    @Transactional(propagation = Propagation.REQUIRED)
    public Product createProduct(Product product) {
        return repo.save(product);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public Product findProductById(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Product> findAll() {
        return repo.findAll();
    }

}
