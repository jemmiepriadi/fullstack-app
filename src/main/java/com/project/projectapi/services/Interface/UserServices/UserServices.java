package com.project.projectapi.services.Interface.UserServices;

import com.project.projectapi.model.entities.User;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public interface UserServices {
    User findByEmailAndPassword(String email, String password);

    boolean isPasswordMatch(User user, String password);

    User registerEmail(String email, String password, ZonedDateTime createdAt);
}
