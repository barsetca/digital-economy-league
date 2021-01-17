package com.cherniak.digital.service;

import com.cherniak.digital.model.Product;
import com.cherniak.digital.repository.ProductRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class ProductService {

  public static Map<Long, Product> identityMap = new HashMap<>();

  private final ProductRepository repository;

  @Transactional
  public Product saveOrUpdate(Product product) {
    Product saved = repository.save(product);
    identityMap.put(saved.getId(), saved);
    return saved;
  }

  public Product get(Long id) {
    if (identityMap.containsKey(id)) {
      return identityMap.get(id);
    }
    Product product = repository.findById(id).orElse(null);
    if (product != null) {
      identityMap.put(product.getId(), product);
    }
    return product;
  }

  @Transactional
  public void delete(Long id) {
    identityMap.remove(id);
    repository.deleteById(id);
  }

  public List<Product> getAll() {
    int count = repository.getCount();
    if (count != identityMap.size()) {
      repository.findAll().stream()
          .filter(p -> !identityMap.containsKey(p.getId()))
          .forEach(p -> identityMap.put(p.getId(), p));
    }
    return new ArrayList<>(identityMap.values());
  }
}
