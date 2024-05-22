package com.ebook.app.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.alibaba.fastjson.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UserRepository extends BaseRepository{
    private final OkHttpClient client=new OkHttpClient();

    /**
     * 登录
     * @param email 邮箱
     * @param password 密码
     * @return 登录结果
     */
    public LiveData<String> login(String email, String password) {
        // 创建LiveData对象
        MutableLiveData<String> liveData = new MutableLiveData<>();
        // 构建请求体
        RequestBody requestBody = new FormBody.Builder()
                .add("email", email)
                .add("password", password)
                .build();
        // 构建请求
        Request request = new Request.Builder()
                .url(BASE_URL+"/v1/token")
                .post(requestBody)
                .build();
        // 发起请求
        client.newCall(request).enqueue(new Callback() {
            /**
             * 请求失败
             * @param call 请求
             * @param e 异常
             */
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                // 请求失败
                String errorMsg="登录失败 error="+e.getMessage();
                // 打印日志
                Log.e("UserRepository", errorMsg);
                // 返回空对象
                liveData.postValue("ERROR");
            }

            /**
             * 请求成功
             * @param call 请求
             * @param response 响应
             * @throws IOException IO异常
             */
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                // 请求成功
                if(!response.isSuccessful())
                    return;
                String result=response.body().string();
                liveData.postValue(result);
            }
        });
        // 返回LiveData对象
        return liveData;
    }
}
