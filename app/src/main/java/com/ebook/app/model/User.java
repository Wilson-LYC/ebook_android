package com.ebook.app.model;


import com.alibaba.fastjson.JSONObject;

public class User {
    private String name;
    private String email;
    private String avatar;
    private String token;

    public User(String name) {
        this.name = name;
    }

    public User(JSONObject json) {
        this.name = json.getString("name");
        this.email = json.getString("email");
        this.avatar = json.getString("avatar");
        this.token = json.getString("token");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
