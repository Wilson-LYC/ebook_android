package com.ebook.app.repository;

import com.ebook.app.util.HttpUtil;


import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;

public class AiReposity {
    private final HttpUtil httpUtil= new HttpUtil();
    public void sendMsg(String msg, Callback callback) {
        String url=HttpUtil.BASE_URL+"/v1/ai";
        RequestBody requestBody = new FormBody.Builder()
                .add("question", msg)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        httpUtil.request(request,callback);
    }
}
