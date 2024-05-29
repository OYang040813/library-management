package com.example.springboot.controller;

import com.example.springboot.common.Result;
import com.example.springboot.controller.dto.LoginDTO;
import com.example.springboot.controller.request.AdminPageRequest;
import com.example.springboot.controller.request.KeynumRequest;
import com.example.springboot.controller.request.LoginRequest;
import com.example.springboot.entity.Admin;
import com.example.springboot.service.IAdminSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin
@RequestMapping("/admin")
@RestController //标识该类暴露出的API为rest风格接口，以js格式返回数据
public class AdminController {

    @Autowired
    IAdminSevice adminSevice;

    @PostMapping("/save")
    public Result save(@RequestBody Admin obj){
        adminSevice.save(obj);
        return Result.success();
    }

    @PostMapping("/login")
    public Result login(@RequestBody LoginRequest request){
        LoginDTO loginDTO = adminSevice.login(request);
        return Result.success(loginDTO);
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        adminSevice.deleteById(id);
        return Result.success();
    }

    @PutMapping("/update")
    public Result update(@RequestBody Admin obj){
        adminSevice.update(obj);
        return Result.success();
    }

    @PutMapping("/keynum")
    public Result keynum(@RequestBody KeynumRequest request){
        adminSevice.changeKeynum(request);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id){
        Admin obj = adminSevice.getById(id);
        return Result.success(obj);
    }

    @GetMapping("/list")
    public Result list(){
        List<Admin> list = adminSevice.list();
        return Result.success(list);
    }

    @GetMapping("/page")
    public Result page(AdminPageRequest pageRequest){
        return Result.success(adminSevice.page(pageRequest));
    }
}
