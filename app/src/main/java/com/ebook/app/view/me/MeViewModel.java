package com.ebook.app.view.me;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ebook.app.dto.ResponseDto;
import com.ebook.app.repository.UserRepository;
import com.ebook.app.util.HttpUtil;
import com.ebook.app.util.RequestCallback;

import okhttp3.Request;

public class MeViewModel extends ViewModel {
    MutableLiveData<ResponseDto> userInfoResponse = new MutableLiveData<>();
    UserRepository userRepository = new UserRepository();

    public MutableLiveData<ResponseDto> getUserInfoResponse() {
        return userInfoResponse;
    }
    public void loadUserInfo(String token) {
        Request request = new Request.Builder()
                .url("http://10.0.2.2:8080/v1/user/token")
                .addHeader("Authorization", token)
                .build();
        userRepository.getUserByToken(request,new RequestCallback(userInfoResponse));
    }
}