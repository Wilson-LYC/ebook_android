package com.ebook.app.view.authority.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ebook.app.dto.ResponseDto;
import com.ebook.app.repository.UserRepository;
import com.ebook.app.util.RequestCallback;

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
        userRepository.login(email, password, new RequestCallback(loginLiveData));
    }
}
