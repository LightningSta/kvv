package com.natsumi.kv.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Main {


    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "singin";
    }
    @GetMapping("/registration")
    public String reg() {
        return "singup";
    }
}
