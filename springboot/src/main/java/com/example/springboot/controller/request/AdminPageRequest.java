package com.example.springboot.controller.request;

import lombok.Data;

@Data
public class AdminPageRequest extends BaseRequest{
    private String cardnum;
    private String name;
}
