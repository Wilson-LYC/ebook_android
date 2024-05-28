package com.ebook.app.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ebook.app.dtos.ResponseDto;
import com.ebook.app.repositories.UserRepository;
import com.ebook.app.util.HttpUtilCallbackImpl;

/**
 * 登录页面的viewmodel
 */
public class LoginViewModel extends ViewModel {
    final MutableLiveData<ResponseDto> loginLiveData=new MutableLiveData<>();//登录livedata
    final UserRepository userRepository=new UserRepository();//调用api
    public LiveData<ResponseDto> getLoginLiveData(){
        //返回livedata，提供给页面的observer（观察者）监听
        return loginLiveData;
    }
    public void login(String email, String password){
        userRepository.login(email, password, new HttpUtilCallbackImpl(loginLiveData));//调用api
    }
}
