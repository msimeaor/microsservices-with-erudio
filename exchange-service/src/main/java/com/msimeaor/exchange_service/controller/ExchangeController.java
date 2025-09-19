package com.msimeaor.exchange_service.controller;

import com.msimeaor.exchange_service.environment.InstanceInformationService;
import com.msimeaor.exchange_service.model.Exchange;
import com.msimeaor.exchange_service.repository.ExchangeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

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
