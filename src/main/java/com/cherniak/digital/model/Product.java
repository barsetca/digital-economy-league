package com.cherniak.digital.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

@Entity
@NoArgsConstructor
@Data
@Table(name = "products")
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "name")
  @NotBlank
  private String name;

  @OneToMany(mappedBy = "product")
  @Cascade(org.hibernate.annotations.CascadeType.ALL)
  private List<Price> prices = new ArrayList<>();

  public Product(@NotBlank String name) {
    this(null, name);
  }

  public Product(Long id, @NotBlank String name) {
    this.id = id;
    this.name = name;
  }
}

