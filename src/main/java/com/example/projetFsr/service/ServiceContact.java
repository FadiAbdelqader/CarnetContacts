package com.example.projetFsr.service;

import com.example.projetFsr.model.PhoneNumber;
import com.example.projetFsr.repository.ContactRepository;
import com.example.projetFsr.model.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceContact {

	@Autowired
	ContactRepository contactRepository;
	public Boolean createContact(Contact contact) {
		return contactRepository.addContact(contact);
	}

	public void deleteContact(Integer idContact) {
		ContactRepository daoc=new ContactRepository();
		daoc.deleteContact(idContact);
	}
	
	public Contact getContactById(Integer idContact) {
		ContactRepository daoc=new ContactRepository();
		return daoc.getContact(idContact);
	}
	
	public void updateContact(Integer idContact, String fname, String lname, String email) {
		ContactRepository daoc=new ContactRepository();
		daoc.modifyContact(idContact, fname, lname, email);
	}
	
}
