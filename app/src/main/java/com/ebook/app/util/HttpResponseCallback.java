package com.ebook.app.util;

import com.ebook.app.dto.ResponseDto;

public interface HttpResponseCallback {
    void onSuccess(ResponseDto response);
    void onFailure(Throwable t);
}
