package com.ebook.app.api.impl;

import android.util.Log;

import com.ebook.app.api.UserService;
import com.ebook.app.dto.ResponseDto;
import com.ebook.app.util.HttpUtil;

import okhttp3.FormBody;
import okhttp3.RequestBody;


public class UserServiceImpl implements UserService {
    HttpUtil httpUtil = new HttpUtil();
    @Override
    public ResponseDto login(String email, String password) {
        Log.i("UserService", "[Login] email="+email);
        RequestBody requestBody = new FormBody.Builder()
                .add("email", email)
                .add("password", password)
                .build();
        return null;
    }
}
