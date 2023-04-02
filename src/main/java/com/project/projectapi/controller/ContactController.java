package com.project.projectapi.controller;

import com.project.projectapi.model.entities.Contact;
import com.project.projectapi.services.ContactServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contact")
public class ContactController {

    @Autowired
    private ContactServices contactServices;

    @PostMapping
    public Contact create(@RequestBody Contact Contact) {
        return contactServices.save(Contact) ;
    }
    @GetMapping
    public Iterable <Contact> findALL() {
        return contactServices.findAll();
    }
    @GetMapping("/{id}")
    public Contact findOne (@PathVariable("id") Long id){
        return contactServices.getById(id);
    }
    @PutMapping
    public Contact update(@RequestBody Contact Contact) {
        return contactServices.save(Contact);
    }
}
