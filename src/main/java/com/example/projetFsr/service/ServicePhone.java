package com.example.projetFsr.service;

import com.example.projetFsr.model.PhoneNumber;
import com.example.projetFsr.repository.PhoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ServicePhone {
    @Autowired
    PhoneRepository phoneRepository;
    public Boolean createNumberPhone(PhoneNumber phone) {
        return phoneRepository.createNumberPhone(phone);
    }
    public Set<PhoneNumber> getAllNumbers() {
        return phoneRepository.getAllPhones();
    }

    public PhoneNumber getNumberByID(Integer id) {
        return phoneRepository.getPhoneByID(id);
    }

}
