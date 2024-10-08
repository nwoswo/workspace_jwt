package com.nwo.spring_security_m2.controller;

import com.nwo.spring_security_m2.repository.entity.Product;
import com.nwo.spring_security_m2.repository.ProductReopository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

  @Autowired
  private ProductReopository productReopository;

  @GetMapping
  public ResponseEntity<List<Product>>  findAll(){
    List<Product> products = productReopository.findAll();

    if(products !=null && !products.isEmpty()){
      return ResponseEntity.ok(products);
    }

    return ResponseEntity.notFound().build();
  }

  @PostMapping
  public ResponseEntity<Product> createOne(@RequestBody @Valid Product product){
    return ResponseEntity.status(HttpStatus.CREATED).body(
        productReopository.save(product)
    );
  }
}
