package com.example.springboot.controller;

import com.example.springboot.common.Result;
import com.example.springboot.controller.request.UserPageRequest;
import com.example.springboot.entity.User;
import com.example.springboot.service.IUserSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin
@RequestMapping("/user")
@RestController //标识该类暴露出的API为rest风格接口，以js格式返回数据
public class UserController {

    @Autowired
    IUserSevice userSevice;

    @PostMapping("/save")
    public Result save(@RequestBody User user){
        userSevice.save(user);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        userSevice.deleteById(id);
        return Result.success();
    }

    @PutMapping("/update")
    public Result update(@RequestBody User user){
        userSevice.update(user);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id){
        User user = userSevice.getById(id);
        return Result.success(user);
    }

    @GetMapping("/list")
    public Result list(){
        List<User> users = userSevice.list();
        return Result.success(users);
    }

    @GetMapping("/page")
    public Result page(UserPageRequest userPageRequest){
        return Result.success(userSevice.page(userPageRequest));
    }
}
