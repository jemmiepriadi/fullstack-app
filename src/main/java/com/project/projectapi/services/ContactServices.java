package com.project.projectapi.services;

import com.project.projectapi.model.entities.Contact;
import com.project.projectapi.repos.ContactRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ContactServices {
    @Autowired
    private ContactRepository contactRepository;

    public Contact save(Contact contact){
        return contactRepository.save(contact);
    }

    public Contact getById(long id){
        return contactRepository.findById(id).get();
    }

    public Iterable<Contact> findAll(){
        return contactRepository.findAll();
    }

    public void delete(long id){
         contactRepository.deleteById(id);
    }
}
