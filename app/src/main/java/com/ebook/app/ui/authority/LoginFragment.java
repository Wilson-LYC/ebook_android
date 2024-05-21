package com.ebook.app.ui.authority;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.ebook.app.R;
import com.ebook.app.api.UserService;
import com.ebook.app.api.impl.UserServiceImpl;
import com.ebook.app.dto.ResponseDto;
import com.ebook.app.pojo.User;
import com.ebook.app.util.AlertUtil;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginFragment extends Fragment {

    private EditText edEmail, edPassword;
    private Button btnLogin;

    private final UserService userService = new UserServiceImpl();

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
        edEmail = getView().findViewById(R.id.login_ed_email);
        edPassword = getView().findViewById(R.id.login_ed_password);
        btnLogin= getView().findViewById(R.id.login_btn_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
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
                ResponseDto loginRes=userService.login(email,password);
            }
        });
    }
}