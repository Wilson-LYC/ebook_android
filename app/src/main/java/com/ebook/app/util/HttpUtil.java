package com.ebook.app.util;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.ebook.app.dto.ResponseDto;

import java.io.IOException;
import java.io.StringReader;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttp;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpUtil {
    final static String BASE_URL = "http://10.0.2.2:8080";
    final OkHttpClient client = new OkHttpClient();

    public ResponseDto post(String url, RequestBody body,HttpResponseCallback callback) {
        ResponseDto responseDto = null;
        Request request = new Request.Builder()
                .url(BASE_URL+ url)
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // 请求失败
                Log.e("HttpUtil", "[POST "+url+"] error=" + e.getMessage());
                callback.onFailure(e); // 调用失败回调
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    Log.e("HttpUtil", "[POST "+url+"] error=" + response);
                    callback.onFailure(new IOException("Unexpected code " + response));
                } else {
                    String responseStr = response.body().string();
                    Log.i("HttpUtil", "[POST "+url+"] response=" + responseStr);

                    ResponseDto result = (ResponseDto)JSONObject.parseObject(responseStr);
                    callback.onSuccess(result); // 调用成功回调
                }
            }
        });
    }
}
