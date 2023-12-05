package com.example.projetFsr.repository;

import com.example.projetFsr.configuration.JpaUtil;
import com.example.projetFsr.model.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
            Set<PhoneNumber> phones = contact.getPhones();
            contact.setPhones(phones);

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

    public List<ContactDTO> getContactInfo(Integer idContact) {
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        try {
            String jpql = "SELECT new com.example.projetFsr.model.ContactDTO(c.idContact, c.firstName, c.lastName, c.email, a.number, a.street, a.city, a.zip, a.country) " +
                    "FROM Contact c " +
                    "LEFT JOIN c.address a " +
                    "WHERE c.idContact = :idContact";

            TypedQuery<ContactDTO> query = em.createQuery(jpql, ContactDTO.class);
            query.setParameter("idContact", idContact);

            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public void deleteContact(Integer idContact) {
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            // Récupérer l'idAdresse associé au contact
            Query getAddressIdQuery = em.createQuery("SELECT a.idAddresse FROM Address a WHERE a.contact.idContact = :idContact", Integer.class);
            getAddressIdQuery.setParameter("idContact", idContact);
            Integer idAdresse = (Integer) getAddressIdQuery.getSingleResult();
            System.out.println("idAdresse :"+idAdresse);
            // Suppression des numéros de téléphone associés
            Query deletePhones = em.createQuery("DELETE FROM PhoneNumber p WHERE p.contact.idContact = :idContact");
            deletePhones.setParameter("idContact", idContact);
            deletePhones.executeUpdate();

            // Suppression du contact
            Query deleteContact = em.createQuery("DELETE FROM Contact c WHERE c.idContact = :idContact");
            deleteContact.setParameter("idContact", idContact);
            deleteContact.executeUpdate();

            // Suppression de l'adresse associée en utilisant l'idAdresse récupéré
            if (idAdresse != null) {
                Query deleteAddress = em.createQuery("DELETE FROM Address a WHERE a.idAddresse = :idAdresse");
                deleteAddress.setParameter("idAdresse", idAdresse);
                deleteAddress.executeUpdate();
            }



            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public void updateContact(Contact contact) {
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            Query query = em.createQuery("UPDATE Contact c SET c.firstName = :firstName, c.lastName = :lastName, c.email = :email WHERE c.idContact = :idContact");
            query.setParameter("firstName", contact.getFirstName());
            query.setParameter("lastName", contact.getLastName());
            query.setParameter("email", contact.getEmail());
            query.setParameter("idContact", contact.getIdContact());

            query.executeUpdate();

            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }


}












