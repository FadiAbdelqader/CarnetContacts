package com.example.projetFsr.model;

public class ContactGroupDTO {
    long idGroup;
    long idContact;
    String firstName;
    String lastName;
    String groupName;

    public ContactGroupDTO() {
    }

    public ContactGroupDTO(long idGroup, long idContact, String firstName, String lastName, String groupName) {
        this.idGroup = idGroup;
        this.idContact = idContact;
        this.firstName = firstName;
        this.lastName = lastName;
        this.groupName = groupName;
    }

    public long getIdGroup() {
        return idGroup;
    }

    public void setIdGroup(long idGroup) {
        this.idGroup = idGroup;
    }

    public long getIdContact() {
        return idContact;
    }

    public void setIdContact(long idContact) {
        this.idContact = idContact;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public String toString() {
        return "ContactGroupDTO{" +
                "idGroup=" + idGroup +
                ", idContact=" + idContact +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", groupName='" + groupName + '\'' +
                '}';
    }
}
