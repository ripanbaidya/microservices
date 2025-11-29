package com.example.accounts;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAwareImpl")
// Here, we are defining the OpenAPI documentation for the Accounts microservice
// For production we should write all the config in the separate config file.
// But development demo purpose, we are writing it here directly.
@OpenAPIDefinition(
		info = @Info(
				title = "Accounts microservice REST API Documentation",
				description = "Example Accounts microservice REST API Documentation",
				version = "v1",
				contact = @Contact(
						name = "Ripan Baidya",
						email = "ripan.baidya@example.com",
						url = "https://www.example.com"
				),
				license = @License(
						name = "MIT license",
						url = "https://opensource.org/licenses/MIT"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "Accounts microservice External Documentation",
				url = "https://www.example.com/docs"
		)
)
public class AccountsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountsApplication.class, args);
	}

}
