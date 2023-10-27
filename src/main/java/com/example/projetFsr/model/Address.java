package com.example.projetFsr.model;

import jakarta.persistence.*;
import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name = "adresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idAddresse;

    private Integer number;

    private String street;
    private String city;
    private String zip;
    private String country;

    @OneToOne(mappedBy = "address")
    private Contact contact;

    public Address() {
    }

    public Address(String street, String city, String zip, String country, Integer number) {
        this.street = street;
        this.city = city;
        this.zip = zip;
        this.country = country;
        this.number = number;
    }

    public long getIdAddresse() {
        return idAddresse;
    }

    public void setIdAdresse(long idAdresse) {
        this.idAddresse = idAdresse;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    @Override
    public String toString() {
        return "Adress{" +
                "idAdresse=" + idAddresse +
                ", number=" + number +
                ", street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", zip='" + zip + '\'' +
                ", country='" + country + '\'' +
                ", contact=" + contact +
                '}';
    }
}

