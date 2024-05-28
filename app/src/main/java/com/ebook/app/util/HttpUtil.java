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

/**
 * Http请求工具类
 */

public class HttpUtil {
    final static String TAG = "HttpUtil";
    final static String BASE_URL = "http://10.0.2.2:8080";
    private final OkHttpClient client= new OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .build();

    /**
     * post请求
     * @param url 请求地址
     * @param requestBody 请求参数
     * @param callback 回调函数-成功或失败执行的操作
     */
    public void post(String url, RequestBody requestBody,HttpUtilCallback callback) {
        Log.i(TAG,"post请求 "+url);
        //构建请求
        Request request = new Request.Builder()
                .url(BASE_URL + url)
                .post(requestBody)
                .build();
        new Thread() {
            @Override
            public void run() {
                try {
                    //发起同步请求
                    Response response = client.newCall(request).execute();
                    //判断响应是否成功
                    if (!response.isSuccessful())
                        throw new IOException("Unexpected code " + response);//请求失败，抛出异常
                    //响应成功，解析响应结果，解析异常将抛出异常
                    String result = response.body().string();
                    ResponseDto responseDto = JSON.parseObject(result, ResponseDto.class);
                    //执行响应成功的回调
                    Log.i(TAG,"post请求 "+url+" 请求成功");
                    callback.onSuccess(responseDto);
                } catch (Exception e) {
                    //响应失败
                    Log.e(TAG, "post请求 "+url+" 捕捉到异常\n"+e.getMessage());
                    //执行响应失败的回调
                    callback.onError(e);
                }
            }
        }.start();
    }
}
