package com.example.projetFsr.model;
import jakarta.persistence.*;
import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name="phoneNumbers")
public class PhoneNumber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPhoneNumber;

    private String phoneKind;
    private String phoneNumber;

    // Relation ManyToOne avec Contact
    @ManyToOne
    @JoinColumn(name = "contact_id") // Assurez-vous que le nom de la colonne correspond à votre schéma de base de données
    private Contact contact;

    public PhoneNumber() {
        // Constructeur par défaut
    }

    public PhoneNumber(String phoneKind, String phoneNumber) {
        this.phoneKind = phoneKind;
        this.phoneNumber = phoneNumber;
    }

    // Getters et Setters

    public Long getIdPhoneNumber() {
        return idPhoneNumber;
    }

    public void setIdPhoneNumber(Long id) {
        this.idPhoneNumber = id;
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