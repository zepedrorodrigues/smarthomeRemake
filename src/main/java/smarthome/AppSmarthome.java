package smarthome;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

/**
 * Main application class for the Smart Home project. This class is responsible for starting the Spring Boot
 * application.
 */
@SpringBootApplication
@ComponentScan(basePackages = {"smarthome", "smarthome.persistence.spring"})
public class AppSmarthome extends SpringBootServletInitializer {

    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(AppSmarthome.class);
    }


    /**
     * The main method which serves as the entry point for the application. It starts the Spring Boot application by
     * calling the run method of SpringApplication.
     * @param args command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(AppSmarthome.class, args);
    }
}