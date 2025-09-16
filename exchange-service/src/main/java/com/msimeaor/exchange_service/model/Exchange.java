package com.msimeaor.exchange_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Exchange implements Serializable {

  private static final long serialVersionUID = 1L;

  private long id;
  private String from;
  private String to;
  private BigDecimal conversionFactor;
  private BigDecimal convertedValue;
  private String environment;


}
