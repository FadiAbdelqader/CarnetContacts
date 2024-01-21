package com.example.projetFsr.controller;

import com.example.projetFsr.model.Contact;
import com.example.projetFsr.model.ContactGroup;
import com.example.projetFsr.model.PhoneNumber;
import com.example.projetFsr.service.ServicePhone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin("*")
@RequestMapping("/")
public class PhoneController {
    @Autowired
    ServicePhone servicePhone;
    @PostMapping("/createphone")
    public Boolean createNumberPhone(@RequestBody PhoneNumber phone){
        return servicePhone.createNumberPhone(phone);
    }

    @GetMapping("/getphones")
    public String getGroups(){
        return servicePhone.getAllNumbers().toString();
    }

    @GetMapping("/getphonebyid")
    public String getPhoneByID(@RequestParam Integer idPhoneNumber){
        return servicePhone.getNumberByID(idPhoneNumber).toString();
    }

    @DeleteMapping("/deletephone")
    public boolean deletePhone(@RequestParam Integer idPhoneNumber){
        return servicePhone.deletePhone(idPhoneNumber);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/modifyphone")
    public void modifyPhone(@RequestBody PhoneNumber phoneNumber){
        System.out.println("tostr : " + phoneNumber.getIdPhoneNumber() + " " + phoneNumber.getPhoneNumber());
        servicePhone.modifyPhone(phoneNumber);
    }
}