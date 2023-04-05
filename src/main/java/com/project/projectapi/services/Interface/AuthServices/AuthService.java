package com.project.projectapi.services.Interface.AuthServices;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.project.projectapi.model.entities.User;

public interface AuthService {
    String createToken(User user);
    User getUserFromToken(String token) throws JWTVerificationException;
}
