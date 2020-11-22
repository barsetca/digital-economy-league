package com.cherniak.digital.controller;

import com.cherniak.digital.dto.FrequencyPriceChangeByDate;
import com.cherniak.digital.dto.FrequencyPriceChangeByProduct;
import com.cherniak.digital.dto.PriceDto;
import com.cherniak.digital.model.Price;
import com.cherniak.digital.service.PriceService;
import com.cherniak.digital.service.ProductService;
import java.time.LocalDate;
import java.util.List;
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

  @GetMapping
  public List<PriceDto> getAllByDate(
      @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
    List<Price> prices = priceService.getList(date);
    return prices.stream().map(PriceDto::new).collect(Collectors.toList());
  }

  @GetMapping("/statistic")
  public List<FrequencyPriceChangeByDate> getStatistic() {
    StringBuffer sbCount = new StringBuffer("Количество товаров в БД: ");
    sbCount.append(productService.getCount());

    List<FrequencyPriceChangeByProduct> listProducts = priceService
        .getFrequencyPriceChangeByProducts().stream().map(
            FrequencyPriceChangeByProduct::new)
        .collect(Collectors.toList());

    List<FrequencyPriceChangeByDate> listDates = priceService.getFrequencyPriceChangeByDates()
        .stream().map(FrequencyPriceChangeByDate::new).collect(
            Collectors.toList());

    return listDates;
        //listProducts; FrequencyPriceChangeByProduct
  }
}
