package com.msimeaor.greeting_service.controller;

import com.msimeaor.greeting_service.configs.GreetingConfiguration;
import com.msimeaor.greeting_service.model.Greeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GreetingController {

  private static final String template = "%s, %s";
  private final AtomicLong counter = new AtomicLong();

  @Autowired
  private  GreetingConfiguration configuration;

  @RequestMapping("/Greeting")
  public Greeting greeting(
          @RequestParam(value = "name", defaultValue = "") String name) {

    if (name.isEmpty()) name = configuration.defaultvalue();
    return new Greeting(counter.incrementAndGet(), String.format(template, configuration.greeting(), name));
  }

}
