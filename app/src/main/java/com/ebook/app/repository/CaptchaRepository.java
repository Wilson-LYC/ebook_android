package com.ebook.app.repository;

import com.ebook.app.util.HttpUtil;
import com.ebook.app.util.HttpUtilCallback;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class CaptchaRepository {
    private final String GET_CAPTCHA_URL = "/v1/captcha";
    private final HttpUtil httpUtil= new HttpUtil();

    /**
     * 获取验证码
     * @param email 邮箱
     * @param callback 回调函数
     */
    public void getCaptcha(String email, HttpUtilCallback callback) {
        RequestBody requestBody = new FormBody.Builder()
                .add("email", email)
                .build();
        httpUtil.post(GET_CAPTCHA_URL,requestBody,callback);
    }
}
