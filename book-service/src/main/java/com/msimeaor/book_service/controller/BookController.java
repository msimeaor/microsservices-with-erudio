package com.msimeaor.book_service.controller;

import com.msimeaor.book_service.dto.Exchange;
import com.msimeaor.book_service.environment.InstanceInformationService;
import com.msimeaor.book_service.model.Book;
import com.msimeaor.book_service.proxy.ExchangeProxy;
import com.msimeaor.book_service.repository.BookRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("book-service/")
public class BookController {

  private InstanceInformationService informationService;
  private BookRepository repository;
  private ExchangeProxy proxy;

  public BookController(InstanceInformationService informationService,
                        BookRepository repository,
                        ExchangeProxy proxy) {

    this.informationService = informationService;
    this.repository = repository;
    this.proxy = proxy;
  }

  // http://localhost:8100/book-service/id/currency
  @GetMapping("/{id}/{currency}")
  public ResponseEntity<Book> findBook(@PathVariable("id") long id,
                                    @PathVariable("currency") String currency) {

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
