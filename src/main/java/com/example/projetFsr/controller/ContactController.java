package com.example.projetFsr.controller;

import com.example.projetFsr.service.ServiceContact;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContactController {

    @GetMapping("/welcome")
    public String welcome(){
        return "welcome";
    }

    @GetMapping("/add")
    public void add(){
        ServiceContact sc = new ServiceContact();
        sc.createContact("Fadi","Abdelqader","a@gamil.com");
    }
}
