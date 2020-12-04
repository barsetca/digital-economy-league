package com.cherniak.digital.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.cherniak.digital.dto.FrequencyPriceChangeByDate;
import com.cherniak.digital.dto.FrequencyPriceChangeByProduct;
import com.cherniak.digital.model.Price;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
class PriceRepositoryTest {

  private PriceRepository repository;

  @Autowired
  public PriceRepositoryTest(PriceRepository repository) {
    this.repository = repository;
  }

  @Test
  void getList() {
    List<Price> priceList = repository.getList(LocalDate.now());
    Assertions.assertAll(
        () -> {Assertions.assertEquals(3, priceList.size());},
        () -> {Assertions.assertEquals(1, priceList.get(0).getPrice());},
        () -> {Assertions.assertEquals(100000.01, priceList.get(1).getPrice());},
        () -> {Assertions.assertEquals(54.99, priceList.get(2).getPrice());}
    );
  }

  @Test
  void getFrequencyPriceChangeByProducts() {
    List<Object[]> frequency = repository.getFrequencyPriceChangeByProducts();
    List<FrequencyPriceChangeByProduct> frequencyPriceChange =
        frequency.stream().map(FrequencyPriceChangeByProduct::new).collect(Collectors.toList());
    frequencyPriceChange.forEach(System.out::println);

    Assertions.assertAll(
        () -> {Assertions.assertEquals(3, frequency.size());},
        () -> {Assertions.assertEquals("Product1", frequencyPriceChange.get(0).getName());},
        () -> {Assertions.assertEquals("Product2", frequencyPriceChange.get(1).getName());},
        () -> {Assertions.assertEquals("Product3", frequencyPriceChange.get(2).getName());},
        () -> {Assertions.assertEquals(3, frequencyPriceChange.get(0).getFrequency());},
        () -> {Assertions.assertEquals(3, frequencyPriceChange.get(1).getFrequency());},
        () -> {Assertions.assertEquals(4, frequencyPriceChange.get(2).getFrequency());}
    );
  }

  @Test
  void getFrequencyPriceChangeByDates() {
    List<Object[]> frequency = repository.getFrequencyPriceChangeByDates();
    List<FrequencyPriceChangeByDate> frequencyPriceChange =
        frequency.stream().map(FrequencyPriceChangeByDate::new).collect(Collectors.toList());
    frequencyPriceChange.forEach(System.out::println);
    Assertions.assertAll(
        () -> {Assertions.assertEquals(6, frequency.size());},
        () -> {Assertions.assertEquals(LocalDate.of(2020, 11, 11), frequencyPriceChange.get(0).getDate());},
        () -> {Assertions.assertEquals(LocalDate.of(2020, 11, 13), frequencyPriceChange.get(1).getDate());},
        () -> {Assertions.assertEquals(LocalDate.of(2020, 11, 14), frequencyPriceChange.get(2).getDate());},
        () -> {Assertions.assertEquals(LocalDate.of(2020, 11, 15), frequencyPriceChange.get(3).getDate());},
        () -> {Assertions.assertEquals(LocalDate.of(2020, 11, 16), frequencyPriceChange.get(4).getDate());},
        () -> {Assertions.assertEquals(LocalDate.of(2020, 11, 19), frequencyPriceChange.get(5).getDate());},
        () -> {Assertions.assertEquals(1, frequencyPriceChange.get(0).getFrequency());},
        () -> {Assertions.assertEquals(2, frequencyPriceChange.get(1).getFrequency());},
        () -> {Assertions.assertEquals(2, frequencyPriceChange.get(2).getFrequency());},
        () -> {Assertions.assertEquals(2, frequencyPriceChange.get(3).getFrequency());},
        () -> {Assertions.assertEquals(2, frequencyPriceChange.get(4).getFrequency());},
        () -> {Assertions.assertEquals(1, frequencyPriceChange.get(5).getFrequency());}
    );
  }
}