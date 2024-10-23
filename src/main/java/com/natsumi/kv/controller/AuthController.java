package com.natsumi.kv.controller;

import com.natsumi.kv.model.User;
import com.natsumi.kv.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AuthController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @PostMapping("/registration")
    public String registerUser(@ModelAttribute User user) {
        System.out.println("Registering user " + user.getLogin());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        System.out.println("Password: " + user.getPassword());
        System.out.println("role: " + user.getRole());
        userService.addUser(user);
        return "redirect:/login";
    }
}
