package com.project.projectapi.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter @Setter
public class ResponseDTO<T> {
    private String message;
    private String code;
    private T data;
    private String token;

    public static <T> ResponseDTO<T> ok(T data){
        ResponseDTO<T> res = new ResponseDTO<>();
        res.setData(data);
        res.setMessage("");
        return res;
    }

}
