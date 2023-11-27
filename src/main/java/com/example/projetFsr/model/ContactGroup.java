package com.example.projetFsr.model;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="contactGroups")
public class ContactGroup {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer idContact;
    private String groupeName;

    @ManyToMany(mappedBy="cg")
    private Set <Contact> contacts=new HashSet<Contact>();

    public ContactGroup() {

    }

    public ContactGroup(String groupeName) {
        this.groupeName = groupeName;
    }

    public Integer getIdContact() {
        return idContact;
    }
    public void setIdContact(Integer idContact) {
        this.idContact = idContact;
    }
    public String getGroupeName() {
        return groupeName;
    }
    public void setGroupeName(String groupeName) {
        this.groupeName = groupeName;
    }

    public Set<Contact> getContact() {
        return contacts;
    }

    public void setContact(Set<Contact> contacts) {
        this.contacts = contacts;
    }
}
