package br.com.librigate.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig{

    @Bean
    public OpenAPI customOpenAPI(){

        return new OpenAPI()
                .info(new Info()
                        .title("LibriGateAPI")
                        .version("1.0")
                        .description("Sistema para gerenciar acervo, empréstimos e vendas de livros em uma biblioteca e livraria.")
                        .contact(new Contact()
                                .name("Codigo fonte da aplicação")
                                .url("https://github.com/edenilsonjunior/LibriGateAPI")
                        )
                );
    }
}