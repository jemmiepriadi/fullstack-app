package com.project.projectapi.services.Interface;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

@Component
public interface CookieManagerServices {
    void setToken(HttpServletResponse response, String token);
    String getToken(HttpServletRequest httpServletRequest);
}
