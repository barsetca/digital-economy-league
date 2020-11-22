package com.cherniak.digital.dto;

import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FrequencyPriceChangeByProduct implements Serializable {

  private String name;
  private Long frequency;

  public FrequencyPriceChangeByProduct(Object... fields) {
    this.name = (String) fields[0];
    this.frequency = (Long) fields[1];
  }
}
