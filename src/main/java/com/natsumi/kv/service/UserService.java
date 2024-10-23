package com.natsumi.kv.service;


import com.natsumi.kv.model.User;
import com.natsumi.kv.repository.UsersRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UsersRepository usersRepository;

    public User getUser(Integer id) {
        User user = usersRepository.getOne(id);
        return user;
    }
    public User getUserByLogin(String login) {
        User user = usersRepository.getUserBylogin(login);
        return user;
    }
    public boolean updateUser(User user) {
        if(usersRepository.existsUserBylogin(user.getLogin())) {
            usersRepository.save(user);
            return true;
        }else{
            return false;
        }
    }

    public boolean updateUserAll(User user, JSONObject jsonObject) {
        if(user.getRole().equalsIgnoreCase("User")) {
            return false;
        }else{
            User user_toupdate=usersRepository.getUserBylogin(jsonObject.getString("old_login"));
            user_toupdate.UpdateJSON(jsonObject);
            if(usersRepository.existsUserBylogin(user_toupdate.getLogin()) && !jsonObject.getString("old_login").equalsIgnoreCase(jsonObject.getString("login"))) {
                return false;
            }
            usersRepository.save(user_toupdate);
            return true;

        }
    }

    public boolean deleteUser(User user) {
        if(usersRepository.existsUserBylogin(user.getLogin())) {
            usersRepository.delete(user);
            return true;
        }else{
            return false;
        }
    }
    public boolean addUser(User user) {
        if(usersRepository.existsUserBylogin(user.getLogin())) {
            return false;
        }else{
            usersRepository.save(user);
            return true;
        }
    }

    public List<User> getAllUsers() {
        return usersRepository.findAll();
    }

    public boolean deleteAllUsers() {
        usersRepository.deleteAll();
        return true;
    }
    public boolean updateAllUsers(List<User> users) {
        usersRepository.saveAll(users);
        return true;
    }
}
