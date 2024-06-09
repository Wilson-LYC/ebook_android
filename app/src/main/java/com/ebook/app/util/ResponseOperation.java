package com.ebook.app.util;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.ebook.app.dto.ResponseDto;
import com.ebook.app.model.Notify;

public abstract class ResponseOperation {
    private String tag;
    public ResponseOperation(String tag) {
        this.tag = tag;
    }

    public void onRespond(ResponseDto response){
        onCommon(response);
        switch (response.getCode()){
            case 200:
                Log.i(tag, "响应成功");
                onSuccess(response);
                break;
            case 400:
                Log.e(tag, "参数错误 "+response.getMsg());
                onParamError(response);
                break;
            default:
                Log.e(tag, "系统错误 "+response.getMsg());
                onServerError(response);
        }
    }

    public abstract void onSuccess(ResponseDto response);

    public void onParamError(ResponseDto response){
        showError(response.getMsg());
    }

    /**
     * 服务器错误
     * @param response 响应数据
     */
    public void onServerError(ResponseDto response){
        showError(response.getMsg());
    }

    /**
     * 通用操作
     */
    public void onCommon(ResponseDto response){}

    public abstract void showError(String msg);
}
