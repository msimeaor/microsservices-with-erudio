package com.msimeaor.exchange_service.model;

import jakarta.persistence.*;
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
@Entity
public class Exchange implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  private long id;

  @Column(name = "from_currency", length = 3)
  private String from;

  @Column(name = "to_currency", length = 3)
  private String to;

  @Column(precision = 11, scale = 2)
  private BigDecimal conversionFactor;

  @Transient
  private BigDecimal convertedValue;
  @Transient
  private String environment;


}
