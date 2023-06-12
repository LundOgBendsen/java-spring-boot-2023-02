package dk.logb.jpaboot.docservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.*;

@SpringBootApplication
public class JpabootApplication implements CommandLineRunner {
	public static void main(String[] args) {
		SpringApplication.run(JpabootApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("REST Document Service ready");
	}

}
