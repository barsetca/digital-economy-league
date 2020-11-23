package com.cherniak.digital.service;

import com.cherniak.digital.model.Price;
import com.cherniak.digital.repository.PriceRepository;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PriceService {

  private final PriceRepository repository;

  public List<Price> getList(LocalDate date) {
    return repository.getList(date);
  }

  public List<Object[]> getFrequencyPriceChangeByProducts() {
    return repository.getFrequencyPriceChangeByProducts();
  }

  public List<Object[]> getFrequencyPriceChangeByDates() {

    return repository.getFrequencyPriceChangeByDates();
  }

  @Transactional
  public Price saveOrUpdate(Price price) {
    return repository.save(price);

  }
}
