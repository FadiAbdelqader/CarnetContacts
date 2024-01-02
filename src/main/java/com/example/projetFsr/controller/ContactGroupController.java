package com.example.projetFsr.controller;

import com.example.projetFsr.model.ContactGroup;
import com.example.projetFsr.model.GroupDTO;
import com.example.projetFsr.repository.GroupModificationDTO;
import com.example.projetFsr.service.ServiceGroupeContact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;


@RestController
@CrossOrigin("*")
public class ContactGroupController {

    @Autowired
    ServiceGroupeContact serviceGroupeContact;

    @GetMapping("/getAllGroups")
    public List<GroupDTO> getAllGroups(){
        return serviceGroupeContact.getAllGroups();
    }

    @PostMapping("/createGroup")
    public long createGroup(@RequestBody GroupDTO group){
        return serviceGroupeContact.createGroupe(group);
    }

    @GetMapping("/getgroupbyid")
    public List<GroupDTO> getGroupeById(@RequestParam long idGroup){
        return serviceGroupeContact.getGroupById(idGroup);
    }
    @GetMapping("/getgroupbyname")
    public ContactGroup getGroupeByName(@RequestBody ContactGroup contactGroup){
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

        /*
                {
            "cg": {
                "idGroup": 2,
                "groupName": "MIAGE"
            },
            "newName": "MIAGENEW"
        }
         */
    }

    @PostMapping("/addContact")
    public void addContacts(@RequestParam Integer idGroup, @RequestParam List<Integer> idContacts){
        serviceGroupeContact.addContact(idGroup, idContacts);
    }

    @DeleteMapping("/removeContact")
    public void removeContact(@RequestParam Integer idGroup, @RequestParam Integer idContact){
        serviceGroupeContact.removeContact(idGroup,idContact);
    }
}