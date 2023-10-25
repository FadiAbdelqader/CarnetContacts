package com.example.projetFsr.repository;

import com.example.projetFsr.model.Contact;
import com.example.projetFsr.model.ContactGroup;

import java.util.Set;

public interface IContactGroup {

    public boolean createGroup(String groupName);

    boolean deleteGroupById(long idGroup);

    boolean deleteGroupByName(String groupName);


    ContactGroup getGroupById(long idGroup);

    ContactGroup getGroupByGroupeName(String groupName);

    Set<ContactGroup> getAllGroups();

    void modifyGroup(String groupName, String oldName);
}
