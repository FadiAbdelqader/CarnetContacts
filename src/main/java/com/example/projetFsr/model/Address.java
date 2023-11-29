package com.example.projetFsr.model;

import jakarta.persistence.*;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name = "adresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAddresse;

    @NotNull
    private Integer number;

    @NotNull
    private String street;
    @NotNull
    private String city;
    @NotNull
    private String zip;
    @NotNull
    private String country;

    @OneToOne(mappedBy = "address")
    private Contact contact;

    public Address() {
    }

    public Address(Integer idAddresse, Integer number, String street, String city, String zip, String country, Contact contact) {
        this.idAddresse = idAddresse;
        this.number = number;
        this.street = street;
        this.city = city;
        this.zip = zip;
        this.country = country;
        this.contact = contact;
    }

    public Integer getIdAddresse() {
        return idAddresse;
    }

    public void setIdAdresse(Integer idAdresse) {
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

    public void setIdAddresse(Integer idAddresse) {
        this.idAddresse = idAddresse;
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

