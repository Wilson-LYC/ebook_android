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
import android.widget.TextView;

import com.ebook.app.R;
import com.ebook.app.databinding.PageLoginBinding;
import com.ebook.app.dto.ResponseDto;
import com.ebook.app.util.AlertUtil;
import com.ebook.app.util.InputValidator;
import com.ebook.app.util.ResponseOperation;
import com.ebook.app.util.SharedPrefsUtil;
import com.ebook.app.view.authority.AuthorityActivity;
import com.ebook.app.view.main.MainActivity;
import com.ebook.app.view.register.RegisterViewModel;
import com.ebook.app.view.set.SetPasswordActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginFragment extends Fragment {
    final String TAG = "Login Page";
    private PageLoginBinding binding;
    private static LoginViewModel viewModel;
    private TextView tvForgotPwd;
    private Button btnLogin;
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
    }

    private void init(){
        initViewModel();
        initLogin();
        initForgetPwd();
    }
    private void initViewModel(){
        viewModel=new ViewModelProvider(this).get(LoginViewModel.class);
        binding.setLifecycleOwner(this);
    }

    private void initLogin(){
        tilEmail=binding.loginTilEmail;
        tilPassword=binding.loginTilPassword;
        btnLogin=binding.loginBtnLogin;
        btnLogin.setOnClickListener(v->login());
        viewModel.getLoginResponse().observe(getViewLifecycleOwner(),ResponseDto-> {
            new ResponseOperation("login",getContext()){
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
            }.onRespond(ResponseDto);
        });
    }

    private void login(){
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

    private void initForgetPwd(){
        tvForgotPwd=binding.loginTvForgotPwd;
        tvForgotPwd.setOnClickListener(v->{
            Intent intent = new Intent(getActivity(), SetPasswordActivity.class);
            startActivity(intent);
        });
    }
}