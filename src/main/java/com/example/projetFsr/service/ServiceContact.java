package com.example.projetFsr.service;

import com.example.projetFsr.model.ContactDTO;
import com.example.projetFsr.model.PhoneNumber;
import com.example.projetFsr.repository.ContactRepository;
import com.example.projetFsr.model.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class ServiceContact {

	@Autowired
	ContactRepository contactRepository;
	public Boolean createContact(Contact contact) {
		return contactRepository.addContact(contact);
	}

    public List<ContactDTO> getContactInfo(Integer idContact) {
		return contactRepository.getContactInfo(idContact);
    }

    public void deleteContact(Integer idContact) {
		contactRepository.deleteContact(idContact);
    }


	public void updateContact(Contact contact){
		contactRepository.updateContact(contact);
	}
}
