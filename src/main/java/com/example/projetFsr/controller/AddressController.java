package com.example.projetFsr.controller;

import com.example.projetFsr.model.Address;
import com.example.projetFsr.model.PhoneNumber;
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
    public Boolean createAddress(@RequestBody Address address) {
        return serviceAddress.createAddress(address);
    }

    @GetMapping("/getaddressbyid")
    public String getAddressByID(@RequestParam Integer idAddress) {
        return serviceAddress.getAddressByID(idAddress).toString();
    }
    @DeleteMapping("/deleteaddressbyid") //supprimer d'abord la clé externe se trouvant dans la table contact
    public boolean deleteAddressByID(@RequestParam Integer idAddress) {
        return serviceAddress.deleteAddressByID(idAddress);
    }

    @GetMapping("/getaddresses")
    public String getAllAddresses() {
        return serviceAddress.getAllAddresses().toString();
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/modifyaddress")
    public void modifyPhone(@RequestBody Address address){
        serviceAddress.modifyAddress(address);
    }


}