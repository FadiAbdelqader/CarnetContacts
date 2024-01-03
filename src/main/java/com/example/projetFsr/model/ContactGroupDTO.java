package com.example.projetFsr.model;

import org.antlr.v4.runtime.misc.NotNull;

public class ContactGroupDTO {
    long idGroup;
    long idContact;
    String firstName;
    String lastName;
    String email;
    Integer number;
    String street;
    String city;
    String zip;
    String country;
    String phoneKind;
    String phoneNumber;
    String groupName;

    public ContactGroupDTO() {
    }

    public ContactGroupDTO(long idGroup, long idContact, String firstName, String lastName, String email, Integer number, String street, String city, String zip, String country, String phoneKind, String phoneNumber, String groupName) {
        this.idGroup = idGroup;
        this.idContact = idContact;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.number = number;
        this.street = street;
        this.city = city;
        this.zip = zip;
        this.country = country;
        this.phoneKind = phoneKind;
        this.phoneNumber = phoneNumber;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }


}
