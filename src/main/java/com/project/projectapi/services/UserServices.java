package com.project.projectapi.services;

import com.project.projectapi.model.entities.User;
import com.project.projectapi.repos.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserServices {
    @Autowired
    private UserRepository customerRepository;

    public User save(User Customer){
        return customerRepository.save(Customer);
    }

    public User getById(long id){
        return customerRepository.findById(id).get();
    }

    public Iterable<User> findAll(){
        return customerRepository.findAll();
    }

    public void delete(long id){
        customerRepository.deleteById(id);
    }
}
