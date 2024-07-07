package smarthome;

import jakarta.validation.constraints.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class CorsConfig implements WebMvcConfigurer {
    @Bean
    @NotNull
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
                public void addCorsMappings(CorsRegistry registry) {
                    registry.addMapping("/**").allowedMethods("GET", "POST", "PUT", "DELETE");
                }
            };
        }
    }
