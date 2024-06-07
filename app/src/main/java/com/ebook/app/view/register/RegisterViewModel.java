package com.ebook.app.view.register;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ebook.app.dto.ResponseDto;
import com.ebook.app.repository.CaptchaRepository;
import com.ebook.app.repository.UserRepository;
import com.ebook.app.util.RequestCallback;

public class RegisterViewModel extends ViewModel {
    MutableLiveData<ResponseDto> registerLiveData = new MutableLiveData<>();//注册
    MutableLiveData<ResponseDto> getCaptchaLiveData = new MutableLiveData<>();//获取验证码
    final UserRepository userRepository = new UserRepository();
    final CaptchaRepository captchaRepository = new CaptchaRepository();
    public LiveData<ResponseDto> getRegisterLiveData() {
        return registerLiveData;
    }
    public LiveData<ResponseDto> getGetCaptchaLiveData() {
        return getCaptchaLiveData;
    }

    /**
     * 获取验证码
     * @param email 邮箱
     */
    public void getCaptcha(String email) {
        captchaRepository.getCaptcha(email, new RequestCallback(getCaptchaLiveData));
    }

    /**
     * 注册
     * @param email 邮箱
     * @param password 密码
     * @param captcha 验证码
     */
    public void register(String email, String password, String captcha) {
        userRepository.register(email, password, captcha, new RequestCallback(registerLiveData));
    }
}
