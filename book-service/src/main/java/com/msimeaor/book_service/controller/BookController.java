package com.msimeaor.book_service.controller;

import com.msimeaor.book_service.dto.Exchange;
import com.msimeaor.book_service.environment.InstanceInformationService;
import com.msimeaor.book_service.model.Book;
import com.msimeaor.book_service.repository.BookRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@RestController
@RequestMapping("book-service/")
public class BookController {

  private InstanceInformationService informationService;
  private BookRepository repository;

  public BookController(InstanceInformationService informationService,
                        BookRepository repository) {

    this.informationService = informationService;
    this.repository = repository;
  }

  // http://localhost:8100/book-service/id/currency
  @GetMapping("/{id}/{currency}")
  public ResponseEntity<?> findBook(@PathVariable("id") long id,
                                    @PathVariable("currency") String currency) {

    Book book = repository.findById(id).get();
    if (book == null) return new ResponseEntity<>("Livro não encontrado para o ID " + id, HttpStatus.NOT_FOUND);

    HashMap<String, String> params = new HashMap<>();
    params.put("amount", book.getPrice().toString());
    params.put("from", "USD");
    params.put("to", currency);

    var response = new RestTemplate()
            .getForEntity("http://localhost:8000/exchange-service/{amount}/{from}/{to}", Exchange.class, params);

    Exchange exchange = response.getBody();

    book.setEnvironment(informationService.getPort());
    book.setCurrency(currency);
    book.setPrice(exchange.getConvertedValue());

    return new ResponseEntity<>(book, HttpStatus.OK);
  }

}
