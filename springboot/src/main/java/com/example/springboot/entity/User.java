package com.example.springboot.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data; //@Data可以自动生成模板函数。

import java.util.Date;

@Data
public class User {
    private Integer id;
    private String name;
    private String email;
    private String keynum;
    private String phone;
    private String sex;
    private Integer age;
    private String address;
    private String cardnum;
    private Integer IsAuth = 0;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createtime;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date updatetime;
}
