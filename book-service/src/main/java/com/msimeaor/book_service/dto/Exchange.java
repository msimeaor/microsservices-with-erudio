package com.msimeaor.book_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Exchange implements Serializable {

  private static final long serialVersionUID = 1L;

  private Long id;
  private String from;
  private String to;
  private BigDecimal conversionFactor;
  private BigDecimal convertedValue;
  private String environment;


}
