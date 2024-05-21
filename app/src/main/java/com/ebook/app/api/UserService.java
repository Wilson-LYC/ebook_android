package com.ebook.app.api;

import com.ebook.app.dto.ResponseDto;

public interface UserService {
    // 登录
    ResponseDto login(String email, String password);
}
