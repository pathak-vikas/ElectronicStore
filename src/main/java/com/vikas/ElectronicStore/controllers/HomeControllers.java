package com.vikas.ElectronicStore.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class HomeControllers {

    @RequestMapping("/test1")
    public String testing(){
        return "Welcome to electronic store";
    }
}
