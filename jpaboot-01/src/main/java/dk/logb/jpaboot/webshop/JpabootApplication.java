package dk.logb.jpaboot.webshop;

import dk.logb.jpaboot.webshop.order.OrderLine;
import dk.logb.jpaboot.webshop.order.OrderServiceFacade;
import dk.logb.jpaboot.webshop.person.PersonRepository;
import dk.logb.jpaboot.webshop.document.DocumentRepository;
import dk.logb.jpaboot.webshop.order.OrderRepository;
import dk.logb.jpaboot.webshop.product.Product;
import dk.logb.jpaboot.webshop.product.ProductService;
import dk.logb.jpaboot.webshop.product.SupplierService;
import dk.logb.jpaboot.webshop.document.Document;
import dk.logb.jpaboot.webshop.order.Order;
import dk.logb.jpaboot.webshop.person.Person;
import dk.logb.jpaboot.webshop.product.Supplier;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.io.*;
import java.util.List;
import java.util.Random;

@SpringBootApplication
public class JpabootApplication implements CommandLineRunner {

	@Autowired
	private PersonRepository personRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private DocumentRepository documentRepository;

	@Autowired
	SupplierService supplierService;

	@Autowired
	OrderServiceFacade orderServiceFacade;

	@Autowired
	ProductService productService;

	@Autowired
	private PlatformTransactionManager transactionManager;

	@PersistenceContext
	EntityManager em;

	public static void main(String[] args) {
		SpringApplication.run(JpabootApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Hello World");
		personRepository.save(new Person("John", "vangen 23, Brønshøj"));
		personRepository.save(new Person("Ida", "Gyden 34, Hillerød"));
		personRepository.save(new Person("Sue", "Krogen 222, Holte"));
		queryExamples();

	}

	private void queryExamples() throws IOException {
		//ex 1 - find by id - returns optional so we
		var p1 = personRepository.findById(1L).orElse("not found");
		System.out.println("ex 1 : " + p1);
		//ex 2 - custom query method in repository
		List johns = personRepository.findByName("John");
		System.out.println("ex 2 : found " + johns.size() + ", " + johns);

		//ex 3 - find all sorted by name
		Sort sortById = Sort.by("id").ascending();
		Iterable<Person> all = personRepository.findAll(sortById);
		System.out.println("ex 3 : sorted by name");
		all.forEach(System.out::println);

		//ex 4 - find all paged (page 0, size 2)
		Pageable pageable = PageRequest.of(0, 2);
		Page<Person> page = personRepository.findAll(pageable);
		System.out.println("ex 4 : paged");
		page.forEach(System.out::println);
		//get all pages
		while (page.hasNext()) {
			page = personRepository.findAll(page.nextPageable());
			page.forEach(System.out::println);
		}

		//ex 5 - find by id using an entitymanager
		Order order = orderRepository.customFindById(1L);
		System.out.println("ex 5 : " + order);
		order.setNumber(1234);
		System.out.println("ex 5 : now flusing");
		orderRepository.flush();
		System.out.println("ex 5 : just flushed");
		System.out.println("ex 5 : " + orderRepository.getClass().getName());
		//ex 6 - documents
		Long id = 0L;
		try {
			byte[] file = readBinaryFile("./src/main/resources/ex01.pdf");

			Document doc = new Document();
			doc.setName("ex01.pdf");
			doc.setContent(file);
			System.out.println("--->" + doc.getId());

			doc.addKeyword("springboot");
			doc.addKeyword("java");
			doc.addKeyword("course");
			documentRepository.saveAndFlush(doc);
			id = doc.getId();

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		Document d = documentRepository.findById(id).get();

		byte[] content = d.getContent();
		FileOutputStream fos = new FileOutputStream("./src/main/resources/ex01_copy.pdf");
		fos.write(content);
		fos.close();

		Supplier nn = new Supplier("Novo Nordisk", "Penicillinvej 23, 2300 København S");
		nn = supplierService.getOrCreate(nn);
		System.out.println("Supplier: " + nn);

		//ex 7 - order service facade (transaction propagation)
		//no transaction started, so a new one is created using TransactionTemplate
		Order o1 = new TransactionTemplate(transactionManager).execute( status -> {
			Order order1 = getPrefilledOrder();
			System.out.println(em.contains(order1.getPerson()));
			Order o = orderServiceFacade.submitOrder(order1);
			return o;
		});
	}

	private Order getPrefilledOrder() {
		List<Person> persons = personRepository.findAll();
		Person person = persons.get(0);
		Order order = new Order();
		order.setNumber(Math.abs(new Random().nextInt()));
		order.setPerson(person);
		//generate 3 orderlines
		List<Product> products = productService.findAll();
		//if less than 3 products, generate some
		if (products.size() < 3) {
			productService.createProduct(new Product("Milk", 12.5));
			productService.createProduct(new Product("Bread", 5.5));
			productService.createProduct(new Product("Chocolate", 7.5));
		}
		products = productService.findAll();
		//generate orderlines for first 3 products
		for (int i = 0; i < 3; i++) {
			OrderLine ol = new OrderLine();
			ol.setProduct(products.get(i));
			ol.setQuantity(2);
			ol.setPrice(12.5);
			order.addOrderLine(ol);
		}
		return order;
	}

	//method that reads a binary file from the filesystem and returns it as a byte array
	private static byte[] readBinaryFile(String filename) throws IOException {
		//read file from filesystem using inputstream

		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(filename));
		//get length of file
		int length = (int) new java.io.File(filename).length();
		//create byte array of length
		byte[] bytes = new byte[length];
		//read file into byte array
		bis.read(bytes, 0, length);

		//return byte array
		return bytes;
	}
}
