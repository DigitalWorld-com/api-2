package com.digitalworlds.grupo2.api.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("grupo2/api")
public class GreetingController {

    @GetMapping("/greeting")
    public String greeting() {
        return "Saludo desde el grupo 2!";
    }
}
