package com.ebook.app.util;

import androidx.lifecycle.MutableLiveData;

import com.ebook.app.dtos.ResponseDto;

/**
 * viewmodel执行的回调内容一直，所以用一个类来封装
 */
public class MyCallBackImpl implements MyCallback{
    MutableLiveData<ResponseDto> liveData;
    public MyCallBackImpl(MutableLiveData<ResponseDto> liveData) {
        this.liveData = liveData;
    }

    /**
     * 响应成功
     * @param response 响应内容
     */
    @Override
    public void onSuccess(ResponseDto response) {
        liveData.postValue(response);
    }

    /**
     * 响应错误
     * @param e 错误信息
     */
    @Override
    public void onError(Exception e) {
        liveData.postValue(new ResponseDto(500,"服务器错误"));
    }
}
