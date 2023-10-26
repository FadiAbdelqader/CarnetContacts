package com.example.projetFsr.repository;

import com.example.projetFsr.configuration.JpaUtil;
import com.example.projetFsr.model.ContactGroup;
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
public class ContactGroupRepository implements IContactGroup{

    @Autowired
    ContactGroup contactGroup;

    @Override
    public boolean createGroup(ContactGroup cg) {
        boolean success = false;
        try {
            EntityManager em = JpaUtil.getEmf().createEntityManager();
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            //contactGroup.setGroupeName(groupName);
            em.persist(cg);
            tx.commit();
            em.close();
            success = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;

    }

    @Override
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

    @Override
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

    @Override
    public ContactGroup getGroupById(long idGroup) {
        ContactGroup contactGroup=null;
        try {
            EntityManager em = JpaUtil.getEmf().createEntityManager();
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            contactGroup = em.find(ContactGroup.class, idGroup);
            em.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contactGroup;
    }

    @Override
    public ContactGroup getGroupByGroupName(ContactGroup cg) {
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        ContactGroup contactGroup = null;
        try {
            tx.begin();
            //TypedQuery<ContactGroup> query = em.createQuery("SELECT cg FROM ContactGroup cg WHERE cg.groupName = :groupName", ContactGroup.class);
            //query.setParameter("groupName", groupNameToFind);
            TypedQuery<ContactGroup> query = em.createQuery("SELECT cg FROM ContactGroup cg", ContactGroup.class);
            List<ContactGroup> groups = query.getResultList();
            //List<ContactGroup> groups = query.getResultList();
            System.out.println("groups : "+ groups);
            for (ContactGroup group : groups) {
                if(group.getGroupName().equals(cg.getGroupName())) {
                    contactGroup = group;
                } else {
                    System.out.println("No ContactGroup found with the name: " + contactGroup.getGroupName());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return contactGroup;
    }

    @Override
    public Set<ContactGroup> getAllGroups() {
        Set<ContactGroup> contactGroups = null;
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            TypedQuery<ContactGroup> query = em.createQuery("SELECT cg FROM ContactGroup cg", ContactGroup.class);
            List<ContactGroup> groupsList = query.getResultList();
            contactGroups = new HashSet<>(groupsList);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
            return contactGroups;
        }
    }

    @Override
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
}
