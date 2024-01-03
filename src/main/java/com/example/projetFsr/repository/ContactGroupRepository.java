package com.example.projetFsr.repository;

import com.example.projetFsr.configuration.JpaUtil;
import com.example.projetFsr.model.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

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

    public boolean deleteGroupById(long idGroup) {
        boolean success = false;
        try {
            EntityManager em = JpaUtil.getEmf().createEntityManager();
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            ContactGroup contactGroup = em.find(ContactGroup.class, idGroup);
            if (contactGroup != null) {
                em.remove(contactGroup); // Suppression de l'entité
                tx.commit();
                success = true;
            } else {
                System.out.println("Aucun groupe avec l'ID " + idGroup + " n'a été trouvé.");
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

    public void modifyGroup(ContactGroup contactGroup) {
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Query updateQuery = em.createQuery("UPDATE ContactGroup contactGroup SET contactGroup.groupName = :groupName WHERE contactGroup.idGroup = :idGroup");
            updateQuery.setParameter("groupName", contactGroup.getGroupName());
            updateQuery.setParameter("idGroup", contactGroup.getIdGroup());
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

    public List<ContactGroupDTO> getContactsByGroupId(long groupId) {
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        try {
            // JPQL query to fetch contacts of a specific group
            String jpql = "SELECT new com.example.projetFsr.model.ContactGroupDTO(cg.idGroup, c.idContact, c.firstName, c.lastName, c.email, addr.number, addr.street, addr.city, addr.zip, addr.country, phone.phoneKind, phone.phoneNumber, cg.groupName) " +
                    "FROM ContactGroup cg " +
                    "JOIN cg.contacts c " +
                    "LEFT JOIN c.address addr " +
                    "LEFT JOIN c.phones phone " +
                    "WHERE cg.idGroup = :groupId";
            TypedQuery<ContactGroupDTO> query = em.createQuery(jpql, ContactGroupDTO.class);
            query.setParameter("groupId", groupId);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
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
