package com.cherniak.digital.dto;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductPriceDto {

  private Long productId;
  private String name;
  private Long priceId;
  private Double price;
  private Date date;

}
