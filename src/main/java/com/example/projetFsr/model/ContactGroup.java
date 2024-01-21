package com.example.projetFsr.model;
import jakarta.persistence.*;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
@Component
@Entity
@Table(name="contactGroups")
public class ContactGroup {
    @Id @GeneratedValue(strategy=GenerationType.AUTO)
    private long idGroup;
    private String groupName;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy="cg")
    private Set <Contact> contacts=new HashSet<Contact>();

    public ContactGroup() {

    }

    public void addContact(Contact contact){
        this.contacts.add(contact);
        contact.getCg().add(this);
    }

    public void removeContact(Contact contact){
        if(this.contacts.contains(contact)) {
            this.contacts.remove(contact);
            contact.getCg().remove(this);
        }
    }

    public ContactGroup(String groupeName) {
        this.groupName = groupeName;
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

    public Set<Contact> getContact() {
        return contacts;
    }

    public void setContact(Set<Contact> contacts) {
        this.contacts = contacts;
    }
}