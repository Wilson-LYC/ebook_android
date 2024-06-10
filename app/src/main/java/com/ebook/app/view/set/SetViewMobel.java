package com.ebook.app.view.set;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ebook.app.dto.ResponseDto;
import com.ebook.app.repository.CaptchaRepository;
import com.ebook.app.repository.UserRepository;
import com.ebook.app.util.RequestCallback;

public class SetViewMobel extends ViewModel {
    MutableLiveData<ResponseDto> updateInfo=new MutableLiveData<>();
    MutableLiveData<ResponseDto> sendCaptcha=new MutableLiveData<>();
    MutableLiveData<ResponseDto> updateEmail=new MutableLiveData<>();
    MutableLiveData<ResponseDto> updatePassword=new MutableLiveData<>();

    public MutableLiveData<ResponseDto> getUpdateInfo() {
        return updateInfo;
    }

    public MutableLiveData<ResponseDto> getUpdateEmail() {
        return updateEmail;
    }

    public MutableLiveData<ResponseDto> getSendCaptcha() {
        return sendCaptcha;
    }

    public MutableLiveData<ResponseDto> getUpdatePassword() {
        return updatePassword;
    }

    UserRepository userRepository=new UserRepository();
    CaptchaRepository captchaRepository=new CaptchaRepository();

    public void updateInfo(int id,String name,String token){
        userRepository.updateInfo(id,name,token,new RequestCallback(updateInfo));
    }

    public void sendCaptcha(String email){
        captchaRepository.sendCaptcha(email,new RequestCallback(sendCaptcha));
    }
    public void updateEmail(int id,String email,String captcha,String token){
        userRepository.updateEmail(id,email,captcha,token,new RequestCallback(updateEmail));
    }

    public void updatePassword(String email,String captcha,String password){
        userRepository.updatePassword(email,captcha,password,new RequestCallback(updatePassword));
    }
}
