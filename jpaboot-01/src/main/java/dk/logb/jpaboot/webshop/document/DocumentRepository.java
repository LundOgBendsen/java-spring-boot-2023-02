package dk.logb.jpaboot.webshop.document;

import dk.logb.jpaboot.webshop.document.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, Long> {

}
