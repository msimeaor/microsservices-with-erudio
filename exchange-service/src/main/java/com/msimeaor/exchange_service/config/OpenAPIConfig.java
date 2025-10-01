package com.msimeaor.exchange_service.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;

@OpenAPIDefinition(info =
@Info(
        title = "Exchange-Service",
        summary = "Exchange a monetary amount to another currency",
        description = "Exchange a monetary amount to another currency",
        contact = @Contact(name = "Matheus Simeão", email = "maatsimeao@gmail.com"),
        version = "1.0.0"
))
public class OpenAPIConfig {

  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
      .components(new Components())
      .info(new io.swagger.v3.oas.models.info.Info()
        .title("exchange-Service")
        .summary("Exchange a monetary amount to another currency")
        .description("Exchange a monetary amount to another currency")
        .contact(new io.swagger.v3.oas.models.info.Contact()
          .name("Matheus Simeão")
          .email("maatsimeao@gmail.com"))
        .version("1.0.0"));
  }

}
