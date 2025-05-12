package com.twozo.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class DemoController{

    @GetMapping("/hello")
    public String home(){
        return "Hello World form Rest Controller";
    }
}

