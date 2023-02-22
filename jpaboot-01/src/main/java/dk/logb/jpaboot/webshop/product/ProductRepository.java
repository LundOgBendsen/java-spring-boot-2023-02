package dk.logb.jpaboot.webshop.product;

import dk.logb.jpaboot.webshop.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{
}