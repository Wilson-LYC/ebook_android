package com.ebook.app.util;

import com.ebook.app.dtos.ResponseDto;

/**
 * Http请求回调接口
 */
public interface HttpUtilCallback {
    void onSuccess(ResponseDto response);
    void onError(Exception e);
}
