package com.example.projetFsr.repository;

import com.example.projetFsr.configuration.JpaUtil;
import com.example.projetFsr.model.Address;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.springframework.stereotype.Repository;

@Repository
public class AddressRepository {
    public Boolean createAddress(Address address) {
        boolean success = false;
        try {
            EntityManager em = JpaUtil.getEmf().createEntityManager();
            EntityTransaction tx = em.getTransaction();
            tx.begin();

            em.persist(address);

            tx.commit();
            em.close();
            success = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return success;
    }
}
