package com.project.projectapi.services.ServiceImpl;

import com.project.projectapi.services.Interface.CookieManagerServices;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

@Service
public class CookieManagerServiceImpl implements CookieManagerServices {
    @Override
    public void setToken(HttpServletResponse response, String token){
        Cookie cookie = new Cookie("auth_token", token);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    @Override
    public String getToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("auth_token")) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
