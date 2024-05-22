package com.ebook.app.callback;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ebook.app.dto.ResponseDto;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class BaseCallBack implements Callback {
    protected MutableLiveData<ResponseDto> liveData;
    public BaseCallBack(MutableLiveData<ResponseDto> liveData)
    {
        this.liveData = liveData;
    }

    @Override
    public void onFailure(@NonNull Call call, @NonNull IOException e) {
        Log.e("Http Request", "网络请求错误\n"+e.getMessage());
        liveData.postValue(new ResponseDto(500, "网络请求错误"));
    }

    @Override
    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
        if (response.isSuccessful()) {
            String result = response.body().string();
            ResponseDto responseDto;
            try{
                responseDto = JSON.parseObject(result, ResponseDto.class);
            }catch (Exception e){
                Log.e("Http Request", "请求参数错误\n"+result);
                responseDto = new ResponseDto(400, "请求参数错误");
            }
            liveData.postValue(responseDto);
        } else {
            Log.e("Http Request", "网络请求错误\n"+response.body().string());
            liveData.postValue(new ResponseDto(500, "网络请求错误"));
        }
    }
}
