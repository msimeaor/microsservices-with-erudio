package com.msimeaor.book_service.controller;

import com.msimeaor.book_service.dto.Exchange;
import com.msimeaor.book_service.environment.InstanceInformationService;
import com.msimeaor.book_service.model.Book;
import com.msimeaor.book_service.proxy.ExchangeProxy;
import com.msimeaor.book_service.repository.BookRepository;
import io.github.resilience4j.retry.annotation.Retry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Book Controller", description = "Endpoints to book manipulation")
@RestController
@RequestMapping("book-service/")
public class BookController {

  private InstanceInformationService informationService;
  private BookRepository repository;
  private ExchangeProxy proxy;
  private Logger logger = LoggerFactory.getLogger(BookController.class);

  public BookController(InstanceInformationService informationService,
                        BookRepository repository,
                        ExchangeProxy proxy) {

    this.informationService = informationService;
    this.repository = repository;
    this.proxy = proxy;
  }


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
  @GetMapping("/{id}/{currency}")
  @Retry(name = "find-book")
  public ResponseEntity<Book> findBook(@PathVariable("id") long id,
                                    @PathVariable("currency") String currency) {
    logger.info("Request to findBook received");

    Book book = repository.findById(id).get();
    if (book == null) throw new RuntimeException("Livro não encontrado!");

    Exchange exchange = proxy.getExchange(book.getPrice(), "USD", currency).getBody();

    //book.setEnvironment(informationService.getPort());
    book.setEnvironment("BOOK PORT: " + informationService.getPort() +
            " / EXCHANGE PORT: " + exchange.getEnvironment());
    book.setCurrency(currency);
    book.setPrice(exchange.getConvertedValue());

    return new ResponseEntity<>(book, HttpStatus.OK);
  }

}
