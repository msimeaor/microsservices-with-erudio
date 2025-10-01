package com.msimeaor.exchange_service.controller;

import com.msimeaor.exchange_service.environment.InstanceInformationService;
import com.msimeaor.exchange_service.model.Exchange;
import com.msimeaor.exchange_service.repository.ExchangeRepository;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.print.Book;
import java.math.BigDecimal;

@Tag(name = "Exchange Controller")
@RestController
@RequestMapping("exchange-service/")
public class ExchangeController {

  private InstanceInformationService informationService;
  private ExchangeRepository repository;

  public ExchangeController(InstanceInformationService informationService,
                            ExchangeRepository repository) {

    this.informationService = informationService;
    this.repository = repository;
  }

  // http://localhost:8000/exchange-service/5/USD/BRL
  //@Retry(name = "default", fallbackMethod = "fallbackMethod")
  //@RateLimiter(name = "default", fallbackMethod = "fallbackMethod")
  //@CircuitBreaker(name = "default", fallbackMethod = "fallbackMethod")
  //@Bulkhead(name = "default")
  @Operation(summary = "FindBook",
    description = "Endpoint to search for a book",
    tags = {"Search"},
    responses = {
      @ApiResponse(description = "Success", responseCode = "200",
        content = {
          @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = Book.class)
          )
        }
      ),
      @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
      @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
      @ApiResponse(description = "Forbiden", responseCode = "403", content = @Content),
      @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
    }
  )
  @GetMapping("/{amount}/{from}/{to}")
  public ResponseEntity<Exchange> getExchange(@PathVariable("amount") BigDecimal amount,
                                       @PathVariable("from") String from,
                                       @PathVariable("to") String to) {

    Exchange exchange = repository.findByFromAndTo(from, to);
    if (exchange == null) throw new RuntimeException("Impossível realizar o câmbio");

    BigDecimal convertedValue = exchange.getConversionFactor().multiply(amount);
    exchange.setConvertedValue(convertedValue);
    exchange.setEnvironment("PORT: " + informationService.getPort());

    return new ResponseEntity<>(exchange, HttpStatus.OK);
  }

}
