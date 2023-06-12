package dk.logb.jpaboot.docservice.document.json;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dk.logb.jpaboot.docservice.document.Document;
import dk.logb.jpaboot.docservice.representation.Link;

import java.util.ArrayList;
import java.util.List;

public class DocumentRepresentation {
    private Document document;
    private List<Link> links = new ArrayList<Link>();

    public DocumentRepresentation() {
    }

    public DocumentRepresentation(Document document) {
        this.document = document;
    }

    public DocumentRepresentation(Document document, Link... links) {
        this.document = document;
        getLinks().addAll(List.of(links));
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    //include in json for convenience
    public String getEtag() {
        return Integer.toHexString(document.hashCode());
    }
}
