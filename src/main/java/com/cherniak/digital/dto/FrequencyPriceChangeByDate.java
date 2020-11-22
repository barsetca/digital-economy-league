package com.cherniak.digital.dto;

import java.io.Serializable;
import java.time.LocalDate;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;


@Data
@NoArgsConstructor
@Component
public class FrequencyPriceChangeByDate implements Serializable {

  private LocalDate date;
  private Long frequency;

  public FrequencyPriceChangeByDate(Object... fields) {
    this.date = (LocalDate) fields[0];
    this.frequency = (Long) fields[1];
  }
}
