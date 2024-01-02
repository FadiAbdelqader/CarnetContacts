package com.example.projetFsr.model;

public class GroupDTO {
    long idContact;
    long idGroup;
    String groupName;

    public GroupDTO() {
    }

    public GroupDTO(long idContact, long idGroup, String groupName) {
        this.idContact = idContact;
        this.idGroup = idGroup;
        this.groupName = groupName;
    }

    public GroupDTO(long idGroup, String groupName) {
        this.idGroup = idGroup;
        this.groupName = groupName;
    }
    public long getIdContact() {
        return idContact;
    }

    public void setIdContact(long idContact) {
        this.idContact = idContact;
    }

    public long getIdGroup() {
        return idGroup;
    }

    public void setIdGroup(long idGroup) {
        this.idGroup = idGroup;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public String toString() {
        return "Group{" +
                "idContact=" + idContact +
                ", idGroup=" + idGroup +
                ", groupName='" + groupName + '\'' +
                '}';
    }
}
