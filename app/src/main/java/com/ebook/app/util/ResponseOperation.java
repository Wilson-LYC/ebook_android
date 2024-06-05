package com.ebook.app.util;

import android.content.Context;
import android.util.Log;

import com.ebook.app.dto.ResponseDto;

public abstract class ResponseOperation {
    private String tag;

    public ResponseOperation(String TAG) {
        this.tag = TAG;
    }

    /**
     * @param response 响应数据
     */
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

    /**
     * @code 200 业务执行成功
     * @param response 响应数据
     */
    public abstract void onSuccess(ResponseDto response);

    /**
     * @code 400 参数错误
     * @param response 响应数据
     */
    public void onParamError(ResponseDto response){
        showDialog(response.getMsg());
    }

    /**
     * 服务器错误
     * @param response 响应数据
     */
    public void onServerError(ResponseDto response){
        showDialog(response.getMsg());
    }

    /**
     * 通用操作
     */
    public void onCommon(ResponseDto response){}

    public abstract void showDialog(String msg);
}
