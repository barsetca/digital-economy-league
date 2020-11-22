package com.cherniak.digital.service;

import com.cherniak.digital.model.Product;
import com.cherniak.digital.repository.ProductRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

  private final ProductRepository repository;

  public List<Product> getAll() {
    return repository.findAll();
  }
}
