package com.example.projetFsr.controller;

import com.example.projetFsr.model.PhoneNumber;
import com.example.projetFsr.service.ServicePhone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/")
public class PhoneController {
    @Autowired
    ServicePhone servicePhone;
    @PostMapping("/createnumberphone")
    public Boolean createNumberPhone(@RequestBody PhoneNumber phone){
        return servicePhone.createNumberPhone(phone);
    }
}
