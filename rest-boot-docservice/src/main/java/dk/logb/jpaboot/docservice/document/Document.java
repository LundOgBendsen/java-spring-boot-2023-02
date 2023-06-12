package dk.logb.jpaboot.docservice.document;


import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Document {
    private Long id;
    @JsonIgnore
    private byte[] content;

    private String name;

    private List<String> keywords = new ArrayList<String>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void addKeyword(String keyword) {
        keywords.add(keyword);
    }

    public boolean removeKeyword(String keyword) {
        return keywords.remove(keyword);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, name, keywords);
        result = 31 * result + Arrays.hashCode(content);
        return result;
    }

    //equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Document)) return false;
        Document that = (Document) o;
        return Objects.equals(id, that.id) &&
                Arrays.equals(content, that.content) &&
                Objects.equals(name, that.name) &&
                Objects.equals(keywords, that.keywords);
    }

    //toString
    @Override
    public String toString() {
        return "Document{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", keywords=" + keywords +
                '}';
    }
}
