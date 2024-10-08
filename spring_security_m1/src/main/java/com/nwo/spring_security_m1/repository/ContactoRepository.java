package com.nwo.spring_security_m1.repository;

import com.nwo.spring_security_m1.repository.entity.Contacto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactoRepository extends JpaRepository<Contacto,Long> {
}
