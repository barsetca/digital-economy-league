package com.cherniak.digital.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
class ProductRepositoryTest {

  private ProductRepository repository;

  @Autowired
  public ProductRepositoryTest(ProductRepository repository) {
    this.repository = repository;
  }

  @Test
  void getCount() {
    Assertions.assertEquals(3 , repository.getCount());
  }
}