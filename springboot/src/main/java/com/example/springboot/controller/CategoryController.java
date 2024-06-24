package com.example.springboot.controller;

import com.example.springboot.common.Result;
import com.example.springboot.controller.request.AdminPageRequest;
import com.example.springboot.controller.request.CategoryPageRequest;
import com.example.springboot.entity.Category;
import com.example.springboot.service.ICategorySevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin
@RequestMapping("/category")
@RestController //标识该类暴露出的API为rest风格接口，以js格式返回数据
public class CategoryController {

    @Autowired
    ICategorySevice categorySevice;

    @PostMapping("/save")
    public Result save(@RequestBody Category obj){
        categorySevice.save(obj);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        categorySevice.deleteById(id);
        return Result.success();
    }

    @PutMapping("/update")
    public Result update(@RequestBody Category obj){
        categorySevice.update(obj);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id){
        Category obj = categorySevice.getById(id);
        return Result.success(obj);
    }

    @GetMapping("/list")
    public Result list(){
        List<Category> list = categorySevice.list();
        return Result.success(list);
    }

    @GetMapping("/tree")
    public Result tree(){
        List<Category> tree = categorySevice.tree();
        return Result.success(tree);
    }

    @GetMapping("/page")
    public Result page(CategoryPageRequest pageRequest){
        return Result.success(categorySevice.page(pageRequest));
    }
}
