package App;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static org.springframework.boot.SpringApplication.run;

@Configuration
@EnableAutoConfiguration
@ComponentScan({"App.config", "App.controller", "App.service"})
@EnableMongoRepositories
@EntityScan("App.model")
@EnableSwagger2
public class Application {

    private static ApplicationContext applicationContext;

    public static void main(String[] args) {
        applicationContext = (ApplicationContext) run(Application.class, args);
    }

    public static ApplicationContext getContext() {
        return applicationContext;
    }
}
