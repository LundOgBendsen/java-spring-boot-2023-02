package dk.logb.java8.example;

import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.PI;

//example program demonstrating Java features from 1.0 to 8.0

public class PersonWriter {
    private static final String FILE = "./persons.ser";
    List<Person> persons = new ArrayList<>() {
        {
            add(new Person("John", 12));
            add(new Person("Jane", 35));
            add(new Person("Jack", 40));
            add(new Person("Jill", 45));
        }
    };

    public static void main(String[] args) throws IOException {
        new PersonWriter().writePersons();
        new PersonWriter().readPersons().stream().map(PersonWriter::asJson).forEach(System.out::println);
    }
    public void writePersons() throws IOException {
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE));) {
            persons.stream()
                    .filter(p -> p.age > 18)
                    .map(p -> new Person(p.name, (int)(p.age*PI)))
                    .forEach(p -> {
                        try {
                            oos.writeObject(p);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
        }
    }
    public List<Person> readPersons() {
        List<Person> persons = new ArrayList<>();
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE))) {
            while(true) {
                persons.add((Person) ois.readObject());
            }
        } catch (EOFException e) {
            return persons;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static String asJson(Person p) {
        Field[] declaredFields = p.getClass().getDeclaredFields();
        StringBuilder sb = new StringBuilder("{\"")
                .append(p.getClass().getSimpleName())
                .append("\": {");
        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(true);
            try {
                sb.append("\"").append(declaredField.getName()).append("\": \"")
                        .append(declaredField.get(p)).append("\", ");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        sb.delete(sb.length()-2, sb.length());
        sb.append("}}");
        return sb.toString();
    }
}

class Person implements Serializable {
    String name;
    int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
