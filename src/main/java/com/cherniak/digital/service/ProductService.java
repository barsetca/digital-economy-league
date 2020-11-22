package com.cherniak.digital.service;

import com.cherniak.digital.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

  private final ProductRepository repository;

  public int getCount() {
    return repository.getCount();
  }
}
