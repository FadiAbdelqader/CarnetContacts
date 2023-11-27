package com.example.projetFsr.repository;

import com.example.projetFsr.model.Contact;

public interface IContact {
    public boolean addContact(String lastname, String firstname, String email);

    void deleteContact(Integer idContact);

    Contact getContact(Integer idContact);

    void modifyContact(Integer idContact, String fname, String lname, String email);
}
