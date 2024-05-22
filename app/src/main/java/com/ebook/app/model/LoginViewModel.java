package com.ebook.app.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ebook.app.dto.ResponseDto;
import com.ebook.app.repository.BaseRepository;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class LoginViewModel extends ViewModel {
    private BaseRepository repository = new BaseRepository();
    public LiveData<ResponseDto> login(String email, String password) {
        RequestBody requestBody = new FormBody.Builder()
                .add("email", email)
                .add("password", password)
                .build();
        return repository.post("/v1/token/pwd", requestBody);
    }
}
