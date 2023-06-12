package dk.logb.jpaboot.docservice.document;

import dk.logb.jpaboot.docservice.document.json.DocumentRepresentation;
import dk.logb.jpaboot.docservice.representation.Link;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping ("/documents")
public class DocumentController {

    @Autowired
    DocumentService service;


    //get document by id as json with etag
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<DocumentRepresentation> getDocumentByIdAsJSON(@PathVariable(value = "id") Long id, HttpServletRequest request) {
        Document doc = service.findById(id).get();
        //link to doc as pdf
        DocumentRepresentation docRepresentation = getDocumentRepresentation(request, doc);

        return ResponseEntity.ok().header("ETag", docRepresentation.getEtag()).body(docRepresentation);
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/{id}"
    )
    ResponseEntity<DocumentRepresentation> getDocumentByIdDefault(@PathVariable(value = "id") Long id, HttpServletRequest request) {
        Document doc = service.findById(id).get();
        //link to doc as pdf
        DocumentRepresentation docRepresentation = getDocumentRepresentation(request, doc);

        return ResponseEntity.ok().header("ETag", docRepresentation.getEtag()).body(docRepresentation);
    }


    //get document by id as xml
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/{id}",
            produces = MediaType.APPLICATION_XML_VALUE
    )
    ResponseEntity<Document> getDocumentByIdAsXml(@PathVariable(value = "id") Long id) {
        Document doc = service.findById(id).get();
        System.out.println(doc.getName());
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_XML).body(doc);
    }


    @RequestMapping(
            method = RequestMethod.GET,
            value = "/{id}",
            produces = "application/pdf"
    )
    ResponseEntity<byte[]> getDocumentByIdAsPdf(@PathVariable(value = "id") Long id) {
        Document doc = service.findById(id).get();
        byte[] content = doc.getContent();

        HttpHeaders headers = new HttpHeaders();
        String filename = "output.pdf";
        headers.setContentDispositionFormData(filename, filename);

        return ResponseEntity.ok()
                .contentLength(content.length)
                .contentType(MediaType.parseMediaType("application/pdf"))
                .body(content);
    }

    //put document by id as json
    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<DocumentRepresentation> putDocumentByIdAsJSON(@PathVariable(value = "id") Long id, @RequestBody Document doc, HttpServletRequest request) {
        System.out.println(doc);
        Optional<Document> o = service.findById(id);
        if (o.isPresent()) {
            Document document = o.get();
            document.setName(doc.getName());
            document.setContent(doc.getContent());
            document.setKeywords(doc.getKeywords());
            DocumentRepresentation docRepresentation = getDocumentRepresentation(request, document);

            return ResponseEntity.ok().body(docRepresentation);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //post document as json, i.e. create a new document
    @RequestMapping(
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<Document> postDocumentAsJSON(@RequestBody Document doc) {
        System.out.println(doc);
        Document document = service.create(doc);
        return ResponseEntity.ok().body(document);
    }

    //conditional get document by id as json with support for etag
    /*
    curl --location 'localhost:8080/documents/1' \
        --header 'If-None-Match: "b4b49ebe"'
     */
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            headers = "If-None-Match"
    )
    ResponseEntity<DocumentRepresentation> getDocumentByIdAsJSONWithEtag(@PathVariable(value = "id") Long id, HttpServletRequest request) {
        Document doc = service.findById(id).get();
        //link to doc as pdf
        DocumentRepresentation docRepresentation = getDocumentRepresentation(request, doc);
        return ResponseEntity.ok().eTag(docRepresentation.getEtag()).body(docRepresentation);
    }

    //post to add a keyword to a document
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/{id}/keywords",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<DocumentRepresentation> postKeywordToDocument(@PathVariable(value = "id") Long id, @RequestBody String keyword, HttpServletRequest request) {
        Optional<Document> o = service.findById(id);
        if (o.isPresent()) {
            Document document = o.get();
            document.addKeyword(keyword);
            DocumentRepresentation docRepresentation = getDocumentRepresentation(request, document);
            return ResponseEntity.ok().body(docRepresentation);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //delete a keyword from a document
    @RequestMapping(
            method = RequestMethod.DELETE,
            value = "/{id}/keywords/{keyword}"
    )
    ResponseEntity<DocumentRepresentation> deleteKeywordFromDocument(@PathVariable(value = "id") Long id, @PathVariable(value = "keyword") String keyword, HttpServletRequest request) {
        Optional<Document> o = service.findById(id);
        if (o.isPresent()) {
            Document document = o.get();
            if (document.removeKeyword(keyword)) {
                DocumentRepresentation docRepresentation = getDocumentRepresentation(request, document);
                return ResponseEntity.ok().body(docRepresentation);
            }
        }
        return ResponseEntity.notFound().build();
    }


    private static DocumentRepresentation getDocumentRepresentation(HttpServletRequest request, Document doc) {
        Link pdf = new Link(request.getRequestURL().toString(),  "alternate", "application/pdf");
        Link self = new Link(request.getRequestURL().toString(),  "self", "application/json");

        DocumentRepresentation docRepresentation = new DocumentRepresentation(doc, pdf, self);
        return docRepresentation;
    }

    private static DocumentRepresentation getDocumentRepresentation(Document doc) {

        DocumentRepresentation docRepresentation = new DocumentRepresentation(doc);
        return docRepresentation;
    }
}
