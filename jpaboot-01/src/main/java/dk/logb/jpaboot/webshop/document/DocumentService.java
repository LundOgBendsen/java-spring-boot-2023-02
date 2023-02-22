package dk.logb.jpaboot.webshop.document;

import dk.logb.jpaboot.webshop.order.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import static org.springframework.transaction.annotation.Propagation.*;

@Service
public class DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    @Transactional(propagation = REQUIRED)
    public Document save(Document document) {
        return documentRepository.save(document);
    }

    public Document findById(Long id) {
        return documentRepository.findById(id).orElse(null);
    }

    /**
     * Build a document from an order. The order must have an Id (i.e. be persisted) before this method is called.
     */
    @Transactional(propagation = REQUIRED)
    public Document buildDocumentFromOrder(Order order) {
        if (order.getId() == null) {
            throw new IllegalArgumentException("Order must have an id before a document can be built from it.");
        }
        return buildDocument(order);
    }

    //TODO replace by a pdf generating method
    private Document buildDocument(Order order) {
        byte[] bytes;
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream("./src/main/resources/ex01_copy.pdf"))) {
            //get length of file
            int length = (int) new java.io.File("./src/main/resources/ex01_copy.pdf").length();
            //create byte array of length
            bytes = new byte[length];
            //read file into byte array
            try {
                bis.read(bytes, 0, length);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Document document = new Document();
        document.setName("order_" + order.getId() + ".pdf");
        document.setContent(bytes);
        document.addKeyword(""+order.getNumber());
        document.addKeyword("order");
        return document;
    }



}
