package br.com.librigate;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
public class LibrigateApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibrigateApplication.class, args);
	}

	@Configuration
	static class SwaggerConfig{

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
}
