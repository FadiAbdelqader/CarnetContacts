package com.example.projetFsr.repository;

import com.example.projetFsr.model.Contact;

public interface IContact {
    public boolean addContact(String lastname, String firstname, String email);

    void deleteContact(long idContact);

    Contact getContact(long idContact);

    void modifyContact(long idContact, String fname, String lname, String email);
}
