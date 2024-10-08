package com.nwo.spring_security_m2.repository.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Setter @Getter
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Null
  private Long id;

  @NotBlank
  private String name;

  @DecimalMin(value = "0.01")
  private BigDecimal price;

}
