package sowatec.com.TBI.SowaSearchEngine;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
	
	@RequestMapping("/")
	public String index() {
		return "Hey";
	}
	
	@RequestMapping("/hello")
	public String hello() {
		return "Hello World";
	}


	@CrossOrigin
	@RequestMapping("/findAllBooks")
	public List<Book> getBooks() {
		return Stream.of(new Book(101, "Java", 999),
				new Book(102, "Spring", 1199), new Book(103, "Hibernate", 445),
				new Book(104, "Angular", 888)).collect(Collectors.toList());
	}
	
}
