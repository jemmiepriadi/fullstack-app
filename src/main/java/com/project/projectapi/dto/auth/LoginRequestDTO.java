package com.project.projectapi.dto.auth;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class LoginRequestDTO {
    @Getter @Setter
    private String email;

    @Getter @Setter
    private String password;

}
