package com.ebook.app.view.register;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ebook.app.dto.ResponseDto;
import com.ebook.app.model.Notify;
import com.ebook.app.repository.CaptchaRepository;
import com.ebook.app.repository.UserRepository;
import com.ebook.app.util.RequestCallback;

public class RegisterViewModel extends ViewModel {
    MutableLiveData<ResponseDto> sendCaptchaResponse = new MutableLiveData<>();//发送验证码响应
    MutableLiveData<ResponseDto> registerResponse = new MutableLiveData<>();//注册响应
    UserRepository userRepository = new UserRepository();
    CaptchaRepository captchaRepository = new CaptchaRepository();

    public MutableLiveData<ResponseDto> getRegisterResponse() {
        return registerResponse;
    }

    public MutableLiveData<ResponseDto> getSendCaptchaResponse() {
        return sendCaptchaResponse;
    }
    public void sendCaptcha(String email){
        captchaRepository.sendCaptcha(email,new RequestCallback(sendCaptchaResponse));
    }
    public void register(String email,String password,String captcha){
        userRepository.register(email,password,captcha,new RequestCallback(registerResponse));
    }
}
