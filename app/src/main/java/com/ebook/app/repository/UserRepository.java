package com.ebook.app.repository;

import com.ebook.app.util.HttpUtil;
import com.ebook.app.util.HttpUtilCallbackImpl;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * 用户api
 */
public class UserRepository {
    private final String LOGIN_URL = "/v1/token/pwd";
    private final HttpUtil httpUtil= new HttpUtil();

    /**
     * 登录
     * @param email 邮箱
     * @param password 密码
     * @param callback 回调函数
     */
    public void login(String email, String password, Callback callback) {
        //封装请求体
        RequestBody requestBody = new FormBody.Builder()
                .add("email", email)
                .add("password", password)
                .build();
        //发送请求
        httpUtil.post(LOGIN_URL,requestBody,callback);
    }
}
