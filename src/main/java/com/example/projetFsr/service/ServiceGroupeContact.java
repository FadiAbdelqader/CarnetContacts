package com.example.projetFsr.service;

import com.example.projetFsr.model.Contact;
import com.example.projetFsr.model.ContactGroup;
import com.example.projetFsr.repository.ContactGroupRepository;
import com.example.projetFsr.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
@Service("groupeContact")
public class ServiceGroupeContact {
    @Autowired
    ContactGroupRepository contactGroupRepository;
    @Autowired
    private ContactRepository contactRepository;
    public boolean createGroupeContact(ContactGroup cg) {
        return contactGroupRepository.createGroup(cg);
    }
    public Set<ContactGroup> getGroups() {
        return contactGroupRepository.getAllGroups();
    }

    public void deleteGroupById(ContactGroup cg){
        contactGroupRepository.deleteGroupById(cg);
    }


    public ContactGroup getGroupByName(ContactGroup contactGroup){
        return contactGroupRepository.getGroupByGroupName(contactGroup);
    }

    public ContactGroup getGroupById(ContactGroup cg){
        return contactGroupRepository.getGroupById((int) cg.getIdGroup());
    }

    public boolean deleteGroupByName(ContactGroup cg){
        return contactGroupRepository.deleteGroupByName(cg);
    }

    public void modifyGroup(ContactGroup cg, String newName){
        contactGroupRepository.modifyGroup(cg, newName);
    }

    public void addContact(Integer contactGroupID, Integer contactID){
        contactGroupRepository.addContact(contactGroupID,contactID);
    }

    public void removeContact(Integer contactGroupID, Integer contactID){
        contactGroupRepository.removeContact(contactGroupID,contactID);
    }

}
