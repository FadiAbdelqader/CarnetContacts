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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class PhoneRepository {

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

    public boolean deletePhoneById(PhoneNumber phone) {
        boolean success = false;
        try {
            EntityManager em = JpaUtil.getEmf().createEntityManager();
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            PhoneNumber phoneNumber = em.find(PhoneNumber.class, phone.getIdPhoneNumber());
            if (phoneNumber != null) {
                em.remove(phoneNumber); // Suppression de l'entité
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

    public PhoneNumber getPhoneById(PhoneNumber phone) {
        PhoneNumber p=null;
        try {
            EntityManager em = JpaUtil.getEmf().createEntityManager();
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            p = em.find(PhoneNumber.class, phone.getIdPhoneNumber());
            em.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return p;
    }

    public Set<PhoneNumber> getAllGroups() {
        Set<PhoneNumber> phones = null;
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            TypedQuery<PhoneNumber> query = em.createQuery("SELECT pn FROM phoneNumbers pn", PhoneNumber.class);
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

    public void modifyPhone(PhoneNumber pn, PhoneNumber newPhone) {
        PhoneNumber phoneNumber = getPhoneById(pn);
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Query updateQuery = em.createQuery("UPDATE phoneNumbers pn SET pn.phoneNumber = :newPn AND pn.phoneKind = :newPk WHERE pn.idPhoneNumber = :oldGroupName");
            updateQuery.setParameter("newPn", newPhone.getPhoneNumber());
            updateQuery.setParameter("newPk", newPhone.getPhoneKind());
            updateQuery.setParameter("oldGroupName", pn.getIdPhoneNumber());
            int rowsUpdated = updateQuery.executeUpdate();
            tx.commit();
            System.out.println(rowsUpdated + " rows updated.");
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