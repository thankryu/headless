package mmd.headless.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI openAPI(@Value("v1.0") String appVersion) {

        Info info = new Info().title("Headless API Docs").version(appVersion)
                .description("Headless Platform Api 페이지")
                .termsOfService("https://thankryu.iptime.org/swagger-ui")
                .contact(new Contact().name("thankryu").url("https://github.com/thankryu/headless").email("thankryu@gmail.com"));

        return new OpenAPI()
                .components(new Components())
                .info(info);
    }
}