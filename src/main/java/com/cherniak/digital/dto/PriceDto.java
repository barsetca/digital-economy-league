package com.cherniak.digital.dto;

import com.cherniak.digital.model.Price;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceDto {

  private String name;
  private double price;

  public PriceDto(Price price) {
    this.name = price.getProduct().getName();
    this.price = price.getPrice();
  }


}
