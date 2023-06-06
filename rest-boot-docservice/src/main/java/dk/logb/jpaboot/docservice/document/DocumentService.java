package dk.logb.jpaboot.docservice.document;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;


public class DocumentService {

        int nextId = 0;

        Map<Long, Document> documents = new HashMap<Long, Document>();

        @PostConstruct
        void init() {
            create("doc1", readBinaryFile("./src/main/resources/ex01.pdf"), Arrays.asList("ski", "rollerskate", "bike"));
            create("doc2", readBinaryFile("./src/main/resources/ex01.pdf"), Arrays.asList("fly", "plane", "DC10"));
            create("doc3", readBinaryFile("./src/main/resources/ex01.pdf"), Arrays.asList("gym", "jump", "run"));
        }



        Document create(Document document) {
            document.setId((long) nextId++);
            documents.put(document.getId(), document);
            return document;
        }

        Document create(String name, byte[] content, List<String> keywords) {
            Document document = new Document();
            document.setContent(content);
            document.setName(name);
            keywords.stream().forEach(k -> document.addKeyword(k));
            return create(document);
        }
        public Optional<Document> findById(Long id) {
            return  Optional.ofNullable(documents.get(id));
        }

        public List<Document> findAll() {
            return new ArrayList<Document>(documents.values());
        }
    private byte[] readBinaryFile(String filename)  {
        //read file from filesystem using inputstream

        try {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(filename));
        //get length of file
        int length = (int) new java.io.File(filename).length();
        //create byte array of length
        byte[] bytes = new byte[length];
        //read file into byte array
            bis.read(bytes, 0, length);
            //return byte array
            return bytes;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
