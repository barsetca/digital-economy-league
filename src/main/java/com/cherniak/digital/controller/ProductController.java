package com.cherniak.digital.controller;

import com.cherniak.digital.dto.PriceDto;
import com.cherniak.digital.model.Price;
import com.cherniak.digital.service.PriceService;
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

  @GetMapping
  public List<PriceDto> getAllByDate(
      @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
    List<Price> prices = priceService.getList(date);
    return prices.stream().map(PriceDto::new).collect(Collectors.toList());
  }
}
