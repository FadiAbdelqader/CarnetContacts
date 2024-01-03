package com.example.projetFsr.service;

import com.example.projetFsr.model.ContactGroup;
import com.example.projetFsr.model.GroupDTO;
import com.example.projetFsr.repository.ContactGroupRepository;
import com.example.projetFsr.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
@Service("groupeContact")
public class ServiceGroupeContact {
    @Autowired
    ContactGroupRepository contactGroupRepository;
    @Autowired
    private ContactRepository contactRepository;
    public long createGroupe(GroupDTO group) {
        return contactGroupRepository.createGroup(group);
    }
    public List<GroupDTO> getAllGroups() {
        return contactGroupRepository.getAllGroups();
    }

    public boolean deleteGroupById(long idGroup){
       return contactGroupRepository.deleteGroupById(idGroup);
    }


    public ContactGroup getGroupByName(ContactGroup contactGroup){
        return contactGroupRepository.getGroupByGroupName(contactGroup);
    }

    public List<GroupDTO> getGroupById(long idGroup){
        return contactGroupRepository.getGroupById(idGroup);
    }

    public boolean deleteGroupByName(ContactGroup cg){
        return contactGroupRepository.deleteGroupByName(cg);
    }

    public void modifyGroup(ContactGroup contactGroup){
        contactGroupRepository.modifyGroup(contactGroup);
    }

    public void addContact(Integer contactGroupID, List<Integer> contactIDs){
        for(Integer contactID : contactIDs){
            contactGroupRepository.addContact(contactGroupID, contactID);
        }
    }


    public void removeContact(Integer contactGroupID, Integer contactID){
        contactGroupRepository.removeContact(contactGroupID,contactID);
    }

}
