package dk.logb.jpaboot.webshop.document;

import dk.logb.jpaboot.webshop.person.Person;
import dk.logb.jpaboot.webshop.person.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping ("/documents")
public class DocumentController {

    @Autowired
    DocumentService service;


    @RequestMapping(
            method = RequestMethod.GET,
            value = "/{id}",
            produces = "application/pdf"
    )
    ResponseEntity<byte[]> getDocumentById(@PathVariable(value = "id") Long id) {
        Document doc = service.findById(id);
        byte[] content = doc.getContent();

        HttpHeaders headers = new HttpHeaders();
        String filename = "output.pdf";
        headers.setContentDispositionFormData(filename, filename);

        return ResponseEntity.ok()
                //.headers(headers)
                .contentLength(content.length)
                .contentType(MediaType.parseMediaType("application/pdf"))
                .body(content);
    }
}
