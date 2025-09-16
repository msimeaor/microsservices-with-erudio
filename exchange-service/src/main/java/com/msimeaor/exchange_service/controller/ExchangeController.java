package com.msimeaor.exchange_service.controller;

import com.msimeaor.exchange_service.environment.InstanceInformationService;
import com.msimeaor.exchange_service.model.Exchange;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("exchange-service/")
public class ExchangeController {

  private InstanceInformationService informationService;

  public ExchangeController(InstanceInformationService informationService) {
    this.informationService = informationService;
  }

  // http://localhost:8000/exchange-service/5/USD/BRL
  @GetMapping("/{amount}/{from}/{to}")
  public Exchange getExchange(@PathVariable("amount") BigDecimal amount,
                              @PathVariable("amount") String from,
                              @PathVariable("amount") String to) {


    return new Exchange(1L, from, to, BigDecimal.ONE, BigDecimal.ONE, "PORT: " + informationService.getPort());
  }

}
