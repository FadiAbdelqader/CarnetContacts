package com.example.projetFsr.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class ContactGroupController {

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }
}
