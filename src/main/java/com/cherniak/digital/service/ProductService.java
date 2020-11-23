package com.cherniak.digital.service;

import com.cherniak.digital.repository.ProductRepository;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

  private final ProductRepository repository;

  public Integer getCount() {
    return repository.getCount();
  }
}
