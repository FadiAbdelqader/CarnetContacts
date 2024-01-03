package com.example.projetFsr.repository;

import com.example.projetFsr.configuration.JpaUtil;
import com.example.projetFsr.model.Address;
import com.example.projetFsr.model.PhoneNumber;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class AddressRepository {

    @Autowired
    Address address;
    public Integer createAddress(Address address) {
        boolean success = false;
        try {
            EntityManager em = JpaUtil.getEmf().createEntityManager();
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            em.persist(address);
            tx.commit();
            success = true;
            System.out.println(success);
            em.close();
            return address.getIdAddress();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public Address getAddressByID(Integer idAddress){
        Address adr = null;
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            TypedQuery<Address> query = em.createQuery("SELECT ad FROM Address ad WHERE ad.idAddress = :id", Address.class);
            query.setParameter("id", idAddress);
            adr = query.getSingleResult();
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
            return adr;
        }
    }

    public boolean deleteAddressById(Integer id) {
        boolean success = false;
        Address adr = null;
        try {
            EntityManager em = JpaUtil.getEmf().createEntityManager();
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            TypedQuery<Address> query = em.createQuery("SELECT ad FROM Address ad WHERE ad.idAddress = :id", Address.class);
            query.setParameter("id", id);
            adr = query.getSingleResult();
            if (adr != null) {
                em.remove(adr); // Suppression de l'entité
                tx.commit();
                success = true;
            } else {
                System.out.println("Aucun groupe avec l'ID " + id + " n'a été trouvé.");
            }
            em.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }

    public Set<Address> getAddresses() {
        Set<Address> adrs = null;
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            TypedQuery<Address> query = em.createQuery("SELECT ad FROM Address ad", Address.class);
            List<Address> adressesList = query.getResultList();
            adrs = new HashSet<>(adressesList);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
            return adrs;
        }
    }

    public void modifyAddress(Address adr){
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Query updateQuery = em.createQuery("UPDATE Address ad SET ad.number = :number, ad.city = :city, ad.country = :country, ad.street = :street, ad.zip = :zip WHERE ad.idAddress = :id");
            updateQuery.setParameter("id", adr.getIdAddress());
            updateQuery.setParameter("city", adr.getCity());
            updateQuery.setParameter("country", adr.getCountry());
            updateQuery.setParameter("number", adr.getNumber());
            updateQuery.setParameter("zip", adr.getZip());
            updateQuery.setParameter("street", adr.getStreet());
            updateQuery.executeUpdate();
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }


}
