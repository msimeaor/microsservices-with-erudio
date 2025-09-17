package com.msimeaor.book_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Book implements Serializable {

  private static final long serialVersionUID = 1l;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  private Long id;

  @Column(length = 100)
  private String author;

  @Column
  private String title;

  @Column(name = "launch_date")
  @Temporal(TemporalType.DATE)
  private Date launchDate;

  @Column(precision = 11, scale = 2)
  private BigDecimal price;

  @Transient
  private String currency;
  @Transient
  private String environment;

}
