package com.ebook.app.ui.authority;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.ebook.app.R;
import com.ebook.app.model.LoginViewModel;
import com.ebook.app.util.AlertUtil;

public class LoginFragment extends Fragment {

    // UI
    private EditText edEmail, edPassword;
    private Button btnLogin;
    // Model
    private LoginViewModel loginViewModel;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LoginFragment() {
        // Required empty public constructor
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loginViewModel= new ViewModelProvider(this).get(LoginViewModel.class);

        //获取控件
        edEmail = getView().findViewById(R.id.login_ed_email);
        edPassword = getView().findViewById(R.id.login_ed_password);
        btnLogin= getView().findViewById(R.id.login_btn_login);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            //登录按钮点击事件
            @Override
            public void onClick(View v) {
                //获取邮箱和密码
                String email = edEmail.getText().toString().trim();
                String password = edPassword.getText().toString().trim();
                //判断邮箱和密码是否为空
                if (email.isEmpty() || password.isEmpty()) {
                    Log.w("Login", "邮箱或密码为空，拒绝登录");
                    AlertUtil.showErrorDialog(getContext(), "邮箱或密码不能为空");
                    return;
                }
                //判断邮箱格式是否正确
                if (!email.matches("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$")) {
                    Log.w("Login", "邮箱格式错误，拒绝登录");
                    AlertUtil.showErrorDialog(getContext(), "邮箱格式错误");
                    return;
                }
                //登录
                loginViewModel.login(email, password).observe(getViewLifecycleOwner(), res -> {
                    if (res.contains("200")) {
                        Log.i("Login", "登录成功\n"+res);
                        AlertUtil.showToast(getContext(), "登录成功");
                    } else {
                        Log.w("Login", "登录失败\n"+res);
                        AlertUtil.showToast(getContext(), "登录失败");
                    }
                });
            }
        });
    }
}