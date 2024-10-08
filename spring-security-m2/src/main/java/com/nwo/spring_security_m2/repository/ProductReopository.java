package com.nwo.spring_security_m2.repository;

import com.nwo.spring_security_m2.repository.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductReopository extends JpaRepository<Product, Long> {
}
