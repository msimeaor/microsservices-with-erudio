package com.msimeaor.book_service.proxy;

import com.msimeaor.book_service.dto.Exchange;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;

@FeignClient(name = "exchange-service", url = "localhost:8000")
public interface ExchangeProxy {

  @GetMapping("/exchange-service/{amount}/{from}/{to}")
  ResponseEntity<Exchange> getExchange(@PathVariable("amount") BigDecimal amount,
                                       @PathVariable("from") String from,
                                       @PathVariable("to") String to);

}
