package com.ebook.app.repositories;


import static java.security.AccessController.getContext;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.alibaba.fastjson.JSON;
import com.ebook.app.dtos.ResponseDto;
import com.ebook.app.util.AlertUtil;
import com.ebook.app.util.HttpUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UserRepository {
    private final String CLASS_TAG = "UserRepository";
    private final String LOGIN_URL = "/v1/token/pwd";
    private final HttpUtil client= new HttpUtil();
    public void login(String email, String password, MutableLiveData<ResponseDto> liveData) {
        //封装请求体
        RequestBody requestBody = new FormBody.Builder()
                .add("email", email)
                .add("password", password)
                .build();
        client.post(LOGIN_URL, requestBody,liveData);
    }
}
