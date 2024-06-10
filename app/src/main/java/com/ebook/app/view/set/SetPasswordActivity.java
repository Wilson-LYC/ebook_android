package com.ebook.app.view.set;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.ebook.app.R;
import com.ebook.app.databinding.PageSetEmailBinding;
import com.ebook.app.databinding.PageSetPasswordBinding;
import com.ebook.app.dto.ResponseDto;
import com.ebook.app.util.AlertUtil;
import com.ebook.app.util.InputValidator;
import com.ebook.app.util.ResponseOperation;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputLayout;

public class SetPasswordActivity extends AppCompatActivity {

    private PageSetPasswordBinding binding;
    private MaterialToolbar topAppBar;
    private SetViewMobel viewMobel;
    private Button btnSendCaptcha,btnSave;
    private TextInputLayout tilEmail,tilCaptcha,tilPassword,tilConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=PageSetPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();
    }

    private void init(){
        initTopAppBar();
        initViewModel();
        initSendCaptcha();
        initUpdatePassword();
    }
    private void initTopAppBar() {
        topAppBar = binding.appbar;
        topAppBar.setNavigationOnClickListener(v -> finish());
    }
    private void initViewModel(){
        viewMobel=new ViewModelProvider(this).get(SetViewMobel.class);
    }
    private void initSendCaptcha(){
        btnSendCaptcha=binding.setPwdBtnGetCaptcha;
        btnSendCaptcha.setOnClickListener(v->sendCaptcha());
        tilEmail=binding.setPwdTilEmail;
        viewMobel.getSendCaptcha().observe(this,responseDto -> {
            new ResponseOperation("SendCaptcha",getApplicationContext()){
                @Override
                public void onSuccess(ResponseDto response) {
                    AlertUtil.showToast(getContext(),"验证码已发送");
                }

                @Override
                public void showError(String msg) {
                    AlertUtil.showToast(getContext(),msg);
                    btnSendCaptcha.setEnabled(true);
                }
            }.onRespond(responseDto);
        });
    }
    private void sendCaptcha(){
        String email=binding.getEmail();
        if(!InputValidator.isNotEmpty(email)){
            tilEmail.setError("请输入邮箱地址");
            return;
        }
        if(!InputValidator.isValidEmail(email)){
            tilEmail.setError("请输入正确的邮箱地址");
            return;
        }
        tilEmail.setError(null);
        viewMobel.sendCaptcha(email);
        banBtnSendCaptcha();
    }
    private void banBtnSendCaptcha(){
        btnSendCaptcha.setEnabled(false);
        new Thread(()->{
            for(int i=60;i>0;i--){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                final int finalI = i;
                runOnUiThread(()->{
                    btnSendCaptcha.setText(finalI+"后重新发送");
                    if(finalI==1){
                        btnSendCaptcha.setText("获取验证码");
                        btnSendCaptcha.setEnabled(true);
                    }
                });
            }
        }).start();
    }
    private void initUpdatePassword(){
        tilCaptcha=binding.setPwdTilCaptcha;
        tilPassword=binding.setPwdTilNewPwd;
        tilConfirmPassword=binding.setPwdTilNewPwdConfirm;
        btnSave=binding.setPwdBtnSave;
        btnSave.setOnClickListener(v->updatePassword());
        viewMobel.getUpdatePassword().observe(this,responseDto -> {
            new ResponseOperation("UpdatePassword",getApplicationContext()){
                @Override
                public void onSuccess(ResponseDto response) {
                    AlertUtil.showToast(getContext(),"密码已更新");
                    finish();
                }

                @Override
                public void showError(String msg) {
                    AlertUtil.showToast(getContext(),msg);
                }
            }.onRespond(responseDto);
        });
    }
    private void updatePassword(){
        String email=binding.getEmail();
        String captcha=binding.getCaptcha();
        String password=binding.getPassword();
        String confirmPassword=binding.getConfirmPassword();
        if(!InputValidator.isNotEmpty(email)){
            tilEmail.setError("请输入邮箱地址");
            return;
        }
        if(!InputValidator.isValidEmail(email)){
            tilEmail.setError("请输入正确的邮箱地址");
            return;
        }
        tilEmail.setError(null);
        if(!InputValidator.isNotEmpty(captcha)){
            tilCaptcha.setError("请输入验证码");
            return;
        }
        tilCaptcha.setError(null);
        if(!InputValidator.isNotEmpty(password)){
            tilConfirmPassword.setError("请输入新密码");
            return;
        }
        if(!InputValidator.isNotEmpty(confirmPassword)){
            tilConfirmPassword.setError("请再次输入新密码");
            return;
        }
        if(!password.equals(confirmPassword)){
            tilConfirmPassword.setError("两次输入的密码不一致");
            return;
        }
        tilConfirmPassword.setError(null);
        viewMobel.updatePassword(email,captcha,password);
    }
}