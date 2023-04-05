package com.project.projectapi.controller.Auth;

import com.project.projectapi.Exception.BaseException;
import com.project.projectapi.dto.ResponseDTO;
import com.project.projectapi.dto.auth.LoginRequestDTO;
import com.project.projectapi.dto.auth.LoginResponseDTO;
import com.project.projectapi.model.entities.User;
import com.project.projectapi.services.Interface.AuthServices.AuthService;
import com.project.projectapi.services.Interface.CookieManagerServices;
import com.project.projectapi.services.Interface.UserServices.UserServices;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@RestController
@RequestMapping("/api/v1")
public class LoginController {

    private AuthService authService;
    private UserServices userService;

    private final Logger logger;

    private CookieManagerServices cookieManagerServices;

    @Autowired
    public LoginController(AuthService authService, UserServices userService, CookieManagerServices cookieManagerServices) {
        this.authService = authService;
        this.userService = userService;
        this.cookieManagerServices = cookieManagerServices;
        this.logger = LoggerFactory.getLogger(LoginController.class);
    }
    @PostMapping("/login")
    public ResponseDTO<LoginResponseDTO> login(
            @RequestBody LoginRequestDTO loginRequestDTO,
            HttpServletResponse res
    ){
        if (loginRequestDTO.getEmail() == null) {
            throw BaseException.builder()
                    .message("Email can't be empty.")
                    .errorCode("LOGIN-ERROR-EMAIL")
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .build();
        }

        if (loginRequestDTO.getPassword() == null) {
            throw BaseException.builder()
                    .message("Password can't be empty.")
                    .errorCode("LOGIN-ERROR-PASSWORD")
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .build();
        }
        User user = userService.findByEmailAndPassword(loginRequestDTO.getEmail(), loginRequestDTO.getPassword());
        if(user == null) throw BaseException.builder()
                .message("Wrong Password")
                .errorCode("AUTH-ERROR")
                .httpStatus(HttpStatus.BAD_REQUEST)
                .build();

        String token  = authService.createToken(user);
        cookieManagerServices.setToken(res, token);
        return ResponseDTO.ok(LoginResponseDTO.builder().token(token).build());
    }
    @PostMapping("/register")
    public ResponseDTO<User> register(
            @RequestBody(required = false) LoginRequestDTO loginRequestDTO,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime createdAt,
            HttpServletResponse res
    ){
        userService.registerEmail(loginRequestDTO.getEmail(), loginRequestDTO.getPassword(), createdAt);
        return ResponseDTO.ok(null);
    }

}
