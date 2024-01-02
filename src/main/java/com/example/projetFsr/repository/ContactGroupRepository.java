package com.example.projetFsr.repository;

import com.example.projetFsr.configuration.JpaUtil;
import com.example.projetFsr.model.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class ContactGroupRepository{

    @Autowired
    ContactGroup contactGroup;

    public long createGroup(GroupDTO groupDto) {
        long idGroup = -1; // Valeur par défaut indiquant l'échec
        try {
            EntityManager em = JpaUtil.getEmf().createEntityManager();
            EntityTransaction tx = em.getTransaction();
            tx.begin();

            ContactGroup newGroup = new ContactGroup(groupDto.getGroupName());
            em.persist(newGroup);

            tx.commit();
            idGroup = newGroup.getIdGroup(); // Récupérer l'ID après la persistance

            em.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return idGroup;
    }

    public boolean deleteGroupById(ContactGroup cg) {
        boolean success = false;
        try {
            EntityManager em = JpaUtil.getEmf().createEntityManager();
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            ContactGroup contactGroup = em.find(ContactGroup.class, cg.getIdGroup());
            if (contactGroup != null) {
                em.remove(contactGroup); // Suppression de l'entité
                tx.commit();
                success = true;
            } else {
                System.out.println("Aucun groupe avec l'ID " + cg.getIdGroup() + " n'a été trouvé.");
            }
            em.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }

    public boolean deleteGroupByName(ContactGroup cg) {
        boolean success = false;
        try {
            EntityManager em = JpaUtil.getEmf().createEntityManager();
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            ContactGroup contactGroup = getGroupByGroupName(cg);
            if (contactGroup != null) {
                em.remove(contactGroup); // Suppression de l'entité
                tx.commit();
                success = true;
            } else {
                System.out.println("Le groupe " + cg.getGroupName() + " n'a pas été trouvé.");
            }
            em.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }


    public List<GroupDTO> getGroupById(Long idGroup) {
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        try {
            String jpql =
                    "SELECT new com.example.projetFsr.model.GroupDTO(g.idGroup, g.groupName) " +
                            "FROM ContactGroup g " +
                            "WHERE g.idGroup = :idGroup";

            TypedQuery<GroupDTO> query = em.createQuery(jpql, GroupDTO.class);
            query.setParameter("idGroup", idGroup);

            return query.getResultList();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    public ContactGroup getGroupByGroupName(ContactGroup cg) {
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        ContactGroup contactGroup = null;
        try {
            tx.begin();
            TypedQuery<ContactGroup> query = em.createQuery("SELECT cg FROM ContactGroup cg WHERE cg.groupName = :groupName", ContactGroup.class);
            query.setParameter("groupName", cg.getGroupName());
            contactGroup = query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return contactGroup;
    }

    public List<GroupDTO> getAllGroups() {
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        try {
            String jpql =
                    "SELECT new com.example.projetFsr.model.GroupDTO(g.idGroup, g.groupName) " +
                            "FROM ContactGroup g " +
                            "ORDER BY g.groupName ASC";

            TypedQuery<GroupDTO> query = em.createQuery(jpql, GroupDTO.class);

            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public void modifyGroup(ContactGroup cg, String groupName) {
        ContactGroup contactGroup = getGroupByGroupName(cg);
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Query updateQuery = em.createQuery("UPDATE ContactGroup cg SET cg.groupName = :newGroupName WHERE cg.groupName = :oldGroupName");
            updateQuery.setParameter("newGroupName", groupName);
            updateQuery.setParameter("oldGroupName", cg.getGroupName());
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

    public void addContact(Integer contactGroupID, Integer contactID){
        ContactGroup contactGroup = null;
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        Contact contact = null;
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            contactGroup = em.find(ContactGroup.class,contactGroupID);
            contact = em.find(Contact.class,contactID);
            contactGroup.addContact(contact);
            em.merge(contactGroup);
            tx.commit();
        } catch (Exception e) {
            if(tx.isActive())
                tx.rollback();
        } finally {
            em.close();
        }
    }

    public void removeContact(Integer contactGroupID, Integer contactID){
        ContactGroup contactGroup = null;
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        Contact contact = null;
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            contactGroup = em.find(ContactGroup.class,contactGroupID);
            contact = em.find(Contact.class,contactID);
            contactGroup.removeContact(contact);
            em.merge(contactGroup);
            tx.commit();
        } catch (Exception e) {
            if(tx.isActive())
                tx.rollback();
        } finally {
            em.close();
        }
    }
}
