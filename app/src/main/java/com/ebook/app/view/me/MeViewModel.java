package com.ebook.app.view.me;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.alibaba.fastjson.JSONObject;
import com.ebook.app.dto.ResponseDto;
import com.ebook.app.model.User;
import com.ebook.app.repository.UserRepository;
import com.ebook.app.util.AlertUtil;
import com.ebook.app.util.GeneralCallback;
import com.ebook.app.util.RequestCallback;
import com.ebook.app.view.set.SetInfoActivity;

import java.io.IOException;

import okhttp3.Call;

public class MeViewModel extends ViewModel {
    MutableLiveData<ResponseDto> user = new MutableLiveData<>();

    public LiveData<ResponseDto> getUser() {
        return user;
    }

    UserRepository userRepository = new UserRepository();

    public void loadUser(String token){
        userRepository.getUserByToken(token,new RequestCallback(user));
    }

}