package com.ebook.app.repository;

import com.ebook.app.util.HttpUtil;
import com.ebook.app.util.RequestCallback;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class CaptchaRepository {
    private final String SEND_CAPTCHA_URL = "/v1/captcha";
    private final HttpUtil httpUtil= new HttpUtil();

    /**
     * 发送验证码
     * @param email 邮箱
     * @param callback 回调函数
     */
    public void sendCaptcha(String email, RequestCallback callback) {
        RequestBody requestBody = new FormBody.Builder()
                .add("email", email)
                .build();
        httpUtil.post(SEND_CAPTCHA_URL,requestBody,callback);
    }
}
