package com.ebook.app.util;

import androidx.lifecycle.MutableLiveData;

import com.ebook.app.dto.ResponseDto;

/**
 * Http请求回调接口实现类
 * 基本上回调操作类似，所以做一个实现类减少代码量
 */
public class HttpUtilCallbackImpl implements HttpUtilCallback{
    MutableLiveData<ResponseDto> liveData;
    public HttpUtilCallbackImpl(MutableLiveData<ResponseDto> liveData) {
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
