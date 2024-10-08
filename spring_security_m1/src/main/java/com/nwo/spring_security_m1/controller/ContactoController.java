package com.nwo.spring_security_m1.controller;

import com.nwo.spring_security_m1.repository.ContactoRepository;
import com.nwo.spring_security_m1.repository.entity.Contacto;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/contactos")
@AllArgsConstructor
public class ContactoController {

  private final ContactoRepository contactoRepository;

  @GetMapping
  public List<Contacto> contactoList(){return contactoRepository.findAll(); }
}
