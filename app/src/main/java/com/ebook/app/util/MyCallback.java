package com.ebook.app.util;

import com.ebook.app.dtos.ResponseDto;

public interface MyCallback {
    void onSuccess(ResponseDto response);
    void onError(Exception e);
}
