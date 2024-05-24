package com.ebook.app.repositories;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.alibaba.fastjson.JSON;
import com.ebook.app.dtos.ResponseDto;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ResponseDtoCallback implements Callback {
    MutableLiveData<ResponseDto> liveData;

    public ResponseDtoCallback(MutableLiveData<ResponseDto> liveData) {
        this.liveData = liveData;
    }

    @Override
    public void onFailure(@NonNull Call call, @NonNull IOException e) {
        Log.e("Http Request","无法连接到网络\n"+e.getMessage());
        liveData.postValue(new ResponseDto(500,"无法连接到网络"));
    }

    @Override
    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
        if (response.isSuccessful()){
            String responseStr=response.body().string();
            try {
                ResponseDto responseDto= JSON.parseObject(responseStr,ResponseDto.class);
                liveData.postValue(responseDto);
            }catch (Exception e){
                Log.e("Http Request","请求失败(1)\n"+e.getMessage()+e.getClass());
                liveData.postValue(new ResponseDto(500,"请求失败"));
            }
        }else {
            Log.e("Http Request","请求失败(2)\n"+response.body().string());
            liveData.postValue(new ResponseDto(response.code(),"请求失败"));
        }
    }
}
