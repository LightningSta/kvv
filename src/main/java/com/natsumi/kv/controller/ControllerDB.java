package com.natsumi.kv.controller;

import com.natsumi.kv.model.User;
import com.natsumi.kv.security.details.UsersDetails;
import com.natsumi.kv.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

@Controller
public class ControllerDB {

    @Autowired
    private UserService userService;

    private User getUser(HttpSession session) {
        SecurityContextImpl impl = (SecurityContextImpl) session.getAttribute("SPRING_SECURITY_CONTEXT");
        Authentication auth = impl.getAuthentication();
        User user = ((UsersDetails) auth.getPrincipal()).getUser();
        return user;
    }

    private Boolean compare(String role, String role_comp){
        if(role.equals("User")){
            if(role_comp.equals("Admin")|| role_comp.equals("Moderator")){
                return false;
            }else{
                return true;
            }
        }else if(role.equals("Moderator")){
            if(role_comp.equals("Admin")){
                return false;
            }else{
                return true;
            }
        }else{
            return true;
        }
    }

    @GetMapping("/home")
    public String home(HttpSession session) {
        return "home";
    }

    @GetMapping("/home/list")
    @ResponseBody
    public String list(HttpSession session) {
        User user = getUser(session);
        List<User> users = userService.getAllUsers();
        JSONObject obj = new JSONObject();
        JSONArray arr = new JSONArray();
        arr.put(user.toJSON());
        for (int i = 0; i < users.size(); i++) {
            if(compare(user.getRole(), users.get(i).getRole())){
                arr.put(users.get(i).toJSON());
            }
        }
        obj.put("users",arr);
        return obj.toString(2);
    }
    @DeleteMapping("/home/deleteAll")
    @ResponseBody
    public String deleteAll(HttpSession session) {
        User user = getUser(session);
        if(user.getRole().equals("Admin")){
            userService.deleteAllUsers();
            return "success";
        }
        return "fail";
    }
    @DeleteMapping("/home/delete")
    @ResponseBody
    public String delete(HttpSession session,@RequestBody String json) {
        User user = getUser(session);
        String login =  new JSONObject(json).getString("login");
        if(user.getRole().equals("Admin")){
            userService.deleteUser(userService.getUserByLogin(
                    login
            ) );
            return "success";
        }
        return "fail";
    }

    @PatchMapping("/home/update")
    @ResponseBody
    public String update(HttpSession session,@RequestBody String json) {
        User user = getUser(session);
        boolean operation =userService.updateUserAll(user,new JSONObject(json));
        if(operation){
            return "success";
        }
        return "fail";
    }
}
