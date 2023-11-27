package com.example.projetFsr.controller;

import com.example.projetFsr.model.Address;
import com.example.projetFsr.service.ServiceAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/")
@Validated
public class AddressController {

    @Autowired
    ServiceAddress serviceAddress;

    @PostMapping("/createaddress")
    public Boolean createAddress( @RequestBody Address address) {
        if (validateAddress(address)) {
            return serviceAddress.createAddress(address);
        }
        return false;
    }

    private boolean validateAddress(Address address) {
        if (address != null) {
            if (address.getStreet() != null && !address.getStreet().isEmpty() &&
                    address.getCity() != null && !address.getCity().isEmpty() &&
                    address.getZip() != null && !address.getZip().isEmpty() &&
                    address.getCountry() != null && !address.getCountry().isEmpty()) {
                return true;
            }
        }
        return false;
    }
}

