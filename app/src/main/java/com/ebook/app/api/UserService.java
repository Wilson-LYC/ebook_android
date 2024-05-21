package com.ebook.app.api;

import com.ebook.app.dto.ResponseDto;

public interface UserService {
    ResponseDto login(String email, String password);
}
