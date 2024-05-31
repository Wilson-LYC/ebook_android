package com.ebook.app.view.authority.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ebook.app.dto.ResponseDto;
import com.ebook.app.repository.CaptchaRepository;
import com.ebook.app.repository.RegisterRepository;
import com.ebook.app.util.HttpUtilCallbackImpl;

public class RegisterViewModel extends ViewModel {
    MutableLiveData<ResponseDto> registerLiveData = new MutableLiveData<>();//注册数据
    MutableLiveData<ResponseDto> getCaptchaLiveData = new MutableLiveData<>();//获取验证码数据

    final RegisterRepository registerRepository = new RegisterRepository();
    final CaptchaRepository captchaRepository = new CaptchaRepository();
    public LiveData<ResponseDto> getRegisterLiveData() {
        return registerLiveData;
    }

    public LiveData<ResponseDto> getGetCaptchaLiveData() {
        return getCaptchaLiveData;
    }

    /**
     * 注册
     * @param email 邮箱
     * @param password 密码
     * @param captcha 验证码
     */
    public void register(String email, String password, String captcha) {
        registerRepository.register(email, password, captcha, new HttpUtilCallbackImpl(registerLiveData));
    }

    /**
     * 获取验证码
     * @param email 邮箱
     */
    public void getCaptcha(String email) {
        //获取验证码
        getCaptchaLiveData.postValue(new ResponseDto(0, "正在获取验证码"));
        captchaRepository.getCaptcha(email, new HttpUtilCallbackImpl(getCaptchaLiveData));
    }
}
