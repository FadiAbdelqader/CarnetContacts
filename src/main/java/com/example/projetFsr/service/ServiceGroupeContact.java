package com.example.projetFsr.service;

import com.example.projetFsr.model.ContactGroup;
import com.example.projetFsr.repository.ContactGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
@Service("groupeContact")
public class ServiceGroupeContact {
    @Autowired
    ContactGroupRepository contactGroupRepository;
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
        return contactGroupRepository.getGroupById(cg);
    }

    public boolean deleteGroupByName(ContactGroup cg){
        return contactGroupRepository.deleteGroupByName(cg);
    }

    public void modifyGroup(ContactGroup cg, String newName){
        contactGroupRepository.modifyGroup(cg, newName);
    }

}
