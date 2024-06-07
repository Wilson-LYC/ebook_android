package com.ebook.app.view.login;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ebook.app.R;
import com.ebook.app.dto.ResponseDto;
import com.ebook.app.util.AlertUtil;
import com.ebook.app.util.CheckInputUtil;
import com.ebook.app.util.ResponseOperation;
import com.ebook.app.view.main.MainActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginFragment extends Fragment {
    final String TAG = "Login Page";
    private static LoginViewModel loginViewModel;
    private Observer<ResponseDto> loginObserver;
    private Button btnLogin;
    private TextInputLayout tilEmail, tilPassword;
    private TextInputEditText etEmail, etPassword;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public LoginFragment() {
    }
    
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.page_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //绑定ViewModel
        if(loginViewModel==null)
            loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        //绑定页面组件
        btnLogin = view.findViewById(R.id.login_btn_login);
        tilEmail = view.findViewById(R.id.login_til_email);
        tilPassword = view.findViewById(R.id.login_til_password);
        etEmail = view.findViewById(R.id.login_et_email);
        etPassword = view.findViewById(R.id.login_et_password);
        loginObserver = new Observer<ResponseDto>() {
            //登录观察者
            @Override
            public void onChanged(ResponseDto response) {
                //调用登录响应
                loginResponse.onRespond(response);
            }
        };
        loginViewModel.getLoginLiveData().observe(getViewLifecycleOwner(), loginObserver);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            //监听登录点击事件
            @Override
            public void onClick(View v) {
                //获取数据
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                //调用登录按钮点击事件
                btnLoginOnClick(email,password);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        loginViewModel.getLoginLiveData().removeObserver(loginObserver);//移除观察者
    }


    /**
     * 校验登录数据
     * @param email 邮箱
     * @param password 密码
     * @return 校验结果
     */
    private boolean checkLoginInput(String email,String password){
        if(!CheckInputUtil.checkEmpty(email,tilEmail,"邮箱不能为空"))
            return false;
        if(!CheckInputUtil.checkEmpty(password,tilPassword,"密码不能为空"))
            return false;
        if(!CheckInputUtil.checkEmail(email,tilEmail))
            return false;
        return true;
    }

    /**
     * 登录按钮点击事件
     * @param email 邮箱
     * @param password 密码
     */
    private void btnLoginOnClick(String email,String password){
        Log.i(TAG, "点击“登录”按钮");
        if(!checkLoginInput(email,password))
            return;
        AlertUtil.showToast(this.getContext(),"登录中...");
        btnLogin.setEnabled(false);
        loginViewModel.login(email,password);
    }

    ResponseOperation loginResponse = new ResponseOperation(TAG,getView()) {
        @Override
        public void onSuccess(ResponseDto response) {
            AlertUtil.showToast(getContext(),"登录成功");
            startActivity(new Intent(getContext(), MainActivity.class));//跳转到主页面
        }

        @Override
        public void onCommon(ResponseDto response) {
            btnLogin.setEnabled(true);
        }
    };
}