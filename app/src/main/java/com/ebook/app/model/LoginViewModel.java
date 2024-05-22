package com.ebook.app.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alibaba.fastjson.JSONObject;
import com.ebook.app.repository.UserRepository;

public class LoginViewModel extends ViewModel {
    private UserRepository userRepository=new UserRepository();
    public LiveData<String> login(String email, String password) {
        return userRepository.login(email, password);
    }
}
