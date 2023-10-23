package com.example.projetFsr.model;
import jakarta.persistence.*;

@Entity
@Table(name="phoneNumbers")
public class PhoneNumber {

    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long idPhoneNumber;

    private String phoneKind;
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name="id_contact")
    private Contact contact;

    public PhoneNumber() {
        super();
    }

    public PhoneNumber(String phoneKind, String phoneNumber) {
        this.phoneKind = phoneKind;
        this.phoneNumber = phoneNumber;
    }

    public long getIdPhoneNumber() {
        return idPhoneNumber;
    }

    public void setIdPhoneNumber(long idPhoneNumber) {
        this.idPhoneNumber = idPhoneNumber;
    }

    public String getPhoneKind() {
        return phoneKind;
    }

    public void setPhoneKind(String phoneKind) {
        this.phoneKind = phoneKind;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }
}
