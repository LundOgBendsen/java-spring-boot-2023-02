package dk.logb.jpaboot.docservice.representation;

//represents a hyperlink for use in REST representations
public class Link {
private String href;
    private String rel;
    private String type;

    public Link() {
    }

    public Link(String href, String rel, String type) {
        this.href = href;
        this.rel = rel;
        this.type = type;
    }

    public Link(String href, String rel) {
        this.href = href;
        this.rel = rel;
    }

    public Link(String href) {
        this.href = href;
    }

    public String getHref() {
        return href;
    }

    public String getRel() {
        return rel;
    }

    public String getType() {
        return type;
    }
}
