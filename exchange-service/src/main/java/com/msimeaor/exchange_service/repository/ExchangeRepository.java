package com.msimeaor.exchange_service.repository;

import com.msimeaor.exchange_service.model.Exchange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

@Repository
public interface ExchangeRepository extends JpaRepository<Exchange, Long> {

  @Query("SELECT e FROM Exchange e WHERE e.from = :from AND e.to = :to")
  Exchange findByFromAndTo(@PathVariable("from") String from,
                           @PathVariable("to") String to);

}
