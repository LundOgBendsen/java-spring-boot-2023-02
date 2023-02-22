package dk.logb.jpaboot.webshop.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class SupplierService {

    @Autowired
    SupplierRepository repo;

    @Autowired
    ProductService productService;

    @Transactional(propagation = Propagation.REQUIRED)
    public Supplier create(Supplier supplier) {
        return repo.save(supplier);
    }



    /**
     * Get a supplier by id or name. If both are present, id is used.
     * @param supplier
     * @return the supplier if found, otherwise the supplier is saved and returned.
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public Supplier getOrCreate(Supplier supplier) {
        if (supplier.getId() != null) {
            Optional<Supplier> byId = repo.findById(supplier.getId());
            if (byId.isPresent()) {
                return byId.get();
            }
        }
        if (supplier.getName() != null) {
            Supplier byName = repo.findByName(supplier.getName());
            if (byName != null) {
                return byName;
            }
        }
        return repo.save(supplier);
    }
}
