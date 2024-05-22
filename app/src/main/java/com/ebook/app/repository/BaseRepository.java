package com.ebook.app.repository;


import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ebook.app.callback.BaseCallBack;
import com.ebook.app.dto.ResponseDto;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class BaseRepository {
    final static String BASE_URL = "http://10.0.2.2:8080";

    private final OkHttpClient client= new OkHttpClient();

    public LiveData<ResponseDto> post(String url, RequestBody requestBody){
        MutableLiveData<ResponseDto> liveData = new MutableLiveData<>();
        Request request = new Request.Builder()
                .url(BASE_URL+url)
                .post(requestBody)
                .build();
        BaseCallBack callback=new BaseCallBack(liveData);
        client.newCall(request).enqueue(callback);
        return liveData;
    }

}
