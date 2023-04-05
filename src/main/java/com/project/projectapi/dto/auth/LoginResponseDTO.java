package com.project.projectapi.dto.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
public class LoginResponseDTO {
    @Getter
    @Setter
    private String token;
}
