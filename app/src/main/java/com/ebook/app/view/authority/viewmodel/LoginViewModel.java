package com.ebook.app.view.authority.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.alibaba.fastjson.JSON;
import com.ebook.app.dto.ResponseDto;
import com.ebook.app.repository.UserRepository;
import com.ebook.app.util.HttpUtilCallbackImpl;
import com.ebook.app.util.MyCallback;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 登录页面的viewmodel
 */
public class LoginViewModel extends ViewModel {
    private MutableLiveData<ResponseDto> loginLiveData=new MutableLiveData<>();
    private UserRepository userRepository=new UserRepository();
    public LiveData<ResponseDto> getLoginLiveData(){
        return loginLiveData;
    }
    public void login(String email, String password){
        userRepository.login(email, password, new MyCallback(loginLiveData));
    }
}
