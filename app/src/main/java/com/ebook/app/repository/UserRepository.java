package com.ebook.app.repository;

import com.ebook.app.util.HttpUtil;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * 用户api
 */
public class UserRepository {
    private final String LOGIN_URL = "/v1/token/pwd";
    private final String REGISTER_URL = "/v1/user";
    private final String GET_USER_BY_TOKEN_URL = "/v1/user/token";
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

    /**
     * 注册
     * @param email 邮箱
     * @param password 密码
     * @param captcha 验证码
     * @param callback 回调函数
     */
    public void register(String email, String password, String captcha, Callback callback) {
        //封装请求体
        RequestBody requestBody = new FormBody.Builder()
                .add("email", email)
                .add("password", password)
                .add("captcha", captcha)
                .build();
        //发送请求
        httpUtil.post(REGISTER_URL,requestBody,callback);
    }

    /**
     * 通过token获取用户信息
     * @param token token
     * @param callback 回调函数
     */
    public void getUserByToken(Request request,Callback callback) {
        //发送请求
        httpUtil.get(GET_USER_BY_TOKEN_URL,request,callback);
    }
}
