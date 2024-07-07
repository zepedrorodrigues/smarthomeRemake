package smarthome;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public String filePathModels() {
        return "configModels.properties";
    }

    @Bean
    public String filePathDelta() {
        return "configDelta.properties";
    }
}
