package com.msimeaor.book_service.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;

@OpenAPIDefinition(info =
  @Info(
    title = "Book-Service",
    summary = "Search for book prices in many currencies",
    description = "Search for book prices in many currencies",
    contact = @Contact(name = "Matheus Simeão", email = "maatsimeao@gmail.com"),
    version = "1.0.0"
))
public class OpenAPIConfig {

  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
      .components(new Components())
      .info(new io.swagger.v3.oas.models.info.Info()
        .title("Book-Service")
        .summary("Search for book prices in many currencies")
        .description("Search for book prices in many currencies")
        .contact(new io.swagger.v3.oas.models.info.Contact()
          .name("Matheus Simeão")
          .email("maatsimeao@gmail.com"))
        .version("1.0.0"));
  }

}



