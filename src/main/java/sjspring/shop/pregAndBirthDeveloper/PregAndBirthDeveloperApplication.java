package sjspring.shop.pregAndBirthDeveloper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class PregAndBirthDeveloperApplication {
    public static void main(String[] args) {

        SpringApplication.run(PregAndBirthDeveloperApplication.class, args);
    }
}