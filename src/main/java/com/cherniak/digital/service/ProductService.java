package com.cherniak.digital.service;

import com.cherniak.digital.model.Product;
import com.cherniak.digital.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

  private final ProductRepository repository;

  public Integer getCount() {
    return repository.getCount();
  }

  @Transactional
  public Product saveOrUpdate(Product product) {
    return repository.save(product);
  }
}
