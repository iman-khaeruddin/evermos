package com.evermos.onlinestore.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
  
  public static final Logger logger = LoggerFactory.getLogger(HomeController.class);
  
  @GetMapping("/")
  public String index() {
    logger.info("Call Home API");
    return "Welcome to evermos-onlinestore API Service";
  }
  
}
