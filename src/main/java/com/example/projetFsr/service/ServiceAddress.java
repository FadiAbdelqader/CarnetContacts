package com.example.projetFsr.service;

import com.example.projetFsr.model.Address;
import com.example.projetFsr.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ServiceAddress {

    @Autowired
    AddressRepository addressRepository;

    public Boolean createAddress(Address address) {
        return addressRepository.createAddress(address);
    }
    public Address getAddressByID(Integer idAddress) {
        return addressRepository.getAddressByID(idAddress);
    }

    public boolean deleteAddressByID(Integer idAddress) {
        return addressRepository.deleteAddressById(idAddress);
    }

    public Set<Address> getAllAddresses(){return addressRepository.getAddresses();}

    public void modifyAddress(Address address){
        addressRepository.modifyAddress(address);
    }


}

