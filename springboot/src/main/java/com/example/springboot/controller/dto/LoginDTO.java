package com.example.springboot.controller.dto;

import lombok.Data;

@Data
public class LoginDTO {
    private Integer id;
    private String name;
    private String keynum;
    private String cardnum;
    private String token;
    private Integer isAuth;
}
