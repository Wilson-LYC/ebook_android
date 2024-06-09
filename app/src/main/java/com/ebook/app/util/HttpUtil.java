package com.ebook.app.util;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.ebook.app.dto.ResponseDto;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Http请求工具类
 */

public class HttpUtil {
    final static String TAG = "HttpUtil";
    public final static String BASE_URL = "http://10.0.2.2:8080";
    private final OkHttpClient client= new OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .build();

    public void post(String url, RequestBody requestBody, Callback callback) {
        Log.i(TAG,"post => "+BASE_URL+url);
        Request request = new Request.Builder()
                .url(BASE_URL + url)
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(callback);
    }

    public void get(String url, Request request,Callback callback) {
        Log.i(TAG,"get => "+BASE_URL+url);
        client.newCall(request).enqueue(callback);
    }
    public void request (Request request,Callback callback) {
        client.newCall(request).enqueue(callback);
    }
}
