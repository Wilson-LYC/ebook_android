package com.ebook.app.util;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.alibaba.fastjson.JSON;
import com.ebook.app.dto.ResponseDto;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public abstract class GeneralCallback implements Callback {
    final static String TAG="Callback";
    @Override
    public void onFailure(@NonNull Call call, @NonNull IOException e) {
        //请求未能发送至服务器
        Log.e(TAG, "请求失败 "+e.getMessage());
    }
    @Override
    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
        Log.i(TAG, "请求完成");
        try{
            if (!response.isSuccessful()) {
                throw new IOException("response is not successful");
            }
            ResponseDto responseDto= JSON.parseObject(response.body().string(),ResponseDto.class);
            if(responseDto.getCode()==500){
                throw new IOException(responseDto.getMsg());
            }
            onSucceed(responseDto);
        }catch (Exception e){
            Log.e(TAG, "捕捉到异常 "+e.getMessage());
            onError(e);
        }
    }
    public abstract void onSucceed(ResponseDto response);
    public abstract void onError(Exception e);
}
