package com.project.projectapi.services.ServiceImpl.UserServices;

import com.project.projectapi.model.entities.User;
import com.project.projectapi.repos.UserRepository;
import com.project.projectapi.services.Interface.UserServices.UserServices;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;

@Service
@Transactional
public class UserServicesImpl implements UserServices {
    @Autowired
    private UserRepository userRepository;

    public User save(User Customer){
        return userRepository.save(Customer);
    }

    public User getById(long id){
        return userRepository.findById(id).get();
    }

    public Iterable<User> findAll(){
        return userRepository.findAll();
    }

    public void delete(long id){
        userRepository.deleteById(id);
    }

    private boolean isUserSoftDeleted(User user) {
        return (user != null && user.getDeletedAt() != null);
    }

    @Override
    public User findByEmailAndPassword(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user == null) return null;
        if (isUserSoftDeleted(user)) return null;
        if (!isPasswordMatch(user, password)) return null;
        return user;
    }

    @Override
    public boolean isPasswordMatch(User user, String password) {
        return password.equals(user.getPassword());
    }

    @Override
    public User registerEmail(String email, String password, ZonedDateTime createdAt) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
//        user.setCreatedAt(createdAt);
//        user.setDeletedAt(null);
//        user.setUpdatedAt(createdAt);
        userRepository.save(user);
        return null;
    }
}
