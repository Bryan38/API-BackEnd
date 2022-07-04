package fr.ynov.api;

import fr.ynov.api.configuration.AppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
//@Import(WebSecurityConfig.class)
@Import(AppConfig.class)
public class ApiWeb {

    public static void main(String[] args) {
        SpringApplication.run(ApiWeb.class, args);
    }
}
