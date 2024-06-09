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
import com.ebook.app.databinding.PageLoginBinding;
import com.ebook.app.dto.ResponseDto;
import com.ebook.app.util.AlertUtil;
import com.ebook.app.util.InputValidator;
import com.ebook.app.util.ResponseOperation;
import com.ebook.app.util.SharedPrefsUtil;
import com.ebook.app.view.main.MainActivity;
import com.ebook.app.view.register.RegisterViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginFragment extends Fragment {
    final String TAG = "Login Page";
    private PageLoginBinding binding;
    private static LoginViewModel viewModel;
    private Button btnLogin;
    private Observer<ResponseDto> loginObserver;
    private TextInputLayout tilEmail, tilPassword;
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
        binding = PageLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        viewModel.getLoginResponse().removeObserver(loginObserver);//移除观察者
    }

    private void init(){
        initViewModel();
        initElement();//初始化元素
        initButtonListener();//初始化按钮
        initLoginObserver();//初始化登录观察者
    }
    private void initViewModel(){
        viewModel=new ViewModelProvider(this).get(LoginViewModel.class);
        binding.setLifecycleOwner(this);
    }
    private void initElement(){
        tilEmail=binding.loginTilEmail;
        tilPassword=binding.loginTilPassword;
        btnLogin=binding.loginBtnLogin;
    }
    private void initButtonListener(){
        btnLogin.setOnClickListener(v->btnLoginOnClick());
    }

    private void btnLoginOnClick(){
        String email=binding.getEmail();
        String password=binding.getPassword();
        if (!InputValidator.isNotEmpty(email)){
            tilEmail.setError("邮箱不能为空");
            return;
        }
        if (!InputValidator.isValidEmail(email)){
            tilEmail.setError("邮箱格式不正确");
            return;
        }
        tilEmail.setError(null);
        if (password==null || password.isEmpty()){
            tilPassword.setError("密码不能为空");
            return;
        }
        tilPassword.setError(null);
        viewModel.login(email,password);
    }
    private ResponseOperation loginOperation = new ResponseOperation("login") {
        @Override
        public void onSuccess(ResponseDto response) {
            AlertUtil.showToast(getContext(),"登录成功");
            //保存token
            SharedPrefsUtil prefsUtil=new SharedPrefsUtil(getContext());
            prefsUtil.putString("token",response.getData().getString("token"));
            //跳转到主页
            Intent intent=new Intent(getContext(), MainActivity.class);
            startActivity(intent);
        }

        @Override
        public void showError(String msg) {
            AlertUtil.showToast(getContext(),msg);
        }
    };
    private void initLoginObserver(){
        loginObserver=new Observer<ResponseDto>() {
            @Override
            public void onChanged(ResponseDto response) {
                loginOperation.onRespond(response);
            }
        };
        viewModel.getLoginResponse().observe(getViewLifecycleOwner(),loginObserver);
    }
}