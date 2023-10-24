package com.example.projetFsr.controller;

import com.example.projetFsr.model.ContactGroup;
import com.example.projetFsr.service.ServiceGroupeContact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin("*")
public class ContactGroupController {

    @Autowired
    ServiceGroupeContact cgr;

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }

    @GetMapping("/getGroups")
    public String getGroups(){
        Set<ContactGroup> gl=null;
        gl = cgr.getGroups();
        return gl.toString();
    }

    @GetMapping("/createGroup")
    public String createGroup(String groupName){
        cgr.createGroupeContact("Nanterre");
        return "Groupe created.";

    }

    @RequestMapping(value = "/getgroupbyid/{groupId}")
    public ContactGroup getGroupeById(@PathVariable long groupId){
        return cgr.getGroupById(groupId);
    }

    @RequestMapping(value = "/getgroupbyname/{groupName}")
    public ContactGroup getGroupeByName(@PathVariable String groupName){
        return cgr.getGroupByName(groupName);
    }

    @RequestMapping(value = "/deletegroup/{groupId}")
    public String deleteGroup(@PathVariable long groupId){
        cgr.deleteGroup(groupId);
        return "Groupe " + groupId + " deleted.";
    }

    @RequestMapping(value = "/modifygroup/{oldName}")
    public String modifyGroup(@PathVariable String oldName, String newName){
        cgr.modifyGroup(oldName, "newName");
        return "Groupe " + oldName + " has been renamed : " + newName;
    }

    @GetMapping("/error")
    public String ERROR(){
        return "error";
    }

}
