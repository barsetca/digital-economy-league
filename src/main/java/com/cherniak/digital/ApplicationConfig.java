package com.cherniak.digital;

import com.cherniak.digital.service.ProductService;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@Slf4j
public class ApplicationConfig {

  private static ProductService productService;
  private static final String LOAD_DIR_PATH = "C:/newprojects/digital-economy-league/upload";
  private static final long CSV_SCAN_TIMEOUT = 1000L;


  @Autowired
  public void setProductService(ProductService productService) {
    ApplicationConfig.productService = productService;
  }

  public static void main(String[] args) {
    SpringApplication.run(ApplicationConfig.class, args);
    new Thread(() -> {
      try {
        new CsvParser(productService, LOAD_DIR_PATH, CSV_SCAN_TIMEOUT).load();
      } catch (IOException e) {
        log.error("Щшибка загрузки из директории {} с сообщением: {}", LOAD_DIR_PATH,
            e.getMessage());
      }
    }).start();
  }
}
