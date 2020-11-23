package com.cherniak.digital;

import static java.lang.Thread.sleep;
import static java.util.stream.Collectors.toList;

import com.cherniak.digital.dto.ProductPriceDto;
import com.cherniak.digital.model.Price;
import com.cherniak.digital.model.Product;
import com.cherniak.digital.service.ProductService;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ParseDate;
import org.supercsv.cellprocessor.ParseDouble;
import org.supercsv.cellprocessor.ParseLong;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;


@Component
@NoArgsConstructor
@Slf4j
public class CsvParser {

  private ProductService productService;
  private String dir;
  private long scanTimeout;

  private List<Path> dirContent;
  private List<Path> newContent;

  public CsvParser(ProductService productService, String dir, long scanTimeout) {
    this.productService = productService;
    this.dir = dir;
    this.scanTimeout = scanTimeout;
    dirContent = new CopyOnWriteArrayList<>();
    newContent = new CopyOnWriteArrayList<>();
  }

  public void load() throws IOException {

    Path path = Paths.get(dir);
    dirContent = Files.list(path).collect(toList());
    dirContent.forEach(this::loadDates);

    while (true) {
      try {
        sleep(scanTimeout);
      } catch (InterruptedException e) {
        log.error("Ошибка обработки потока {}", e.getMessage());
      }
      newContent = Files.list(path).collect(toList());
      checkNewFile();
      dirContent = Files.list(path).collect(toList());


    }
  }

  private void checkNewFile() {
    newContent = newContent.stream().filter(p -> !dirContent.contains(p))
        .collect(toList());
    if (newContent.isEmpty()) {
      return;
    }
    newContent.forEach(this::loadDates);
  }

  private void loadDates(Path path) {
    String simpleFileName = path.getFileName().toString();
    log.info("Начало загрузки файла {}", simpleFileName);
    AtomicInteger count = new AtomicInteger(0);
    try {
      ICsvBeanReader csvBeanReader = new CsvBeanReader(new FileReader(path.toString()),
          CsvPreference.EXCEL_NORTH_EUROPE_PREFERENCE);

      String[] mapping = new String[]{"productId", "name", "priceId", "price", "date"};

      CellProcessor[] procs = getProcessors();
      ProductPriceDto productPriceDto;

      while ((productPriceDto = csvBeanReader.read(ProductPriceDto.class, mapping, procs))
          != null) {
        count.incrementAndGet();
        Product product = toProduct(productPriceDto);
        Price price = toPrice(productPriceDto);
        price.setProduct(product);
        product.getPrices().add(price);
        try {
          productService.saveOrUpdate(product);
        } catch (Exception e) {
          log.error("Ошибка загрузки из файла {} № строки {} продукт {} с сообщением: {}",
              simpleFileName, count, productPriceDto, e.getMessage());
          count.decrementAndGet();
        }

      }
      log.info("Загрузка данных из файла {} завершена. Загружено строк {}", simpleFileName, count);
      csvBeanReader.close();
    } catch (Exception e) {
      log.error("Ошибка загрузки из файла {} с сообщением: {}", simpleFileName, e.getMessage());
    }
  }

  private CellProcessor[] getProcessors() {
    return new CellProcessor[]{
        new Optional(new ParseLong()),
        new NotNull(),
        new Optional(new ParseLong()),
        new NotNull(new ParseDouble()),
        new Optional(new ParseDate("yyyy-MM-dd"))
    };
  }

  private Product toProduct(ProductPriceDto productPriceDto) {
    return new Product(productPriceDto.getProductId(), productPriceDto.getName());
  }

  private Price toPrice(ProductPriceDto productPriceDto) {
    return new Price(productPriceDto.getPriceId(), productPriceDto.getPrice(),
        new java.sql.Date(productPriceDto.getDate().getTime()).toLocalDate());
  }
}
