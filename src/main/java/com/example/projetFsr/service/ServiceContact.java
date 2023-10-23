package com.example.projetFsr.service;

import com.example.projetFsr.repository.ContactRepository;
import com.example.projetFsr.model.Contact;

public class ServiceContact {

	public void createContact(String fname, String lname, String email) {
		ContactRepository daoc=new ContactRepository();
		daoc.addContact(fname, lname, email);
	}

	public void deleteContact(long idContact) {
		ContactRepository daoc=new ContactRepository();
		daoc.deleteContact(idContact);
	}
	
	public Contact getContactById(long idContact) {
		ContactRepository daoc=new ContactRepository();
		return daoc.getContact(idContact);
	}
	
	public void updateContact(long idContact, String fname, String lname, String email) {
		ContactRepository daoc=new ContactRepository();
		daoc.modifyContact(idContact, fname, lname, email);
	}
	
}
