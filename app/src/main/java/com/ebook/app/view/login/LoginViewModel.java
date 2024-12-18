package com.ebook.app.view.login;

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
    private MutableLiveData<ResponseDto> loginResponse=new MutableLiveData<>();
    private UserRepository userRepository=new UserRepository();

    public LiveData<ResponseDto> getLoginResponse() {
        return loginResponse;
    }

    public void login(String email, String password){
        userRepository.login(email, password, new RequestCallback(loginResponse));
    }
}
