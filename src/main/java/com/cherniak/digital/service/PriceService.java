package com.cherniak.digital.service;

import com.cherniak.digital.model.Price;
import com.cherniak.digital.repository.PriceRepository;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PriceService {

  private final PriceRepository repository;

  public List<Price> getList(LocalDate date) {
    return repository.getList(date);
  }
}
