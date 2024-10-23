package com.natsumi.kv.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;


@Entity
@Getter
@Setter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "login",unique = true)
    @NotNull
    private String login;
    @Column(name = "password")
    @NotNull
    private String password;
    @Column(name = "role")
    @NotNull
    private String role;

    public User(String login, String password, String role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }

    @Override
    public String toString() {
        return "{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                '}';
    }

    public User(JSONObject json) {
        this.login=json.optString("login");
        this.password=json.optString("password");
        this.role=json.optString("role");
    }

    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.put("login",login);
        json.put("role",role);
        return json;
    }

    public void UpdateJSON(JSONObject json) {
        this.login=json.optString("login");
        this.role=json.optString("role");
    }

    public User() {

    }
}
