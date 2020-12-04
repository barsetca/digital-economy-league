package com.cherniak.digital.service;

import com.cherniak.digital.dto.FrequencyPriceChangeByDate;
import com.cherniak.digital.dto.FrequencyPriceChangeByProduct;
import com.cherniak.digital.model.Price;
import com.cherniak.digital.repository.PriceRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PriceService {

  private final PriceRepository repository;

  public List<Price> getList(LocalDate date) {
    return repository.getList(date);
  }

  public List<FrequencyPriceChangeByProduct> getFrequencyPriceChangeByProducts() {
    return repository.getFrequencyPriceChangeByProducts().stream()
        .map(FrequencyPriceChangeByProduct::new)
        .collect(Collectors.toList());
  }

  public List<FrequencyPriceChangeByDate> getFrequencyPriceChangeByDates() {
    return repository.getFrequencyPriceChangeByDates().stream()
        .map(FrequencyPriceChangeByDate::new)
        .collect(Collectors.toList());
  }
}
