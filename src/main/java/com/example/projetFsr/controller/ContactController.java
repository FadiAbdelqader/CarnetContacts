package com.example.projetFsr.controller;

import com.example.projetFsr.model.Contact;
import com.example.projetFsr.model.ContactDTO;
import com.example.projetFsr.model.PhoneNumber;
import com.example.projetFsr.service.ServiceContact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/")
public class ContactController {

    @Autowired
    ServiceContact serviceContact;
    @PostMapping("/createcontact")
    public Boolean createContact(@RequestBody Contact contact){
        Integer idContact = contact.getIdContact();
        contact.setIdContact(idContact);
        return serviceContact.createContact(contact);
    }

    @GetMapping("/getContactInfo")
    public List<ContactDTO> getContactInfo(@RequestParam Integer idContact){
        return serviceContact.getContactInfo(idContact);
    }
}
