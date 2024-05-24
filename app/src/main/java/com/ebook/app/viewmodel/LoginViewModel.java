package com.ebook.app.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ebook.app.dtos.ResponseDto;
import com.ebook.app.repositories.UserRepository;

public class LoginViewModel extends ViewModel {
    MutableLiveData<ResponseDto> liveData = new MutableLiveData<>();
    final UserRepository userRepository=new UserRepository();

    public LiveData<ResponseDto> getLiveData(){
        return liveData;
    }
    public void login(String email, String password){
        userRepository.login(email, password, liveData);
    }
}
