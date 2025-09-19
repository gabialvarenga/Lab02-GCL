package lab02.br.locadora.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI locadoraOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl("http://localhost:8080");
        devServer.setDescription("Servidor de desenvolvimento");

        Contact contact = new Contact();
        contact.setEmail("contato@locadora.com");
        contact.setName("Suporte Locadora");
        contact.setUrl("https://locadora.com");

        License mitLicense = new License().name("MIT License").url("https://opensource.org/licenses/MIT");

        Info info = new Info()
                .title("API Locadora de Veículos")
                .version("1.0")
                .contact(contact)
                .description("API para gerenciamento de locadora de veículos")
                .termsOfService("https://locadora.com/termos")
                .license(mitLicense);

        return new OpenAPI()
                .info(info)
                .servers(List.of(devServer));
    }
}