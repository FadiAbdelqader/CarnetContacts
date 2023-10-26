package com.example.projetFsr.controller;

import com.example.projetFsr.model.ContactGroup;
import com.example.projetFsr.repository.GroupModificationDTO;
import com.example.projetFsr.service.ServiceGroupeContact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@CrossOrigin("*")
public class ContactGroupController {

    @Autowired
    ServiceGroupeContact serviceGroupeContact;

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }

    @GetMapping("/getGroups")
    public String getGroups(){
        Set<ContactGroup> gl=null;
        gl = serviceGroupeContact.getGroups();
        return gl.toString();
    }

    @PostMapping("/createGroup")
    public boolean createGroup(@RequestBody ContactGroup contactGroup){
        return serviceGroupeContact.createGroupeContact(contactGroup);
    }

    @GetMapping("/getgroupbyid")
    public ContactGroup getGroupeById(@RequestBody ContactGroup contactGroup){
        return serviceGroupeContact.getGroupById(contactGroup.getIdGroup());
    }
    @GetMapping("/getgroupbyname")
    public ContactGroup getGroupeByName(@RequestBody ContactGroup contactGroup){
        System.out.println("groupname : " + contactGroup.getGroupName());
        return serviceGroupeContact.getGroupByName(contactGroup);
    }

    @DeleteMapping(value = "/deletegroupbyid")
    public String deleteGroupByName(@RequestBody ContactGroup contactGroup){
        serviceGroupeContact.deleteGroupById(contactGroup);
        return "Groupe " + contactGroup.getIdGroup() + " deleted.";
    }

    @PatchMapping(value = "/modifygroup")
    public String modifyGroup(@RequestBody GroupModificationDTO groupModificationDTO){
        ContactGroup cg = groupModificationDTO.getCg();
        String newName = groupModificationDTO.getNewName();
        serviceGroupeContact.modifyGroup(cg, newName);
        return "Groupe " + cg.getGroupName() + " has been renamed : " + newName;
    }

}
