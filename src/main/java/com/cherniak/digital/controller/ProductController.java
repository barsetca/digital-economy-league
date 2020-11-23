package com.cherniak.digital.controller;

import com.cherniak.digital.dto.FrequencyPriceChangeByDate;
import com.cherniak.digital.dto.FrequencyPriceChangeByProduct;
import com.cherniak.digital.dto.PriceDto;
import com.cherniak.digital.model.Price;
import com.cherniak.digital.service.PriceService;
import com.cherniak.digital.service.ProductService;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

  private final PriceService priceService;
  private final ProductService productService;

  private static final int THREAD_NUMBER = 3;

  @GetMapping
  public List<PriceDto> getAllByDate(
      @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
    List<Price> prices = priceService.getList(date);
    return prices.stream().map(PriceDto::new).collect(Collectors.toList());
  }

  @GetMapping("/statistic")
  public Map<String, Object> getStatistic() {

    CountDownLatch latch = new CountDownLatch(THREAD_NUMBER);
    ExecutorService executorService = Executors.newFixedThreadPool(THREAD_NUMBER);

    Future<Integer> countProductsFuture = getCountProductsFuture(latch, executorService);

    Future<List<FrequencyPriceChangeByProduct>> frequencyPriceChangeByProductFuture = getListPriceChangeByProductFuture(
        latch, executorService);

    Future<List<FrequencyPriceChangeByDate>> frequencyPriceChangeByDateFuture = getListPriceChangeByDateFuture(
        latch, executorService);

    try {
      latch.await();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    Map<String, Object> statistics = new ConcurrentHashMap<>();
    try {
      Integer countProducts = countProductsFuture.get(10, TimeUnit.SECONDS);
      List<FrequencyPriceChangeByProduct> frequencyPriceChangeByProduct = frequencyPriceChangeByProductFuture
          .get(10, TimeUnit.SECONDS);
      List<FrequencyPriceChangeByDate> frequencyPriceChangeByDate = frequencyPriceChangeByDateFuture
          .get(10, TimeUnit.SECONDS);
      statistics.put("Количество товаров в БД: ", countProducts);
      statistics.put("Частота изменения цены в разрезе товаров: ", frequencyPriceChangeByProduct);
      statistics.put("Частота изменения цены в разрезе дат: ", frequencyPriceChangeByDate);
    } catch (InterruptedException | ExecutionException | TimeoutException e) {
      e.printStackTrace();
    }
    executorService.shutdown();
    return statistics;

  }

  private Future<Integer> getCountProductsFuture(CountDownLatch latch,
      ExecutorService executorService) {
    return executorService.submit(() -> {
      Integer count = productService.getCount();
      latch.countDown();
      return count;
    });
  }

  private Future<List<FrequencyPriceChangeByDate>> getListPriceChangeByDateFuture(
      CountDownLatch latch,
      ExecutorService executorService) {
    return executorService
        .submit(() -> {
          List<FrequencyPriceChangeByDate> listDates = priceService
              .getFrequencyPriceChangeByDates()
              .stream().map(FrequencyPriceChangeByDate::new).collect(
                  Collectors.toList());
          latch.countDown();
          return listDates;
        });
  }

  private Future<List<FrequencyPriceChangeByProduct>> getListPriceChangeByProductFuture(
      CountDownLatch latch,
      ExecutorService executorService) {
    return executorService
        .submit(() -> {
          List<FrequencyPriceChangeByProduct> listProducts = priceService
              .getFrequencyPriceChangeByProducts().stream().map(
                  FrequencyPriceChangeByProduct::new)
              .collect(Collectors.toList());
          latch.countDown();
          return listProducts;
        });
  }
}
