package com.example.projetFsr.repository;


import com.example.projetFsr.model.ContactGroup;

public class GroupModificationDTO {
    private ContactGroup cg;
    private String newName;

    public ContactGroup getCg() {
        return cg;
    }

    public void setCg(ContactGroup cg) {
        this.cg = cg;
    }

    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }
}
