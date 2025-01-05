package estudos.springboot.thymeleaf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@EntityScan(basePackages = { "estudos.springboot.thymeleaf.entities" })
@EnableJpaRepositories(basePackages = { "estudos.springboot.thymeleaf.repositories" })
@ComponentScan(basePackages = { "estudos.springboot.thymeleaf.services", "estudos.springboot.thymeleaf.controllers", "estudos.springboot.thymeleaf.security"})
@SpringBootApplication
public class EstudosSpringbootThymeleafApplication {

	public static void main(String[] args) {
		SpringApplication.run(EstudosSpringbootThymeleafApplication.class, args);
	}
}
