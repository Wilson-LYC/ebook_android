package com.ebook.app.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ebook.app.dtos.ResponseDto;
import com.ebook.app.repositories.UserRepository;
import com.ebook.app.util.MyCallBackImpl;
import com.ebook.app.util.MyCallback;

public class LoginViewModel extends ViewModel {
    final MutableLiveData<ResponseDto> loginLiveData=new MutableLiveData<>();//登录livedata
    final UserRepository userRepository=new UserRepository();
    public LiveData<ResponseDto> getLoginLiveData(){
        //返回livedata供页面的observer监听
        return loginLiveData;
    }
    public void login(String email, String password){
        //调用api
        userRepository.login(email, password, new MyCallBackImpl(loginLiveData));
    }
}
