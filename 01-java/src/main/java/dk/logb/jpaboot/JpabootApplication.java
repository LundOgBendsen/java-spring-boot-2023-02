package dk.logb.jpaboot;

import dk.logb.jpaboot.dao.PersonRepository;
import dk.logb.jpaboot.model.person.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JpabootApplication implements CommandLineRunner {

	@Autowired
	private PersonRepository repository;
	public static void main(String[] args) {
		SpringApplication.run(JpabootApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Hello World");
		repository.save(new Person("John", "vangen 23, Brønshøj"));
		repository.save(new Person("Ida", "Gyden 34, Hillerød"));
		repository.save(new Person("Sue", "Krogen 222, Holte"));

	}

}
