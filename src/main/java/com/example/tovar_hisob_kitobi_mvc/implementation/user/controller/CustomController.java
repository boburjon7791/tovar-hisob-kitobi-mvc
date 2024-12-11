package com.example.tovar_hisob_kitobi_mvc.implementation.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomController {
    @GetMapping("/auth/login")
    public String login(){
        return "auth/login";
    }
}
