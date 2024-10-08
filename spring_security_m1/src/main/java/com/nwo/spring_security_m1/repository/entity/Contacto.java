package com.nwo.spring_security_m1.repository.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Contacto {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long idcontacto;

  private String nombre;

  @Column(name = "fechanac")
  private LocalDate fechaNacimiento;

  private String celular;
  private String email;

}
