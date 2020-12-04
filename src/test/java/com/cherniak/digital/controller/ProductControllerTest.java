package com.cherniak.digital.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.cherniak.digital.dto.FrequencyPriceChangeByDate;
import com.cherniak.digital.dto.FrequencyPriceChangeByProduct;
import com.cherniak.digital.model.Price;
import com.cherniak.digital.model.Product;
import com.cherniak.digital.service.PriceService;
import com.cherniak.digital.service.ProductService;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ProductService service;

  @MockBean
  private PriceService priceService;

  private List<FrequencyPriceChangeByDate> frequencyPriceChangeByDates;

  private List<FrequencyPriceChangeByProduct> frequencyPriceChangeByProducts;

  private static final Long FREQUENCY = 5L;
  private static final String NAME = "Product";

  @BeforeEach
  void setUp() {
    FrequencyPriceChangeByDate byDate = new FrequencyPriceChangeByDate();
    byDate.setDate(LocalDate.now());
    byDate.setFrequency(FREQUENCY);
    frequencyPriceChangeByDates = new ArrayList<>(Collections.singletonList(byDate));
    Mockito.doReturn(frequencyPriceChangeByDates)
        .when(priceService).getFrequencyPriceChangeByDates();

    FrequencyPriceChangeByProduct byProduct = new FrequencyPriceChangeByProduct();
    byProduct.setName(NAME);
    byProduct.setFrequency(FREQUENCY);
    frequencyPriceChangeByProducts = new ArrayList<>(Collections.singletonList(byProduct));
    Mockito.doReturn(frequencyPriceChangeByProducts)
        .when(priceService).getFrequencyPriceChangeByProducts();


  }

  @Test
  void getAllByDate() throws Exception {
    Product product = new Product();
    product.setName(NAME);
    Price price = new Price();
    price.setProduct(product);
    price.setDate(LocalDate.now());
    price.setId(1L);
    price.setPrice(1.00);
    List<Price> prices = new ArrayList<>(Collections.singletonList(price));
    Mockito.doReturn(prices)
        .when(priceService).getList(LocalDate.now());
    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate date = LocalDate.now();
    String formattedDate = date.format(fmt);
    System.out.println(formattedDate);
  mockMvc.perform(MockMvcRequestBuilders.get("/products?date=" + formattedDate)
      .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
      .andExpect(jsonPath("$").isArray())
      .andExpect(jsonPath("$").isNotEmpty())
      .andExpect(jsonPath("$", hasSize(1)))
      .andExpect(jsonPath("$[0].price", is(price.getPrice())))
      .andExpect(jsonPath("$[0].name", is(price.getProduct().getName())));
  }

  @Test
  void getStatistic() {
  }
}