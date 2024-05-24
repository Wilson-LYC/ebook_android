package com.ebook.app.util;

import android.os.Handler;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.alibaba.fastjson.JSON;
import com.ebook.app.dtos.ResponseDto;

import java.util.concurrent.TimeUnit;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class HttpUtil {
    final static String BASE_URL = "http://10.0.2.2:8080";
    private final OkHttpClient client= new OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .build();

    public void post(String url, RequestBody requestBody, MutableLiveData<ResponseDto> liveData) {
        Request request = new Request.Builder()
                .url(BASE_URL + url)
                .post(requestBody)
                .build();
        new Thread() {
            @Override
            public void run() {
                try {
                    Response response = client.newCall(request).execute();
                    String res = response.body().string();
                    if (response.isSuccessful()) {
                        ResponseDto responseDto = JSON.parseObject(res, ResponseDto.class);
                        liveData.postValue(responseDto);
                    } else {
                        Log.e("HttpUtil", "请求失败\n" + res);
                        liveData.postValue(new ResponseDto(500, "请求失败"));
                    }
                } catch (Exception e) {
                    Log.e("HttpUtil", "请求失败\n" + e);
                    liveData.postValue(new ResponseDto(500, "请求失败"));
                }
            }
        }.start();
    }

    public void postAsyc(String url, RequestBody requestBody,Callback callBack ){
        Request request = new Request.Builder()
                .url(BASE_URL+url)
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(callBack);
    }
}
