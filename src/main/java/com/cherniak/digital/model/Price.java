package com.cherniak.digital.model;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
@Table(name = "prices")
public class Price {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "price")
  @Positive
  private Double price;

  @Column(name = "date")
  @FutureOrPresent
  private LocalDate date;

  @ManyToOne
  @JoinColumn(name = "product_id")
  private Product product;


  public Price(@Positive Double price) {
    this(null, price, LocalDate.now());
  }

  public Price(@Positive Double price,
      @FutureOrPresent LocalDate date) {
    this(null, price, date);
  }

  public Price(Long id, @Positive Double price,
      @FutureOrPresent LocalDate date) {
    this.id = id;
    this.price = price;
    this.date = date;
  }
}
