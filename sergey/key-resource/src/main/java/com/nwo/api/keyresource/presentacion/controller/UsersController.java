package com.nwo.api.keyresource.presentacion.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UsersController {

  @GetMapping("/status/check")
  public String status() {
    return "working";
  }
}
