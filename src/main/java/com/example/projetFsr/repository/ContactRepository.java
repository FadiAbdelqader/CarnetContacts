package com.example.projetFsr.repository;

import com.example.projetFsr.configuration.JpaUtil;
import com.example.projetFsr.model.Contact;
import com.example.projetFsr.model.PhoneNumber;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public class ContactRepository {

    @Autowired
    Contact contact;
    public boolean addContact(Contact contact) {

        boolean success = false;
        try {
            contact.setFirstName(contact.getFirstName());
            contact.setLastName(contact.getLastName());
            contact.setEmail(contact.getEmail());
            contact.setAddress(contact.getAddress());
            contact.setPhones(contact.getPhones());

            EntityManager em = JpaUtil.getEmf().createEntityManager();
            EntityTransaction tx = em.getTransaction();
            tx.begin();

            em.persist(contact);

            tx.commit();
            em.close();
            success = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return success;
    }

    public void deleteContact(Integer idContact) {

    }

    public Contact getContact(Integer idContact) {
        return null;
    }

    public void modifyContact(Integer idContact, String fname, String lname, String email) {

    }
}












