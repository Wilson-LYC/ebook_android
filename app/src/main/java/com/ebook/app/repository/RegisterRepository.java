package com.ebook.app.repository;

import android.util.Log;

import com.ebook.app.util.HttpUtil;
import com.ebook.app.util.HttpUtilCallback;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class RegisterRepository {
    private final String REGISTER_URL = "/v1/account";
    private final HttpUtil httpUtil= new HttpUtil();
    /**
     * 注册
     * @param email 邮箱
     * @param password 密码
     * @param captcha 验证码
     * @param callback 回调
     */
    public void register(String email, String password, String captcha, HttpUtilCallback callback) {
        RequestBody requestBody = new FormBody.Builder()
                .add("email", email)
                .add("password", password)
                .add("captcha", captcha)
                .build();
        Log.d("RegisterRepository", "register: "+email+" "+password+" "+captcha);
        httpUtil.post(REGISTER_URL,requestBody,callback);
    }
}
