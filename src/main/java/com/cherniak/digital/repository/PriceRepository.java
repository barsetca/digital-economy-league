package com.cherniak.digital.repository;

import com.cherniak.digital.model.Price;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceRepository extends JpaRepository<Price, Long>,
    JpaSpecificationExecutor<Price> {


  @Query("SELECT p as product FROM Price p WHERE p.id IN (SELECT MAX(p1.id) FROM Price p1 WHERE p1.date <= :select_date GROUP BY p1.product.id HAVING MAX(p1.date) <= :select_date)")
  List<Price> getList(@Param("select_date") LocalDate date);

  @Query("SELECT MAX(p.id) FROM Price p WHERE p.date <= :select_date GROUP BY p.product.id HAVING MAX(p.date) <= :select_date")
  List<Object> getListObject(@Param("select_date") LocalDate date);
}
