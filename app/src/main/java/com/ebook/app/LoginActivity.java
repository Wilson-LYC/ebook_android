package com.ebook.app;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;

import cn.hutool.http.HttpUtil;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {
    private TextView email;//邮箱输入框
    private TextView captch;//验证码输入框
    private CheckBox autoRegister;//自动注册协议复选框
    private Button getCaptch;//获取验证码按钮
    final private OkHttpClient client = new OkHttpClient();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.login), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //初始化控件
        email = findViewById(R.id.login_input_email);
        captch = findViewById(R.id.login_input_captch);
        autoRegister = findViewById(R.id.login_auto_register);
        getCaptch = findViewById(R.id.login_button_get_captch);
    }

    /**
     * “登录”按钮点击事件
     * @param view 点击的视图
     */
    public void onLoginClick(View view) {
        if(!autoRegister.isChecked()){
            Toast.makeText(this, "请先同意自动注册协议", Toast.LENGTH_SHORT).show();
            return;
        }
        String emailText = email.getText().toString();
        String captchText = captch.getText().toString();
        Toast.makeText(this, emailText+" "+captchText, Toast.LENGTH_SHORT).show();
    }

    /**
     * “获取验证码”按钮点击事件
     * @param view 点击的视图
     */
    public void onGetCaptchClick(View view) {
        //向localhost:8080/v1/test发送get请求
        new Thread(() -> {
            Request request = new Request.Builder()
                    .url("http://10.0.2.2:8080/v1/test")
                    .build();
            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
                String responseText = response.body().string();
                runOnUiThread(() -> email.setText(responseText));
            } catch (IOException e) {
                email.setText(e.getMessage());
            }
        }).start();
        //按钮倒计时60s
        getCaptchStartCountdown();
    }

    /**
     * “获取验证码”按钮开始倒计时
     */
    private void getCaptchStartCountdown(){
        //修改按钮样式，禁用按钮，60s后重新启用
        getCaptch.setEnabled(false);
        getCaptch.setBackground(getDrawable(R.drawable.ebook_button_disabled));
        getCaptch.setText("60s后重新获取");
        new Thread(() -> {
            for(int i=59;i>0;i--){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                final int finalI = i;
                runOnUiThread(() -> getCaptch.setText(finalI+"s后重新获取"));
            }
            runOnUiThread(() -> {
                getCaptch.setEnabled(true);
                getCaptch.setBackground(getDrawable(R.drawable.ebook_button));
                getCaptch.setText("获取验证码");
            });
        }).start();
    }
}