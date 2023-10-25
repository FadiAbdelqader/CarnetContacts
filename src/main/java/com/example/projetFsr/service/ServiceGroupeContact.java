package com.example.projetFsr.service;

import com.example.projetFsr.model.ContactGroup;
import com.example.projetFsr.repository.ContactGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
@Service("groupeContact")
public class ServiceGroupeContact {
    @Autowired
    ContactGroupRepository cgr;
    public void createGroupeContact(String name) {
        cgr.createGroup(name);
    }
    public Set<ContactGroup> getGroups() {
        return cgr.getAllGroups();
    }

    public void deleteGroup(long idGroup){
        cgr.deleteGroupById(idGroup);
    }

    public ContactGroup getGroupByName(String groupeName){
        return cgr.getGroupByGroupeName(groupeName);
    }

    public ContactGroup getGroupById(long idGroup){
        return cgr.getGroupById(idGroup);
    }

    public boolean deleteGroupByName(String groupName){
        return cgr.deleteGroupByName(groupName);
    }

    public void modifyGroup(String oldName, String newName){
        cgr.modifyGroup(oldName, newName);
    }

}
