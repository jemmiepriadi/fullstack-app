package com.project.projectapi.Exception;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Builder
public class BaseException extends RuntimeException{
    @Getter @Setter
    private String message;
    @Getter @Setter
    private String errorCode;
    @Getter @Setter
    private HttpStatus httpStatus;
    @Getter @Setter
    private Map<String, String> errors;

    @Getter @Setter @Builder.Default
    @JsonIgnore
    private boolean logAsWarning = true;
}
