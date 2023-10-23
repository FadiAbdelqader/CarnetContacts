package com.example.projetFsr.repository;

import com.example.projetFsr.configuration.JpaUtil;
import com.example.projetFsr.model.Adress;
import com.example.projetFsr.model.Contact;
import com.example.projetFsr.model.ContactGroup;
import com.example.projetFsr.model.PhoneNumber;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.HashSet;
import java.util.Set;

public class ContactRepository implements IContact {
    @Override
    public boolean addContact(String lastname, String firstname, String email) {

        boolean success = false;
        try {
            EntityManager em = JpaUtil.getEmf().createEntityManager();
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            Contact contact = new Contact(lastname, firstname, email);
            Contact ethan = new Contact("riahi", "ethan", "eee");
            Set<Contact> contacts = new HashSet<Contact>();
            contacts.add(contact);
            contacts.add(ethan);
            ContactGroup cg1 = new ContactGroup("MIAGE");
            ContactGroup cg2 = new ContactGroup("MIAGE2");
            Set<ContactGroup> cgs = new HashSet<ContactGroup>();
            cgs.add(cg1);
            cgs.add(cg2);
            contact.setCg(cgs);
            cg1.setContact(contacts);
            Adress ad = new Adress("Avenue de la Republique", "Nanterre", "92000", "France");
            PhoneNumber ph_home = new PhoneNumber("home", "0123456789");
            ph_home.setContact(contact);
            PhoneNumber ph_port = new PhoneNumber("portable", "0612345678");
            ph_port.setContact(contact);
            Set<PhoneNumber> phs = new HashSet<PhoneNumber>();
            phs.add(ph_home);
            phs.add(ph_port);
            contact.setAdress(ad);
            ad.setContact(contact);
            contact.setPhones(phs);
            em.persist(contact);
            //em.persist(cg);
            tx.commit();
            em.close();
            success = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return success;
    }
}












