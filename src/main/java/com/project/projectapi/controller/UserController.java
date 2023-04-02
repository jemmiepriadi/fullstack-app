package com.project.projectapi.controller;

import com.project.projectapi.model.entities.User;
import com.project.projectapi.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserServices userServices;

    @PostMapping
    public User create(@RequestBody User Customer) {
        return userServices.save(Customer) ;
    }
    @GetMapping
    public Iterable <User> findALL() {
        return userServices.findAll();
    }
    @GetMapping("/{id}")
    public User findOne (@PathVariable("id") Long id){
        return userServices.getById(id);
    }
    @PutMapping
    public User update(@RequestBody User Customer) {
        return userServices.save(Customer);
    }
}
