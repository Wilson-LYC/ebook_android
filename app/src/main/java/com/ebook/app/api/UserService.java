package com.ebook.app.api;

import com.alibaba.fastjson.JSONObject;

public interface UserService {
    JSONObject login(String email, String password);
}
