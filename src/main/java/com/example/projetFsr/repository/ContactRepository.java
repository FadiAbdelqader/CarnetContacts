package com.example.projetFsr.repository;

import com.example.projetFsr.configuration.JpaUtil;
import com.example.projetFsr.model.Address;
import com.example.projetFsr.model.Contact;
import com.example.projetFsr.model.ContactGroup;
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
        try (EntityManager em = JpaUtil.getEmf().createEntityManager()) {
            EntityTransaction tx = em.getTransaction();
            tx.begin();

            Address address = contact.getAddress();
            if (address.getIdAddresse() != null) {
                address = em.find(Address.class, address.getIdAddresse());
            } else {
                em.persist(address);
            }

            contact.setAddress(address);
            contact.setPhones(contact.getPhones());

            // Persistez le contact
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


    public void deleteContact(Integer idContact) {

    }

    public Contact getContact(Integer idContact) {
        return null;
    }

    public void modifyContact(Integer idContact, String fname, String lname, String email) {

    }
}












