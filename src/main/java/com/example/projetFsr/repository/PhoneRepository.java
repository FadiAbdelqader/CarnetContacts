package com.example.projetFsr.repository;

import com.example.projetFsr.configuration.JpaUtil;
import com.example.projetFsr.model.ContactGroup;
import com.example.projetFsr.model.PhoneNumber;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class PhoneRepository {

    @Autowired
    PhoneNumber phoneNumber;
    public Boolean createNumberPhone(PhoneNumber phone) {
        boolean success = false;
        try {
            EntityManager em = JpaUtil.getEmf().createEntityManager();
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            em.persist(phone);
            tx.commit();
            em.close();
            success = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }

    public boolean deletePhoneById(Integer id) {
        boolean success = false;
        PhoneNumber phone = null;
        try {
            EntityManager em = JpaUtil.getEmf().createEntityManager();
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            TypedQuery<PhoneNumber> query = em.createQuery("SELECT pn FROM PhoneNumber pn WHERE pn.idPhoneNumber = :id", PhoneNumber.class);
            query.setParameter("id", id);
            phone = query.getSingleResult();
            if (phone != null) {
                em.remove(phone); // Suppression de l'entité
                tx.commit();
                success = true;
            } else {
                System.out.println("Aucun groupe avec l'ID " + phone.getIdPhoneNumber() + " n'a été trouvé.");
            }
            em.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }


    public PhoneNumber getPhoneByID(Integer id) {
        PhoneNumber phone = null;
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            TypedQuery<PhoneNumber> query = em.createQuery("SELECT pn FROM PhoneNumber pn WHERE pn.idPhoneNumber = :id", PhoneNumber.class);
            query.setParameter("id", id);
            phone = query.getSingleResult();
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
            return phone;
        }
    }

    public Set<PhoneNumber> getAllPhones() {
        Set<PhoneNumber> phones = null;
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            TypedQuery<PhoneNumber> query = em.createQuery("SELECT pn FROM PhoneNumber pn", PhoneNumber.class);
            List<PhoneNumber> phonesList = query.getResultList();
            phones = new HashSet<>(phonesList);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
            return phones;
        }
    }

    public void modifyPhone(PhoneNumber newPhone) {
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Query updateQuery = em.createQuery("UPDATE PhoneNumber pn SET pn.phoneNumber = :newPn WHERE pn.idPhoneNumber = :oldID");
            updateQuery.setParameter("newPn", newPhone.getPhoneNumber());
            updateQuery.setParameter("oldID", newPhone.getIdPhoneNumber());
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