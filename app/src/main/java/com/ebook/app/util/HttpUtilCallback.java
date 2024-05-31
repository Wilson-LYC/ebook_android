package com.ebook.app.util;

import com.ebook.app.dto.ResponseDto;

/**
 * Http请求回调接口
 */
public interface HttpUtilCallback {
    void onSuccess(ResponseDto response);
    void onError(Exception e);
}
