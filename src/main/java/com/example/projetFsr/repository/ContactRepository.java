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

    public Integer addContact(Contact contact) {
        boolean success = false;
        try (EntityManager em = JpaUtil.getEmf().createEntityManager()) {
            EntityTransaction tx = em.getTransaction();
            tx.begin();

            Address address = contact.getAddress();
            if (address.getIdAddress() != null) {
                address = em.find(Address.class, address.getIdAddress());
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
        return contact.getIdContact();
    }
    public List<ContactDTO> getAllContact() {
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        try {
            String jpql =
                    "SELECT new com.example.projetFsr.model.ContactDTO(c.idContact, c.firstName, c.lastName, c.email, a.number, a.street, a.city, a.zip, a.country, pn.phoneKind, pn.phoneNumber) " +
                            "FROM Contact c " +
                            "LEFT JOIN c.address a " +
                            "LEFT JOIN c.phones pn "+
                            "ORDER BY c.firstName ASC";;

            TypedQuery<ContactDTO> query = em.createQuery(jpql, ContactDTO.class);


            return query.getResultList();
        } finally {
            em.close();
        }

    }
    public List<ContactDTO> getContactInfo(Integer idContact) {
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        try {
            String jpql =
                    "SELECT new com.example.projetFsr.model.ContactDTO(c.idContact, c.firstName, c.lastName, c.email, a.number, a.street, a.city, a.zip, a.country, pn.phoneKind, pn.phoneNumber) " +
                    "FROM Contact c " +
                    "LEFT JOIN c.address a " +
                    "LEFT JOIN c.phones pn " +
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
            Query getAddressIdQuery = em.createQuery("SELECT a.idAddress FROM Address a WHERE a.contact.idContact = :idContact", Integer.class);
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
                Query deleteAddress = em.createQuery("DELETE FROM Address a WHERE a.idAddress = :idAdresse");
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

    public Contact getContactByID(Integer id){
        Contact ctc = null;
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
                TypedQuery<Contact> query = em.createQuery("SELECT ct FROM Contact ct WHERE ct.idContact = :id", Contact.class);
            query.setParameter("id", id);
            ctc = query.getSingleResult();
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
            return ctc;
        }
    }


    public void updateAContact(ContactDTO contactDTO) {
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            // Mise à jour du contact
            Query queryContact = em.createQuery("UPDATE Contact c SET c.firstName = :firstName, c.lastName = :lastName, c.email = :email WHERE c.idContact = :idContact");
            queryContact.setParameter("firstName", contactDTO.getFirstName());
            queryContact.setParameter("lastName", contactDTO.getLastName());
            queryContact.setParameter("email", contactDTO.getEmail());
            queryContact.setParameter("idContact", contactDTO.getIdContact());
            queryContact.executeUpdate();

            // Mise à jour de l'adresse
            Query queryAddress = em.createQuery("UPDATE Address a SET a.number = :number, a.street = :street, a.city = :city, a.zip = :zip, a.country = :country WHERE a.contact.idContact = :idContact");
            queryAddress.setParameter("number", contactDTO.getNumber());
            queryAddress.setParameter("street", contactDTO.getStreet());
            queryAddress.setParameter("city", contactDTO.getCity());
            queryAddress.setParameter("zip", contactDTO.getZip());
            queryAddress.setParameter("country", contactDTO.getCountry());
            queryAddress.setParameter("idContact", contactDTO.getIdContact());
            queryAddress.executeUpdate();

            // Mise à jour du numéro de téléphone
            Query queryPhone = em.createQuery("UPDATE PhoneNumber p SET p.phoneKind = :phoneKind, p.phoneNumber = :phoneNumber WHERE p.contact.idContact = :idContact");
            queryPhone.setParameter("phoneKind", contactDTO.getPhoneKind());
            queryPhone.setParameter("phoneNumber", contactDTO.getPhoneNumber());
            queryPhone.setParameter("idContact", contactDTO.getIdContact());
            queryPhone.executeUpdate();

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












