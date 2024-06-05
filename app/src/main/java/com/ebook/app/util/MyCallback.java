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

public class MyCallback implements Callback {
    MutableLiveData<ResponseDto> liveData;

    public MyCallback(MutableLiveData<ResponseDto> liveData) {
        super();
        this.liveData = liveData;
    }

    @Override
    public void onFailure(@NonNull Call call, @NonNull IOException e) {
        Log.e("Callback", "请求中止\n"+e.getMessage());
        liveData.postValue(new ResponseDto(500,"请求超时"));
    }

    @Override
    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
        Log.i("Callback", "请求成功");
        try{
            if (!response.isSuccessful()) {
                Log.e("Callback", "响应失败");
                throw new IOException("响应失败");
            }
            ResponseDto responseDto= JSON.parseObject(response.body().string(),ResponseDto.class);
            liveData.postValue(responseDto);
        }catch (Exception e){
            Log.e("Callback", "捕捉到异常\n"+e.getMessage());
            liveData.postValue(new ResponseDto(500,"请求错误"));
        }
    }
}
