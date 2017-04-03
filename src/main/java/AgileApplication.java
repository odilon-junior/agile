import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableAutoConfiguration
@RestController
@ComponentScan("br.com.agile.*")
public class AgileApplication {

	@RequestMapping("/")
	String hello() {
		return "hello world";
	}

	@RequestMapping("/users")
	public @ResponseBody
	String getUsers() {
		return "{\"users\":[{\"firstname\":\"Richard\", \"lastname\":\"Feynman\"}," +
				"{\"firstname\":\"Marie\",\"lastname\":\"Curie\"}]}";
	}

	public static void main(String[] args) {
		SpringApplication.run(AgileApplication.class, args);
	}
}
