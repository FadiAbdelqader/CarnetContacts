package com.example.projetFsr.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="contacts")
public class Contact {

    private String firstName;
    private String lastName;
    private String email;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long idContact;

    @OneToOne(cascade=CascadeType.PERSIST)
    @JoinColumn(name="idAdresse")
    Adress address=null;

    @OneToMany(cascade=CascadeType.ALL, mappedBy="contact")
    Set<PhoneNumber> phones =new HashSet<PhoneNumber>();

    @ManyToMany(cascade=CascadeType.PERSIST)
    @JoinTable(name="CTC_GRP", joinColumns=@JoinColumn(name="CTC_ID"), inverseJoinColumns=@JoinColumn(name="GRP_ID"))
    private Set<ContactGroup> cg=new HashSet<>();

    public Contact(){
    }


    public Contact(String firstName, String lastName, String email, long idContact) {
        this(firstName, lastName, email);
        this.idContact = idContact;
    }


    public Contact(String firstName, String lastName, String email) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }


    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getFirstName(){
        return firstName;
    }

    public void setFirstName(String firstname){
        this.firstName = firstname;
    }


    public String getLastName(){
        return lastName;
    }

    public void setLastName(String lastname){
        this.lastName = lastname;
    }

    public long getIdContact() {
        return idContact;
    }

    public void setIdContact(long idContact) {
        this.idContact = idContact;
    }


    public Adress getAdress() {
        return address;
    }


    public void setAdress(Adress adresse) {
        this.address = adresse;
    }


    public Set<PhoneNumber> getPhones() {
        return phones;
    }


    public void setPhones(Set<PhoneNumber> phones) {
        this.phones = phones;
    }


    public Set<ContactGroup> getCg() {
        return cg;
    }


    public void setCg(Set<ContactGroup> cg) {
        this.cg = cg;
    }
}
