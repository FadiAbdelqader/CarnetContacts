package com.example.projetFsr.service;

import com.example.projetFsr.model.Address;
import com.example.projetFsr.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceAddress {

    @Autowired
    AddressRepository addressRepository;

    public Boolean createAddress(Address address) {
        return addressRepository.createAddress(address);
    }
}

