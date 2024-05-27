package com.ebook.app.repositories;

import com.ebook.app.util.HttpUtil;
import com.ebook.app.util.MyCallback;

import okhttp3.FormBody;
import okhttp3.RequestBody;
public class UserRepository {
    private final String LOGIN_URL = "/v1/token/pwd";
    private final HttpUtil httpUtil= new HttpUtil();

    /**
     * 登录
     * @param email 邮箱
     * @param password 密码
     * @param callback 回调函数
     */
    public void login(String email, String password, MyCallback callback) {
        //封装请求体
        RequestBody requestBody = new FormBody.Builder()
                .add("email", email)
                .add("password", password)
                .build();
        //发送请求
        httpUtil.post(LOGIN_URL, requestBody,callback);
    }
}
