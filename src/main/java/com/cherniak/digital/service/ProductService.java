package com.cherniak.digital.service;

import com.cherniak.digital.model.Product;
import com.cherniak.digital.repository.ProductRepository;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@RequiredArgsConstructor
public class ProductService {

  public static Map<Long,Product> identityMap = new HashMap<>();

  private final ProductRepository repository;

  @Transactional
  public Product saveOrUpdate(Product product) {
   identityMap.replace(product.getId(), product);
    return repository.save(product);
  }

  public Product get(Long id){
    if (identityMap.containsKey(id)){
      return identityMap.get(id);
    }
    Product product = repository.findById(id).orElse(null);
    if (product != null) {
      identityMap.put(product.getId(), product);
    }
    return product;
  }

  @Transactional
  public void delete(Long id){
    identityMap.remove(id);
    repository.deleteById(id);
  }
}
