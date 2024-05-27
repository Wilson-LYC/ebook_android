package com.ebook.app.util;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.ebook.app.dtos.ResponseDto;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpUtil {
    final static String TAG = "HttpUtil";
    //统一的BASE_URL
    final static String BASE_URL = "http://10.0.2.2:8080";
    //统一的OkHttpClient配置
    private final OkHttpClient client= new OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .build();

    /***
     * post请求
     * @param url 请求地址
     * @param requestBody 请求参数
     * @param callback 回调函数 响应成功或失败执行的操作
     */
    public void post(String url, RequestBody requestBody,MyCallback callback) {
        Log.d(TAG,"post请求 "+url);
        //构建请求
        Request request = new Request.Builder()
                .url(BASE_URL + url)
                .post(requestBody)
                .build();
        //启动线程
        new Thread() {
            @Override
            public void run() {
                try {
                    //发起请求
                    Response response = client.newCall(request).execute();
                    //判断响应是否成功
                    if (!response.isSuccessful())
                        throw new IOException("Unexpected code " + response);//请求失败，抛出异常
                    //响应成功，解析响应结果，解析异常将抛出异常
                    String result = response.body().string();
                    ResponseDto responseDto = JSON.parseObject(result, ResponseDto.class);
                    //执行响应成功的回调
                    Log.d(TAG,"post "+url+" 请求成功");
                    callback.onSuccess(responseDto);
                } catch (Exception e) {
                    //响应失败
                    Log.e(TAG, "post "+url+" 捕捉到异常\n"+e.getMessage());
                    //执行响应失败的回调
                    callback.onError(e);
                }
            }
        }.start();
    }
}
