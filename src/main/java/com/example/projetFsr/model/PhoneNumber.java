package com.example.projetFsr.model;
import jakarta.persistence.*;
import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name="phoneNumbers")
public class PhoneNumber {

    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long idPhoneNumber;

    private String phoneKind;
    private String phoneNumber;

    @ManyToOne(cascade=CascadeType.PERSIST)
    @JoinColumn(name = "idContact")
    private Contact idContact;

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

    public Contact getIdContact() {
        return idContact;
    }

    public void setIdContact(Contact idcontact) {
        this.idContact = idcontact;
    }

    @Override
    public String toString() {
        return "PhoneNumber{" +
                "idPhoneNumber=" + idPhoneNumber +
                ", phoneKind='" + phoneKind + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
