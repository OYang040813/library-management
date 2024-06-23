package com.example.springboot.controller;

import com.example.springboot.common.Result;
import com.example.springboot.controller.request.BookPageRequest;
import com.example.springboot.entity.Book;
import com.example.springboot.service.IBookSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin
@RequestMapping("/book")
@RestController //标识该类暴露出的API为rest风格接口，以js格式返回数据
public class BookController {

    @Autowired
    IBookSevice bookService;

    @PostMapping("/save")
    public Result save(@RequestBody Book obj){
        bookService.save(obj);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        bookService.deleteById(id);
        return Result.success();
    }

    @PutMapping("/update")
    public Result update(@RequestBody Book obj){
        bookService.update(obj);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id){
        Book obj = bookService.getById(id);
        return Result.success(obj);
    }

    @GetMapping("/list")
    public Result list(){
        List<Book> list = bookService.list();
        return Result.success(list);
    }

    @GetMapping("/page")
    public Result page(BookPageRequest pageRequest){
        return Result.success(bookService.page(pageRequest));
    }
}
