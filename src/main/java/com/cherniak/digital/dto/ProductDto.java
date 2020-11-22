package com.cherniak.digital.dto;

import com.cherniak.digital.model.Product;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductDto {

  private long productId;
  private String name;

  public ProductDto(Product product) {
    this.productId = product.getId();
    this.name = product.getName();
  }
}
