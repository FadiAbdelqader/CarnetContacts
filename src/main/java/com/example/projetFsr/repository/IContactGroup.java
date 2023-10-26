package com.example.projetFsr.repository;

import com.example.projetFsr.model.ContactGroup;

import java.util.Set;

public interface IContactGroup {

    public boolean createGroup(ContactGroup cg);

    boolean deleteGroupById(ContactGroup contactGroup);

    boolean deleteGroupByName(ContactGroup contactGroup);


    ContactGroup getGroupById(long idGroup);

    ContactGroup getGroupByGroupName(ContactGroup contactGroup);

    Set<ContactGroup> getAllGroups();

    void modifyGroup(ContactGroup contactGroup, String oldName);
}
