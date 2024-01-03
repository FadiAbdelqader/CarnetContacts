package com.example.projetFsr.controller;

import com.example.projetFsr.model.Contact;
import com.example.projetFsr.model.ContactGroup;
import com.example.projetFsr.model.ContactGroupDTO;
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

    @RequestMapping(method = RequestMethod.PUT,value = "/deletegroupbyid")
    public boolean deleteGroupByName(@RequestParam long idGroup){
        return serviceGroupeContact.deleteGroupById(idGroup);
    }

    @RequestMapping(method = RequestMethod.PUT,value = "/updategroup")
    public void modifyGroup(@RequestBody ContactGroup contactGroup){
        serviceGroupeContact.modifyGroup(contactGroup);
    }

    @PostMapping("/addContact")
    public void addContacts(@RequestParam Integer idGroup, @RequestParam List<Integer> idContacts){
        serviceGroupeContact.addContact(idGroup, idContacts);
    }
    @GetMapping("/getContactsByGroupId")
    public List<ContactGroupDTO> getContactsByGroupId(@RequestParam long groupId){
        return serviceGroupeContact.getContactsByGroupId(groupId);
    }


    @DeleteMapping("/removeContact")
    public void removeContact(@RequestParam Integer idGroup, @RequestParam Integer idContact){
        serviceGroupeContact.removeContact(idGroup,idContact);
    }
}