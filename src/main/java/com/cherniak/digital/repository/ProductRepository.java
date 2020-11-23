package com.cherniak.digital.repository;

import com.cherniak.digital.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

  @Query("SELECT COUNT (p.id) as count_of_products FROM Product p")
  Integer getCount();

}
