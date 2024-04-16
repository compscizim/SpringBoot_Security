package com.compscizimsecurity.compscizimsecurity.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
public class SecureController {
    @GetMapping
    public String sayhello(){
        return "I am secure";
    }
}
