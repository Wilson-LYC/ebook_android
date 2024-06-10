package com.ebook.app.view.set;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.ebook.app.R;
import com.ebook.app.databinding.PageSetEmailBinding;
import com.ebook.app.dto.ResponseDto;
import com.ebook.app.util.AlertUtil;
import com.ebook.app.util.InputValidator;
import com.ebook.app.util.ResponseOperation;
import com.ebook.app.util.SharedPrefsUtil;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputLayout;

public class SetEmailActivity extends AppCompatActivity {
    private PageSetEmailBinding binding;
    private MaterialToolbar topAppBar;
    private Button btnSendCaptcha,btnSave;
    private TextInputLayout tilEmail,tilCaptcha;
    private SetViewMobel viewMobel;
    private SharedPrefsUtil prefsUtil;
    private int uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=PageSetEmailBinding.inflate(getLayoutInflater());
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
        initUpdateEmail();
    }

    private void initTopAppBar() {
        topAppBar = binding.appbar;
        topAppBar.setNavigationOnClickListener(v -> finish());
    }

    private void initViewModel(){
        viewMobel=new ViewModelProvider(this).get(SetViewMobel.class);
    }
    private void initSendCaptcha(){
        btnSendCaptcha=binding.setEmailBtnGetCaptcha;
        btnSendCaptcha.setOnClickListener(v->sendCaptcha());
        tilEmail=binding.setEmailTilNewEmail;
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
    private void initUpdateEmail(){
        tilCaptcha=binding.setEmailTilCaptcha;
        uid=getIntent().getIntExtra("id",0);
        btnSave=binding.setEmailBtnSave;
        btnSave.setOnClickListener(v->updateEmail());
        viewMobel.getUpdateEmail().observe(this,responseDto -> {
            new ResponseOperation("UpdateEmail",getApplicationContext()){
                @Override
                public void onSuccess(ResponseDto response) {
                    AlertUtil.showToast(getContext(),"邮箱已更新");
                    finish();
                }

                @Override
                public void showError(String msg) {
                    AlertUtil.showToast(getContext(),msg);
                }
            }.onRespond(responseDto);
        });
    }
    private void updateEmail(){
        String email=binding.getEmail();
        String captcha=binding.getCaptcha();
        prefsUtil=SharedPrefsUtil.with(this);
        String token=prefsUtil.getString("token","");
        if (!InputValidator.isNotEmpty(email)){
            tilEmail.setError("请输入邮箱地址");
            return;
        }
        if (!InputValidator.isValidEmail(email)){
            tilEmail.setError("请输入正确的邮箱地址");
            return;
        }
        tilEmail.setError(null);
        if (!InputValidator.isNotEmpty(captcha)){
            tilCaptcha.setError("请输入验证码");
            return;
        }
        tilCaptcha.setError(null);
        viewMobel.updateEmail(uid,email,captcha,token);
    }
}