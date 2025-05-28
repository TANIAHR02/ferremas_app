package cl.duoc.ferremasapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "cl.duoc.ferremasapp")
@EnableJpaRepositories(basePackages = "cl.duoc.ferremasapp.repository")
@EntityScan(basePackages = "cl.duoc.ferremasapp.model")
public class FerremasApplication {
    public static void main(String[] args) {
        SpringApplication.run(FerremasApplication.class, args);
    }
}
